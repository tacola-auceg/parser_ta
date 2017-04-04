
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.rtf.*;

/**
 * Used to set the file filers in the file chooser.
 *  @ version 5.12.2002
 * 	@ author RCILTS-Tamil,MIT.
 */
class SimpleFilter extends javax.swing.filechooser.FileFilter
{
	/** contains the description of the file extension*/
	private String m_description = null;
	/** contains the extension of the file.*/
	private String m_extension = null;

	/**
	* Constructs an object of <code>  SimpleFilter </code>
	* with the file extension and description
	* @param extension extension of the file type for which this filter is needed
	* @param description description of the file extension.
	*/
	public SimpleFilter(String extension, String description)
	{
		m_description = description;
		m_extension = "." + extension.toLowerCase();
	}

	/**
	* Used to get the description of the file filter.
	*/
	public String getDescription()
	{
		String temp = null;
		try
		{
			temp = "Text Files(*.txt)";
		}
		catch(Exception e)
		{
			System.out.println( e +"\n--->e at simplefilter");
			//e.printStackTrace();
		}
		return temp;
		//return m_description;
	}

	/**
	* Used to identify that the file has to be accepted or not.
	*
	* @param f file which has to be checked with this file filter.
	*/
	public boolean accept(File f)
	{
		if (f == null)
			return false;
		if (f.isDirectory())
			return true;
		String type = f.getName().toLowerCase();

		if( (type.endsWith("htm") ) && ( m_extension.equals(".html")) )
				return true;

		if( (type.endsWith("doc") ) && ( m_extension.equals("DOC")) )
				return true;

		if(( (type.endsWith("gif") ) || (type.endsWith("jpeg")) ) && ( m_extension.equals(".jpg")) )
				return true;

		return type.endsWith(m_extension);

	}

	/**
	* Used to get the extension of the file.
	*/
	public String getExtension()
	{
		return m_extension;
	}
}