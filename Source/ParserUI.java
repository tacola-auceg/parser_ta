import java.io.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import javax.swing.event.*;
import java.awt.event.*;
import java.awt.Toolkit;


//import common.*;
import analyser.*;
import Parser.*;
import TREEUI.*;

public class ParserUI extends JFrame
{

	JTabbedPane jtp_display = new JTabbedPane();

	JPanel jp_north = new JPanel();
	JPanel jp_south = new JPanel();
	JPanel jp_center = new JPanel();
	JPanel jp_East = new JPanel();

	JTextArea jt_input ;
	JTextArea jt_refrence ;
	JTextArea jt_output;

	JButton fileOpenButton = new JButton("«è£ð¢¹/File");
	JButton sentenceButton = new JButton("Ü´î¢î õ£è¢è¤òñ¢/Next Sentence ");
	JButton parseButton = new JButton("Üôè¤´/Parse");
	JButton helpButton = new JButton("àîõ¤/Help");
	JButton clearButton = new JButton("Üèø¢Á/Clear");
	JButton exitButton = new JButton("ªõ÷¤«òÁ/Exit");

	JScrollPane inputScrollPane ;
	JScrollPane outputScrollPane ;

	Color blk = new Color(0,0,0);
	Color ylw = new Color(255,0,230);

	File fileToProcess ;
	String text;
	// index of the new line character i the text.
	int newLineIndex=0;
	Font defaultFont ;

	public ParserUI()
	{
			super("Parser");
           	try
           {
			InputStream fontStream = getClass().getResourceAsStream("TABANNA.TTF");
			Font tempFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
			defaultFont = tempFont.deriveFont(Font.PLAIN, 16);

			Toolkit tk = Toolkit.getDefaultToolkit();
			Dimension d = tk.getScreenSize();

			jt_input = new JTextArea(3,53);
			jt_refrence = new JTextArea(5,60);
			jt_output = new JTextArea(16,60);

			inputScrollPane = new JScrollPane(jt_input);
			outputScrollPane = new JScrollPane(jt_refrence);

			jp_East.setLayout(new GridLayout(13, 1));
			jp_East.setBackground(Color.gray);

			jp_north.setLayout(new BorderLayout());
			jp_north.add(inputScrollPane);


			//jp_south.add(outputScrollPane);

			jp_East.add(new Label(""));
			jp_East.add(fileOpenButton);
			jp_East.add(sentenceButton);
			jp_East.add(parseButton);
			jp_East.add(helpButton);
			jp_East.add(clearButton);
			jp_East.add(exitButton);

			fileOpenButton.setBackground(Color.orange);
			fileOpenButton.setForeground(Color.black);
			fileOpenButton.setFont(defaultFont);

			sentenceButton.setBackground(Color.orange);
			sentenceButton.setForeground(Color.black);
			sentenceButton.setFont(defaultFont);

			parseButton.setBackground(Color.orange);
			parseButton.setForeground(Color.black);
			parseButton.setFont(defaultFont);

			helpButton.setBackground(Color.orange);
			helpButton.setForeground(Color.black);
			helpButton.setFont(defaultFont);

			/* listens the cursor's position in the input textarea and enables the Parse button.   */
			CaretListener curentplace = new CaretListener()
				{
					public void caretUpdate(CaretEvent e)
					{
						if(jt_input.getText().length() == 0)
							parseButton.setEnabled(false);
						else
							parseButton.setEnabled(true);
					}
				};
			jt_input.addCaretListener(curentplace);



			ActionListener fileListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent ev)
					{
						JFileChooser fileChooser = new JFileChooser();
						fileChooser.setCurrentDirectory(new File(System.getProperty("user.dir")));
						fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

						SimpleFilter textFilter = new SimpleFilter("txt","TextFiles");
						fileChooser.addChoosableFileFilter(textFilter);
						fileChooser.addKeyListener(new KeyAdapter()
							{		// adds the key listener to close the dialog while escape key is entered.
								public void keyPressed(KeyEvent ke)
								{
									if(ke.getKeyCode() == 27)//Esc
									{
										ke.getComponent().setVisible(false);
									}
								}
							});

						int result = fileChooser.showOpenDialog(getFrame());
						if (result == JFileChooser.APPROVE_OPTION)
						{
							File filename = fileChooser.getSelectedFile();
							if (filename == null)
							{
								return;
							}
							try
							{
								fileToProcess = new File(filename.getAbsolutePath());
								setTitle();
								BufferedReader in = new BufferedReader( new FileReader( fileToProcess) );

								String tempText;
								text = "";
								newLineIndex=0;
								while( (tempText =in.readLine() )!=null)
									text = text +" "+tempText;
								sentenceButton.setEnabled(true);

							}
							catch(Exception e)
							{
								System.out.println(e+"-----> e at file opening");
							}
						}
					} // end of action performed
				};

