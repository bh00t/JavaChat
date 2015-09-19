package JavaChat;

import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

class event implements ActionListener, KeyListener, FocusListener
{
	String str;
	static byte shift = 0;//shift key flag
	static byte d = 0; // dialogbox is open flag
	static byte focus =0;// focus flag
	static socket s = new socket();

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
									
									try
									{
										if(rni.status==true)
										rni.in.close();
									}
									catch(IOException e)
									{System.out.println("thred ka lfda");}
									rni.status=false;
									rno.status=false;
									app.m.enableCreate_Server();
									app.chat.disable_sendbox();
									break;

			case "Create_ServerDialog_OK" :	if(menubar.sc==1)
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
											else if(menubar.sc==0)
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
			case "About" :					app.m.about(app.a);
											break;

			case "Create_ServerDialog_Cancel" :
			case "About_OK" :					app.m.closeServer();
												break;

			case "Connect_Server" : try{
											app.m.createServer(app.a,(byte)2);
										}
									catch(Exception e){}

									break;
			default: break;
		}
			
	}

	public void keyTyped(KeyEvent e) 
	{
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
		}

		if((int)e.getKeyChar()==10)
			if(shift==1)
				app.chat.newline_sendbox();
	 		else
	 			app.chat.receivebox_text(app.chat.clear_sendbox());
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
      }

      public void focusGained(FocusEvent e)
      {focus = 1;}
      public void focusLost(FocusEvent e)
      {focus = 0;}

      static void beep()
      {
      	Toolkit.getDefaultToolkit().beep();
      }
}