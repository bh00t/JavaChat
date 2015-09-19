package JavaChat;

import java.io.*;
import java.net.*;

class socket implements Runnable
{
	ServerSocket serverSocket;
	Socket client =  new Socket();
	static byte s=0;//for thrd
	static byte x=0;//client is connect flag
	void create(int port)
	{
		try
		{
			serverSocket = new ServerSocket(port);
			app.m.disableCreate_Server();
			app.m.closeServer();
			app.chat.clear_receivebox();
			app.chat.receivebox_text("Waiting for client....");
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
			app.chat.clear_receivebox();
			app.chat.enable_sendbox();
			app.m.closeServer();
			app.chat.receivebox_text("CONNECTED....");
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
			else if(menubar.sc==0)
				client.close();
		}
		catch(Exception e){}
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
				app.chat.enable_sendbox();
				app.chat.receivebox_text("CONNECTED....");
				s=0;
			}
			
		}
		catch(IOException e){}
		catch(Exception e)
    	{
    		System.out.println("thred ka lfda");
    		e.printStackTrace();
    	}
	}

	void io(Socket client)
	{
		try{
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
                Thread.yield();
                if((an=in.readUTF())!=null)
                {
                	app.chat.receivebox_text(an);
                	if(event.focus==0)
                		event.beep();
                }
                if(status==false)
                	return;
            }
        }
        catch(SocketException e)
        {
        	app.chat.disable_sendbox();
        	app.chat.clear_receivebox();
        	app.chat.receivebox_text("DISCONNECTED....");
        	event.beep();
        	event.s.Close();
			app.m.enableCreate_Server();
        }   
        catch(IOException e)
        {
        	app.chat.disable_sendbox();
        	app.chat.clear_receivebox();
        	app.chat.receivebox_text("DISCONNECTED....");
        	event.beep();
        	event.s.Close();
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
        try
        {
	        while(status)
	        {
	            Thread.yield();
	            if(chatbox.m!=null)
	            {
	                    out.writeUTF(app.name+" : "+chatbox.m);
	                    chatbox.m=null;
	            }
	            if(status==false)
	                return ;
	        }
   }
        catch(SocketException e){}
        catch(IOException e){}
    }
}