			fileOpenButton.addActionListener(fileListener);

			ActionListener sentenceListener = new ActionListener()
				{
					public void actionPerformed(ActionEvent ev)
					{
						extractSentence();
						parseButton.setEnabled(true);
					} // end of action performed
				};
			sentenceButton.addActionListener(sentenceListener);

			ActionListener buttonListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					Parser parser = new Parser();
					String temp = parser.DoPreprocess(jt_input.getText());
					if(temp!= null)
					{
						jt_output.setText(temp);

						//Object[] options = {"Ýñ¢ ","Þô¢¬ô"};
						Object[] options = {"Yes","NO"};

						// gets the user option

						String messageTitle = "Error Input";

						String message = " Error in Input. Corrected input is in the output field. Do you want to parse the corrected input?";

						/*
						JOptionPane p = new JOptionPane((Object)message,
							JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,	options, options[0]);
						JDialog d = p.createDialog(null,messageTitle);

						d.setResizable( false );
						d.show();
						Object selectedValue = p.getValue();

						System.out.println("  selected value :"+selectedValue);
						if(selectedValue == null )
							return;

						int option = ((Integer)selectedValue).intValue();

						if(option == JOptionPane.NO_OPTION)
						{
							System.out.println(" NO");
						}
						else
							System.out.println(" YES");

						*/

						int overwrite = Utils.showDialog(null,"aa",
														JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null,
														options, 1);

						if (overwrite == JOptionPane.CANCEL_OPTION || overwrite == JOptionPane.NO_OPTION)
						{
								//System.out.println(" NNNN");
							return;
						}
						//else
						//	System.out.println(" YYYY");

						jt_input.setText(temp);
								ParseSentence();


					}
					else
						ParseSentence();
				} // end of action performed
			};

			parseButton.addActionListener(buttonListener);

			ActionListener buttonListener1 = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					jt_input.setText("");
					//jp_center.removeAll();
					jp_center.repaint();
					jt_refrence.setText("");
					jt_output.setText("");
				} // end of action performed
			};

			clearButton.addActionListener(buttonListener1);

			ActionListener exitListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					System.exit(0);
				} // end of action performed
			};

			exitButton.addActionListener(exitListener);

			ActionListener helpListener = new ActionListener()
			{
				public void actionPerformed(ActionEvent ev)
				{
					ShowHelp();
				} // end of action performed
			};
			helpButton.addActionListener(helpListener);

			clearButton.setBackground(Color.orange);
			clearButton.setForeground(Color.black);
			clearButton.setFont(defaultFont);

			exitButton.setBackground(Color.orange);
			exitButton.setForeground(Color.black);
			exitButton.setFont(defaultFont);

			jp_south.setBackground(Color.gray);
			jt_input.setBackground(Color.black);
			jt_input.setFont(defaultFont);
			jt_input.setForeground(Color.yellow);
			jp_north.setBackground(Color.gray);
			jt_refrence.setForeground(Color.yellow);
			jt_refrence.setBackground(Color.black);
			jt_refrence.setFont(defaultFont);
			jt_refrence.setEditable(false);

			sentenceButton.setEnabled(false);
			parseButton.setEnabled(false);

			jp_center.setBackground(Color.gray);
			jp_center.setFont(defaultFont);

            //JScrollPane temp = new JScrollPane(jt_output);

            Component text_display = makeTextPanel();

 			//jp_center.add(temp);

			ImageIcon icon = new ImageIcon("middle.gif");
 		    jtp_display.addTab("", icon, text_display , "");
        	jtp_display.setSelectedIndex(0);

          /*  TreeDisplay td = new TreeDisplay();
            td.setSize(1000,1000);
            JScrollPane jsp_temp = new JScrollPane();
            jsp_temp.setSize(1000,1000);
            jsp_temp.add(td);
            jtp_display.addTab("Tree", icon, jsp_temp, "");

        	Component panel2 = makeTextPanel("");//Blah blah");
			jtp_display.addTab("Tree1", icon, panel2, "");

			Component panel3 = makeTextPanel("");//Blah blah blah");
			jtp_display.addTab("Tree2", icon, panel3, "");
			*/
			jp_center.setLayout(new GridLayout(1, 1));
			jp_center.add(jtp_display);


			jt_output.setBackground(Color.black);

			defaultFont = tempFont.deriveFont(Font.PLAIN, 12);
			jt_output.setFont(defaultFont);
			jt_output.setForeground(Color.yellow);

			getContentPane().add(jp_north,"North");
			getContentPane().add(jp_center,"Center");
			getContentPane().add(jp_south,"South");
			getContentPane().add(jp_East,"East");

			setSize(d.width,d.height-20);
			show();
			addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent we){System.exit(0);}});
		}
		catch(Exception ee)
		{
			System.out.println("ex at heeeere");
			//ee.printStackTrace();
		}
	}

	protected Component makeTextPanel()
    {
        JPanel panel = new JPanel(false);
        //JLabel filler = new JLabel(text);
        //filler.setHorizontalAlignment(JLabel.CENTER);
        JScrollPane temp = new JScrollPane(jt_output);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(temp);
        return panel;
    }



	protected Component makeTextPanel(String text)
    {
        JPanel panel = new JPanel(false);
        JLabel filler = new JLabel(text);
        filler.setHorizontalAlignment(JLabel.CENTER);
        panel.setLayout(new GridLayout(1, 1));
        panel.add(filler);
        return panel;
    }


	public String getProperOutput(ArrayList parser_output)
	{
			String output = new String();
			int tab_size = 0;

			for(int position = 0 ; position<parser_output.size()-1 ; position++)
			{
				String word = parser_output.get(position).toString();
				String next_word = parser_output.get(position+1).toString();
				if(word.endsWith("("))
					tab_size++;
				else if( ( word.startsWith(")"))  && ( next_word.startsWith(")") ) )
				{
					//System.out.println("	88888888888888888888   :  "+position+"   "+word+"  "+next_word);
					tab_size --;
				}
				else if( next_word.startsWith(")"))
				    tab_size --;
				/*
				else if( ( word.startsWith(")"))  && ( !next_word.startsWith(")") ) )
				{
					System.out.println("	88888888888888888888   :  "+position+"   "+word+"  "+next_word);
					tab_size = tab_size;
				}
				else if(word.startsWith(")") )
					tab_size--;
				else
					tab_size--;
					*/


				if(  (!word.startsWith("(")) && (!word.startsWith(")")) && ( next_word.startsWith("(") )  )
					output = output+word;
				else
				{
					output = output+word+"\n";
					for(int i = 0 ; i<tab_size ; i++)
						output = output+"             ";  //output = output+"\t";
				}

			}
			output = output + parser_output.get(parser_output.size()-1);

			return output;
	}


	public static void main(String a[])
	{
		new ParserUI();
	}


	public JFrame getFrame()
	{
		return this;
	}

	public void setTitle()
	{
		this.setTitle(fileToProcess.toString());
	}


	public void ParseSentence()
	{
		try
		{
			Parser parser = new Parser();

			long bef,aft;

			Date date1 = new Date();
			bef = date1.getTime();
			System.out.println("\n=====================================================================");
		//	System.out.println("Time before parse :"+bef);

			/*
			ArrayList parser_output = parser.Parse(jt_input.getText());
			if(parser_output == null)
			{
				jt_output.setText(" êó¤ò£ù õ£è¢è¤òñ¢ Þô¢¬ô.\n Not a proper sentence.");
				return;
			}
			jt_output.setText(getProperOutput(parser_output));
			*/

			jt_output.setText(parser.ParseTheSentence(jt_input.getText()));

			Date date2 = new Date();
			aft = date2.getTime();
		//	System.out.println("Time after parse  :"+aft);
			System.out.println("Time Taken by Parse: "+(aft - bef)+" milliseconds.");
			System.out.println("Time taken by Analyser: "+parser.analyser_time);
			System.out.println("=====================================================================");
			System.out.println("\n");
		}
		catch(Exception e)
		{
			System.out.println(e+"---> ex at parserUI");

		}
	}

	public void extractSentence()
	{
		//System.out.println(" comes here text :"+text.length()+"    :"+newLineIndex);
		int previousIndex = newLineIndex;
		if(newLineIndex != -1)
		{
			newLineIndex = text.indexOf(".",newLineIndex+1);
			System.out.println("   "+previousIndex+"    "+newLineIndex);
			if(newLineIndex == -1)
				newLineIndex = -1;
			if(newLineIndex != -1)
			{
				String sentence = text.substring(previousIndex+1,newLineIndex);
				jt_input.setText(sentence);
			}
		}
		else
		{
			sentenceButton.setEnabled(false);
		}

	}

	public void ShowHelp()
	{
		try
		{
			File filename = new File("./Help.txt");
			if (filename == null)
			{
				return;
			}
			fileToProcess = new File(filename.getAbsolutePath());
			setTitle();
			BufferedReader in = new BufferedReader( new FileReader( fileToProcess) );

			String tempText;
			String helpText= new String();
			while( (tempText =in.readLine() )!=null)
				helpText = helpText +"\n"+tempText;
			jt_output.setText(helpText);
		}
		catch(Exception e)
		{
			System.out.println(e+"-----> e at file opening");
		}

	}




}

/********
## End of class module
********/