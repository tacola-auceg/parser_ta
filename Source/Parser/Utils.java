package Parser;

import java.awt.Component;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.*;
import java.util.ResourceBundle;

/**
 *  Contains the useful utilities of this package. Used to set the dialog boxes in the
 *  middle point of the monitor and to show the message windows to the user.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
public class Utils
{
	 public static final char[] WORD_SEPARATORS = {' ', '\t', '\n',
	    '\r', '\f', '.', ',', ':', '-', '(', ')', '[', ']', '{',
	    '}', '<', '>', '/', '|', '\\', '\'', '\"'};

      /**
      * Used to check whether a character is a separator of not.
      */
	  public static boolean isSeparator(char ch)
	  {
	    for (int k=0; k<WORD_SEPARATORS.length; k++)
	      if (ch == WORD_SEPARATORS[k])
	        return true;
	    return false;
  		}
/*

	public static Color invertColor(Color color)
	{
		int r=color.getRed();
		int g=color.getGreen();
		int b=color.getBlue();
		r=Math.abs(255-r);
		g=Math.abs(255-g);
		b=Math.abs(255-b);
		return new Color(r,g,b);
	}
	*/

	/**
	* Used to set a component in the middle of the monitor.
	*
	* @param d dimension of a component which has to be fixed in the middle of the monitor
	* @return Point point where the required component has to be placed in order to
	*				fix it in the middle of the monitor.
	*/
	public static Point getMiddle(Dimension d)
	{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		int x = (int)(screenSize.width-d.width)/2;
		int y = (int)(screenSize.height-d.height)/2;
		return new Point(x,y);
	}
/*
	public static Point getMiddle(Dimension d1,Dimension d2)
	{
		int x = (int)(d1.width-d2.width)/2;
		int y = (int)(d1.height-d2.height)/2;
		return new Point(x,y);
	}
	*/

    /**
	* Used to show the message dialogs to the user.
	*
	* @ param parent a reference to the main <code> Word </code> object
	* @ param msg message to be displayed in the message dialog.
	* @ param type type of the message dialog
	* @ param icon icon to be shown in the message dialog
	* @ param options command options to be displayed in the message dialog
	* @ param selectIndex index of the component to be focused.
	*/
	public static int showDialog(Component parent,String msg,int option,int type,Icon icon,
				Object[] options,int selectIndex)
	{
				// 	Contains the word bundle for the current local
				//ResourceBundle wordBundle = Word.wordBundle;

				//String messageTitle = "îõø£ù à÷¢÷¦´";//wordBundle.getString("MessageTitle");
				String messageTitle = "Error input";

				//String message="êó¤ò£ù à÷¢÷¦ì¢¬ì ªè£´è¢èõ£ ?";
				String message="Corrected input is in the output field. Do you want to parse the corrected input?";


				/*if(msg.indexOf("RHBD")!=-1)   // to show "replacement(s) have been done." message.
				  message=msg.substring(0,msg.indexOf("RHBD")-1)+" "+wordBundle.getString("RHBD");
				else
					message = wordBundle.getString(msg);
			*/
				JOptionPane p = new JOptionPane((Object)message,
					option, type, null,	options, options[0]);
				JDialog d = p.createDialog(parent,messageTitle);

				d.setResizable( false );
				d.show();
				Object selectedValue = p.getValue();

				if(selectedValue.equals(new Integer(-1)))
				{
					d.dispose();
					return JOptionPane.CANCEL_OPTION;
				}

				if(selectedValue == null)
				{
					d.dispose();
					return JOptionPane.CLOSED_OPTION;
				}

				//If there is not an array of option buttons:
				if(options == null)
				{
					if(selectedValue instanceof Integer)
					{
						d.dispose();
						return ((Integer)selectedValue).intValue();
					}
					d.dispose();
					return JOptionPane.CLOSED_OPTION;
				}

				//If there is an array of option buttons:
				for(int counter = 0, maxCounter = options.length;
					counter < maxCounter; counter++)
				{
					if(options[counter].equals(selectedValue))
					{
						d.dispose();
						return counter;
					}
				}
				d.dispose();
				return 0;
	}


}
