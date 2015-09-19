package JavaChat;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;

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
	}

	void enable_sendbox()
	{
		sendbox.setEnabled(true);
	}
}