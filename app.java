package JavaChat;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class app
{
	static menubar m = new menubar();
	static String name;
	static chatbox chat = new chatbox();
	//static JTabbedPane chat = new JTabbedPane(JTabbedPane.BOTTOM);
	static JFrame a = new JFrame("JavaChat");
	public static void main(String[] args) throws UnknownHostException
	{
		
		Container cntnr = a.getContentPane();
		cntnr.setLayout(new BorderLayout());

		a.setJMenuBar(m);

		InetAddress IP = InetAddress.getLocalHost();
		JLabel ip;
		if(IP.toString().indexOf('/')>-1)
		{
			name = IP.toString().substring(0,IP.toString().indexOf('/'));
			System.out.println(name);
			ip = new JLabel("IP address : "+IP.toString().substring(IP.toString().indexOf('/')+1)+"  ",JLabel.RIGHT);
		}
		else
			ip = new JLabel("IP address : "+IP.getLocalHost(),JLabel.RIGHT);
		
		cntnr.add(ip,BorderLayout.NORTH);

		//chat.setEnabled(false);
		JScrollPane scrollPane = new JScrollPane(chat);
		cntnr.add(scrollPane,BorderLayout.CENTER);

		a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		//a.setResizable(false);
		a.setBounds(1000,58,300,613);
		a.setVisible(true);
		//chat.clear_sendbox();
	}
}

class menubar extends JMenuBar
{
	JMenu Connect,Help;
	JMenuItem Create_Server, Close_Server,Connect_Server, About, Documentation;
	JDialog Create_ServerDialog, Connect_ServerDialog;
	JLabel portwarning, port;

	menubar()
	{
		portwarning = new JLabel("",JLabel.CENTER);
		Font font=new Font(portwarning.getFont().getName(),portwarning.getFont().getStyle(),10);
		portwarning.setFont(font);

		port =new JLabel();
		Font font2=new Font(port.getFont().getName(),port.getFont().getStyle(),15);
		port.setFont(font2);
		port.setBackground(Color.WHITE);
		port.addKeyListener(new event());

		Connect = new JMenu("Connect",false);

		Create_Server = new JMenuItem("Create Server");
		Create_Server.setActionCommand("Create_Server");
		Create_Server.addActionListener(new event());

		Close_Server = new JMenuItem("Close Server");
		Close_Server.setActionCommand("Close_Server");
		Close_Server.addActionListener(new event());
		Close_Server.setEnabled(false);

		Connect_Server = new JMenuItem("Connect Server");
		Connect_Server.setActionCommand("Connect_Server");
		Connect_Server.addActionListener(new event());

		Connect.add(Create_Server);
		Connect.add(Close_Server);
		Connect.add(Connect_Server);

		Help = new JMenu("Help",false);
		About = new JMenuItem("About");
		About.setActionCommand("About");
		About.addActionListener(new event());

		Documentation = new JMenuItem("Documentation");
		Documentation.setActionCommand("Documentation");
		Documentation.addActionListener(new event());

		Help.add(About);
		Help.add(Documentation);

		this.add(Connect);
		this.add(Help);
	}

	void disableCreate_Server()
	{
		Create_Server.setEnabled(false);
		Close_Server.setEnabled(true);
	}

	void enableCreate_Server()
	{
		Create_Server.setEnabled(true);
		Close_Server.setEnabled(false);
	}

	void createServer(JFrame f)
	{
		event.d=1;
		Create_ServerDialog = new JDialog(f,"Create Server",true);
		Create_ServerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Create_ServerDialog.setBounds(app.a.getX(),app.a.getY()+50,230,150);								// Setting dimension of dialog window
		Create_ServerDialog.setResizable(false);
		Create_ServerDialog.setLayout(new GridLayout(3,1));
		portwarning.setText("Port value should be 1024 to 65535.");
		Create_ServerDialog.add(portwarning);//,BorderLayout.NORTH);

		JPanel cntr = new JPanel();
		JLabel prt = new JLabel("PORT : ",JLabel.CENTER);
		cntr.setLayout(new GridLayout(1,2,5,50));
		cntr.add(prt);
		port.setHorizontalAlignment(JLabel.CENTER);
		port.setBorder(BorderFactory.createLoweredBevelBorder());
		port.setBackground(Color.WHITE);
		cntr.add(port);

		Create_ServerDialog.add(cntr);//,BorderLayout.CENTER);

		JButton ok = new JButton("OK");
		ok.setActionCommand("Create_ServerDialog_OK");
		ok.addActionListener(new event());
		ok.addKeyListener(new event());
		JButton cancel = new JButton("Cancel");
		cancel.setActionCommand("Create_ServerDialog_Cancel");
		cancel.addKeyListener(new event());
		cancel.addActionListener(new event());
		JPanel btn = new JPanel();
		btn.setLayout(new FlowLayout());
		btn.add(ok);
		btn.add(cancel);
		Create_ServerDialog.add(btn);//,BorderLayout.SOUTH);
		Create_ServerDialog.show();
		event.d=0;
	}

