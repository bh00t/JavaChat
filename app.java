package JavaChat;

import java.io.*;
import java.net.*;
import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

class app
{
	static menubar m = new menubar();
	static String name;
	static chatbox chat = new chatbox();
	static JFrame a = new JFrame("JavaChat");
	app() throws UnknownHostException,IOException
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

		JScrollPane scrollPane = new JScrollPane(chat);
		cntnr.add(scrollPane,BorderLayout.CENTER);

		a.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		a.setResizable(false);
		a.setBounds(1000,58,300,613);
		a.setVisible(true);

	}

	public static void main(String[] args) throws UnknownHostException,Exception
	{
		app a =new app();
	}
}

class menubar extends JMenuBar
{
	JMenu Connect,Help;
	JMenuItem Create_Server, Disconnect,Connect_Server, About, Documentation;
	JDialog Create_ServerDialog, Connect_ServerDialog;
	JLabel warning, port;
	JTextField ip,c_port;
	static byte sc=0;
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
		Create_ServerDialog.add(warning);//,BorderLayout.NORTH);

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
			sc=2;
			cntr.setLayout(new GridLayout(2,2));
			JLabel ip_lbl=new JLabel("IP Address : ",JLabel.CENTER);
			cntr.add(ip_lbl);
			ip = new JTextField();
			cntr.add(ip);
			cntr.add(prt);
			c_port = new JTextField();
			cntr.add(c_port);
		}

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
		//System.out.println()
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
				server.s=1;
				return true;
			}
		}
		else if(sc==2);
		{
			System.out.println("clnt "+sc);
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

class event implements ActionListener, KeyListener
{
	String str;
	static byte shift = 0;//shift key flag
	static byte d = 0; // dialogbox is open flag
	static server s = new server();

	public void actionPerformed(ActionEvent a)
	{
		str = a.getActionCommand();

		switch(str)
		{
			case "Create_Server" : 	
									try{
											app.m.createServer(app.a,(byte)1);
										}
									catch(Exception e){}

									break;

			case "Disconnect" :		s.Close();
									rni.status=false;
									rno.status=false;
									try
									{
										rni.in.close();
									}
									catch(IOException e)
									{}
									System.out.println("close ho gya");
									app.m.enableCreate_Server();
									app.chat.disable_sendbox();
									break;

			case "Create_ServerDialog_OK" :	//System.out.println(app.m.gettext().length());
											if(menubar.sc==1)
											{
												if(app.m.checkport())
												{
													s.create(Integer.parseInt(app.m.gettext()));
													if(s.x==0)
													{
														Thread z = new Thread(s);
														z.start();
													}
												}
											}
											else if(menubar.sc==2)
												if(app.m.checkport())
												{
													String[] str =app.m.c_gettext();
													try
													{
														s.c_create(InetAddress.getByName(str[0]),Integer.parseInt(str[1]));
													}
													catch(Exception e){}
												}
											break;

			case "Create_ServerDialog_Cancel" :	app.m.closeServer();
												break;

			case "Connect_Server" : try{
											app.m.createServer(app.a,(byte)2);
										}
									catch(Exception e){}

									break;
			default: break;
		}
			
		//System.out.println(s);
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
			else if((int)e.getKeyChar()==10)
				{
					if(menubar.sc==1)
					{
						if(app.m.checkport())
						{
							s.create(Integer.parseInt(app.m.gettext()));
							if(s.x==0)
							{
								Thread z = new Thread(s);
								z.start();
							}
						}
					}
				}

			else
				beep();

				//System.out.println("zero hai ");
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

      static void beep()
      {
      	Toolkit.getDefaultToolkit().beep();
      }
}

class chatbox extends JPanel
{
	JTextArea receivebox, sendbox;
	static String m; 
	chatbox()
	{
		receivebox = new JTextArea();
		DefaultCaret caret = (DefaultCaret)receivebox.getCaret();
 		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
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
		receivebox.append(msg+"\n");
	}

	void clear_receivebox()
	{
		receivebox.setText("");
	}

	String clear_sendbox()
	{
		String s = sendbox.getText();
	//	System.out.println(sendbox.getText()+" "+sendbox.getText().length());
		sendbox.setText(null);
		if(s.length()<=1)
				return null;
		else
		{
			m=s;
			return "You : "+s;
		}
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

class server implements Runnable
{
	ServerSocket serverSocket;
	Socket client =  new Socket();
	static byte s=0;
	static byte x=0;
	void create(int port)
	{
		try
		{
			serverSocket = new ServerSocket(port);
			System.out.println("bangya server");
			app.m.disableCreate_Server();
			x=0;
		}
		catch(IOException e)
		{
			app.m.show_warning("Please enter another Port.");
			event.beep();
			x=1;
		}
	}

	void c_create(InetAddress IP, int port)
	{
		try
		{
			client = new Socket(IP, port);
			app.m.disableCreate_Server();
			io(client);
			System.out.println("connected");
			app.chat.enable_sendbox();
		}
		catch(Exception e)
		{
			app.m.show_warning("Please enter another IP or Port.");
			event.beep();
			x=1;
		}
	}

	void Close()
	{
		try
		{
			s=0;
			Thread.sleep(500);
			if(menubar.sc==1)
				serverSocket.close();
			else if(menubar.sc==2)
				client.close();
			System.out.println("Disconnect");
		}
		catch(Exception e)
		{
			System.out.println(serverSocket.isClosed()+"???");
		}
	}

	public void run()
	{
		try
		{
			
			while(s==1)
			{
				Thread.yield();
				if(s==1)
				client = serverSocket.accept();
				app.m.disableCreate_Server();
				io(client);
				System.out.println("connected");
				app.chat.enable_sendbox();
				s=0;
				//break;
			}
			
		}
		catch(IOException e)
		{
			System.out.println("Exception hai");
			e.printStackTrace();
		}
	}

	void io(Socket client)
	{
		try{
			System.out.println(Thread.activeCount()+"ANAND");

			OutputStream outToclnt = client.getOutputStream();
	        DataOutputStream out = new DataOutputStream(outToclnt);
	        
	       	InputStream inFromclnt = client.getInputStream();
	        DataInputStream in = new DataInputStream(inFromclnt);

	        Thread inpt = new Thread(new rni(in));
	        Thread oupt = new Thread(new rno(out));
	       	inpt.start();
	        oupt.start();
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
	}
}

class rni implements Runnable
{
    String an;
    static DataInputStream in;
    static boolean status;
    rni(DataInputStream in)
    {
        this.in=in;
        status=true;
    }

    public void run()
    {
        try
        {
            while(status)
            {
            	System.out.println("This is RNI");
                Thread.yield();
                if((an=in.readUTF())!=null)
                {
                    //if(an.compareTo("bye")==0)
                	app.chat.receivebox_text(an);
                	System.out.println(status+" ds");
                   // System.out.println(name+" : "+an);
                }
                if(status==false)
                	return;
            }
        }
        catch(SocketException e)
        {
        	System.out.println("input ki exception hai\n");
        	app.chat.disable_sendbox();
        	app.chat.clear_receivebox();
        	app.chat.receivebox_text("DISCONNECTED....");
        	event.beep();
        	event.s.Close();
        	System.out.println("close ho gya");
			app.m.enableCreate_Server();
            //e.printStackTrace();
        }   
        catch(IOException e)
        {
        	System.out.println("INPUT/output ki exception hai\n");
            System.out.println("input ki exception hai\n");
        	app.chat.disable_sendbox();
        	app.chat.clear_receivebox();
        	app.chat.receivebox_text("DISCONNECTED....");
        	event.beep();
        	event.s.Close();
        	System.out.println("close ho gya");
			app.m.enableCreate_Server();
        }
    }
}
class rno implements Runnable
{
    DataOutputStream out;
    static boolean status;
    rno(DataOutputStream out)
    {
        this.out=out;
        status=true;
    }

    public void run()
    {
        try{
        while(status)
        {
        	//System.out.println("This is RN0");
            Thread.yield();
            if(chatbox.m!=null)
            {
                    out.writeUTF(app.name+" : "+chatbox.m);
                    chatbox.m=null;
                    //System.out.println(status);
            }
            if(status==false)
                return ;
        }
   }
        catch(SocketException e)
        {
        	System.out.println("output ki exception hai\n");
           // e.printStackTrace();
        }
        catch(IOException e)
        {
        	System.out.println("input/OUTPUT ki exception hai\n");
            //e.printStackTrace();
        }
    }
}