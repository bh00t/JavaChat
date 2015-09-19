package JavaChat;

import java.net.*;
import javax.swing.*;
import java.awt.*;

class app
{
	static menubar m = new menubar();
	static String name;
	static chatbox chat = new chatbox();
	static JFrame a = new JFrame("JavaChat");
	app() throws UnknownHostException
	{
		Container cntnr = a.getContentPane();
		cntnr.setLayout(new BorderLayout());
		ImageIcon calicon=new ImageIcon("JavaChat/javachat.png");
		a.setIconImage(calicon.getImage());

		a.setJMenuBar(m);


		InetAddress IP = InetAddress.getLocalHost();
		JLabel ip;
		if(IP.toString().indexOf('/')>-1)
		{
			name = IP.toString().substring(0,IP.toString().indexOf('/'));
			ip = new JLabel("IP address : "+IP.toString().substring(IP.toString().indexOf('/')+1)+"  ",JLabel.RIGHT);
		}
		else
		{
			ip = new JLabel("IP address : "+IP.getLocalHost(),JLabel.RIGHT);
			name ="Friend";
		}
		
		cntnr.add(ip,BorderLayout.NORTH);

		JScrollPane scrollPane = new JScrollPane(chat);
		cntnr.add(scrollPane,BorderLayout.CENTER);

		a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		a.setResizable(false);
		a.setBounds(1000,58,300,613);
		a.setVisible(true);

	}

	public static void main(String[] args) throws UnknownHostException
	{
		app a =new app();
	}
}