	void closeServer()
	{
		Create_ServerDialog.dispose();
	}

	void createServer_warning(String s)
	{
		portwarning.setText(s);
	}

	String gettext()
	{
		return port.getText();
	}

	void settext(String text)
	{
		port.setText(text);
		return;
	}
}

class event implements ActionListener, KeyListener
{
	String s;
	static byte shift = 0;
	static byte d = 0;
	public void actionPerformed(ActionEvent a)
	{
		s = a.getActionCommand();

		switch(s)
		{
			case "Create_Server" : 	app.m.disableCreate_Server();
									app.chat.enable_sendbox();
									try{
									app.m.createServer(app.a);
									//System.out.println(app.a.getX()+"  "+app.a.getY());
									}
									catch(Exception e){}
									break;

			case "Close_Server" :	app.m.enableCreate_Server();
									app.chat.disable_sendbox();
									break;

			case "Create_ServerDialog_OK" :	app.m.createServer_warning("jfbkj"); //app.m.closeServer();
											break;

			case "Create_ServerDialog_Cancel" :	app.m.closeServer();
												break;
			default: break;
		}
			
		System.out.println(s);
	}

	public void keyTyped(KeyEvent e) 
	{
		//if(e.getSource() == new JButton())
			System.out.println(d+"  typed: "+(int)e.getKeyChar());

		if(d==1)
		{
			if((int)e.getKeyChar() >= 48 && (int)e.getKeyChar() <= 57)
			{
				if(app.m.gettext().length() <5)
					app.m.settext(app.m.gettext()+Character.toString((char)e.getKeyChar()));
				else
					beep();
			}
			else if((int)e.getKeyChar()==8)
			{
				if(app.m.gettext().length() >0)
					app.m.settext(app.m.gettext().substring(0,app.m.gettext().length()-1));
				else
					beep();
			}
			else
				beep();

				System.out.println("zero hai ");
		}

		if((int)e.getKeyChar()==10)
			if(shift==1)
			{
				app.chat.newline_sendbox();
	 			System.out.println("SHIFT + ENTER");
	 		}
	 		else
	 		{
	 			
	 			app.chat.receivebox_text(app.chat.clear_sendbox());
	 			System.out.println("ENTER"); 	
	 		}
	 		
    }

      public void keyPressed(KeyEvent e)
      {
      	if((int)e.getKeyChar()==65535)
      		shift=1;
      }
      public void keyReleased(KeyEvent e)
      {
      	if((int)e.getKeyChar()==65535)
      		shift=0;
      	//System.out.println("released"+(int)e.getKeyChar());
      }

      void beep()
      {
      	Toolkit.getDefaultToolkit().beep();
      }
}

class chatbox extends JPanel
{
	JTextArea receivebox, sendbox;
	chatbox()
	{
		receivebox = new JTextArea();
		receivebox.setLineWrap(true);
		receivebox.setWrapStyleWord(true);
		receivebox.setEditable(false);
		JScrollPane rscroll = new JScrollPane(receivebox);

		sendbox = new JTextArea();
		sendbox.setLineWrap(true);
		sendbox.setWrapStyleWord(true);
		sendbox.setEditable(true);
		sendbox.setRows(4);
		sendbox.addKeyListener(new event());
		sendbox.setEnabled(false);
		JScrollPane sscroll = new JScrollPane(sendbox);

		this.setLayout(new BorderLayout());
		this.add(rscroll,BorderLayout.CENTER);
		this.add(sscroll,BorderLayout.SOUTH);
	}

	void receivebox_text(String msg)
	{
		if(msg!=null)
		receivebox.append(msg);
	}

	String clear_sendbox()
	{
		String s = sendbox.getText();
		System.out.println(sendbox.getText()+" "+sendbox.getText().length());
		sendbox.setText(null);
		if(s.length()==1)
			return null;
		else
			return "You : "+s;
	}

	void newline_sendbox()
	{
		sendbox.append("\n");
	}

	void disable_sendbox()
	{
		clear_sendbox();
		sendbox.setEnabled(false);
		System.out.println("disable_sendbox");
	}

	void enable_sendbox()
	{
		sendbox.setEnabled(true);
		System.out.println("enable_sendbox");
	}
}