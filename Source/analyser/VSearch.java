package analyser;

import java.io.*;
import java.util.*;

/**
  * program to search the dictionary for defective types of verb category
  */
public class VSearch
{
	public VSearch(){}

	//public static ResourceBundle fileBundle = ResourceBundle.getBundle("fileBundle");


	public boolean dicSearch(String word,String fileName)
	{
		String temp="";
		BufferedReader dic = null;//FileReader dic = null;

		try
		{
			try
			{
				dic = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(fileName)));
				//dic = new FileReader(FileLocator.getFile(fileName).getPath());
			}
			catch(Exception e)
			{
				System.out.println(e+"---->ex at VSearch-dicSearch ");
			}
			StreamTokenizer input = new StreamTokenizer(dic);
			int tokentype = 0;
			while((tokentype = input.nextToken()) != StreamTokenizer.TT_EOF)
			{
				if(tokentype == StreamTokenizer.TT_WORD)
				{
					temp=input.sval;
					if(temp.equals(word))
						return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
}
