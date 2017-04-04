
import javax.swing.*;
import java.awt.event.*;
import java.awt.Toolkit;
import java.awt.*;
import java.util.*;
import java.io.*;

import common.*;
import analyser.*;
import Parser.*;

public class ParserAppletUI extends JApplet
{

	JTextArea text1 ;
	JTextArea text2 ;
	JTextArea jt_output;


	JButton but1 = new JButton("‹ÙË§¥");
	JButton but2 = new JButton("‹Ë¯¢¡");

	JScrollPane p1 ;
	JScrollPane p2 ;

	JPanel subpanel1 = new JPanel();
	JPanel subpanel2 = new JPanel();

	JPanel p4 = new JPanel();
	JPanel p5 = new JPanel();
	JPanel p6 = new JPanel();

	Color blk = new Color(0,0,0);
	Color ylw = new Color(255,0,230);

	Font f = new Font("TAB-Anna",4,16);
	//	ByteMeth bm = new ByteMeth();
	//tabconvert2 tc = new tabconvert2();
	boolean cojoin_flag_noun = false; // co join flag to know whether two nouns has to be join or not

	//public ParserAppletUI()
	public void init()
	{
			//super("Parser");

            System.out.println("\n\n");
			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();

			text1 = new JTextArea(3,53);
			text2 = new JTextArea(5,60);
			jt_output = new JTextArea(16,60);

			p1 = new JScrollPane(text1);
			p2 = new JScrollPane(text2);

			subpanel1.add(p1);

			subpanel2.setLayout(new BorderLayout());
			subpanel2.add(but1,BorderLayout.NORTH);
			subpanel2.add(but2,BorderLayout.SOUTH);

			subpanel1.setBackground(Color.gray);
			subpanel2.setBackground(Color.gray);

			p4.setLayout(new BorderLayout());
			p4.add(subpanel1,BorderLayout.WEST);
			p4.add(subpanel2,BorderLayout.EAST);

			p5.add(p2);

			but1.setBackground(Color.orange);
			but1.setForeground(Color.black);
			but1.setFont(f);

			/************************************************************************
			****** Action Listener For Button 1 *************************************
			## Stack wrdStack Used To Get word parsed by getWords().
			## each element is popped of the stack and converted to an array.
			## The array is sent to processWords() for further processing.
			*************************************************************************
			*************************************************************************/
			ActionListener buttonListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					Parser parser = new Parser();

					long bef,aft;

					Date date1 = new Date();
					bef = date1.getTime();
					System.out.println("Time before parse :"+bef);

					jt_output.setText(parser.Parse(text1.getText()));

					Date date2 = new Date();
					aft = date2.getTime();
					System.out.println("Time after parse  :"+aft);
					System.out.println("Time Taken by Parse: "+(aft - bef)+" milliseconds.");
					System.out.println("Time taken by Analyser: "+parser.analyser_time);
					System.out.println("\n\n");

				} // end of action performed
			};

			ActionListener buttonListener1 = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					text1.setText("");
					p6.removeAll();
					p6.repaint();
					text2.setText("");
				} // end of action performed
			};


			but1.addActionListener(buttonListener);
			but2.addActionListener(buttonListener1);

			but2.setBackground(Color.orange);
			but2.setForeground(Color.black);
			but2.setFont(f);

			p5.setBackground(Color.gray);
			text1.setBackground(Color.black);
			text1.setFont(f);
			text1.setForeground(Color.yellow);
			p4.setBackground(Color.gray);
			text2.setForeground(Color.yellow);
			text2.setBackground(Color.black);
			text2.setFont(f);
			text2.setEditable(false);


			p6.setBackground(Color.gray);
			p6.setFont(f);

            JScrollPane temp = new JScrollPane(jt_output);
 			p6.add(temp);
			jt_output.setBackground(Color.black);
			jt_output.setFont(f);
			jt_output.setForeground(Color.yellow);

			getContentPane().add(p4,"North");
			getContentPane().add(p6,"Center");
			getContentPane().add(p5,"South");

			setSize(d.width,d.height-20);
			show();
			//addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){System.exit(0);}});
	}

	public static void main(String a[])
	{
		new ParserUI();
	}



}

/********
## End of class module
********/