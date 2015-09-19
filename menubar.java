package JavaChat;

import java.net.*;
import javax.swing.*;
import java.awt.*;

class menubar extends JMenuBar
{
	JMenu Connect,Help;
	JMenuItem Create_Server, Disconnect,Connect_Server, About, Documentation;
	JDialog Create_ServerDialog, Connect_ServerDialog;
	JLabel warning, port;
	JTextField ip,c_port;
	static byte sc=0; // flag for server and client dialogbox
	menubar()
	{
		warning = new JLabel("",JLabel.CENTER);
		port =new JLabel();
		Font font2=new Font(port.getFont().getName(),port.getFont().getStyle(),15);
		port.setFont(font2);
		port.setBackground(Color.WHITE);
		port.addKeyListener(new event());

		Connect = new JMenu("Connect",false);

		Create_Server = new JMenuItem("Create Server");
		Create_Server.setActionCommand("Create_Server");
		Create_Server.addActionListener(new event());

		Disconnect = new JMenuItem("Disconnect");
		Disconnect.setActionCommand("Disconnect");
		Disconnect.addActionListener(new event());
		Disconnect.setEnabled(false);

		Connect_Server = new JMenuItem("Connect Server");
		Connect_Server.setActionCommand("Connect_Server");
		Connect_Server.addActionListener(new event());

		Connect.add(Create_Server);
		Connect.add(Disconnect);
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
		Disconnect.setEnabled(true);
		Connect_Server.setEnabled(false);
	}

	void enableCreate_Server()
	{
		Create_Server.setEnabled(true);
		Disconnect.setEnabled(false);
		Connect_Server.setEnabled(true);
	}

	void createServer(JFrame f,byte op)
	{
		event.d=1;
		Create_ServerDialog = new JDialog(f,"Create Server",true);
		Create_ServerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Create_ServerDialog.setBounds(app.a.getX(),app.a.getY()+50,230,150);								// Setting dimension of dialog window
		Create_ServerDialog.setResizable(false);
		Create_ServerDialog.setLayout(new GridLayout(3,1));

		warning.setText("Port value should be 1024 to 65535.");
		warning.setForeground(Color.black);
		Font font=new Font(warning.getFont().getName(),warning.getFont().getStyle(),10);
		warning.setFont(font);
		Create_ServerDialog.add(warning);

		JPanel cntr = new JPanel();
		JLabel prt = new JLabel("PORT : ",JLabel.CENTER);
		if(op==1)
		{
			sc=1;
			cntr.setLayout(new GridLayout(1,2,5,50));
			cntr.add(prt);
			port.setHorizontalAlignment(JLabel.CENTER);
			port.setBorder(BorderFactory.createLoweredBevelBorder());
			port.setBackground(Color.WHITE);
			port.setText("");
			cntr.add(port);
		}
		else if(op==2)
		{
			sc=0;
			cntr.setLayout(new GridLayout(2,2));
			JLabel ip_lbl=new JLabel("IP Address : ",JLabel.CENTER);
			cntr.add(ip_lbl);
			ip = new JTextField();
			cntr.add(ip);
			cntr.add(prt);
			c_port = new JTextField();
			cntr.add(c_port);
		}

		Create_ServerDialog.add(cntr);

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
		Create_ServerDialog.add(btn);
		Create_ServerDialog.show();
		event.d=0;
	}

	void about(JFrame f)
	{
		Create_ServerDialog = new JDialog(f,"Create Server",true);
		Create_ServerDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		Create_ServerDialog.setBounds(app.a.getX(),app.a.getY()+50,250,160);								// Setting dimension of dialog window
		Create_ServerDialog.setResizable(false);
		Create_ServerDialog.setLayout(new BorderLayout());

		ImageIcon calicon=new ImageIcon("JavaChat/javachat.png");
		Create_ServerDialog.setIconImage(calicon.getImage());

		JTextArea abt = new JTextArea();
		Font font=new Font(abt.getFont().getName(),abt.getFont().getStyle(),13);
		abt.setFont(font);
		abt.setLineWrap(true);
		abt.setWrapStyleWord(true);
		abt.setEditable(false);
		abt.setText("JavaChat 1.0 is open source chat application developed using Java language by Rishabh Tripathi.\n"+"\n"+" It's all files and codes are open source.");
		Create_ServerDialog.add(abt, BorderLayout.CENTER);
		JButton ok = new JButton("OK");
		ok.setActionCommand("About_OK");
		ok.addActionListener(new event());
		ok.addKeyListener(new event());
		JPanel btn = new JPanel();
		btn.setLayout(new FlowLayout());
		btn.add(ok);
		btn.setBackground(Color.WHITE);
		Create_ServerDialog.add(btn, BorderLayout.SOUTH);
		Create_ServerDialog.show();
	}

	void closeServer()
	{
		Create_ServerDialog.dispose();
	}

	void show_warning(String s)
	{
		warning.setForeground(Color.red);
		Font font=new Font(warning.getFont().getName(),warning.getFont().getStyle(),12);
		warning.setFont(font);
		if(s.length()!=0)
			warning.setText(s);
	}

	String gettext()
	{
		return port.getText();
	}

	String[] c_gettext()
	{
		String[] s = new String[2];
		s[0]=ip.getText();
		s[1]=c_port.getText();
		return s;
	}

	void settext(String text)
	{
		port.setText(text);
		return;
	}

	boolean checkport()
	{
		if(sc==1)
		{
			if(gettext().length()==0 || Integer.parseInt(gettext())>65535 || Integer.parseInt(gettext())<1025)
			{
				show_warning("");
				event.beep();
				return false;
			}
			else
			{
				socket.s=1;
				return true;
			}
		}
		else if(sc==0);
		{
			String[] s = c_gettext();
			if(s[0].length()<7 || s[0].length()>15)
			{
				show_warning("invalid IP address");
				event.beep();
				return false;
			}
			else
			{
				try
				{
					if(s[1].length()==0 || Integer.parseInt(s[1])>65535 || Integer.parseInt(s[1])<1025)
					{
						show_warning("invalid port");
						event.beep();
						return false;
					}
					else 
					{
						 InetAddress IP = InetAddress.getByName(s[0]);
						return true;
					}
					
				}
				catch(NumberFormatException e)
				{
					show_warning("invalid Port");
					event.beep();
					return false;
				}
				catch(UnknownHostException e)
				{
					show_warning("invalid IP address");
					event.beep();
					return false;
				}
			}
		}
	}
}