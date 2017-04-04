package Parser;

import java.util.*;
import javax.swing.*;
import TREEUI.*;

/** This class is the main class in parser module through which other program can
 *  access.
*/
public class Parser
{
	Tokenizer tokenizer = new Tokenizer();
	TokenParser tokenparser = new TokenParser();
	BuildTreeNodes build_tree = new BuildTreeNodes();

	public long analyser_time =0;

	/** no_words : stores the no of words in the sentence */
	int total_no_words =0;

	ArrayList al_all_words_detail;


	public ArrayList Parse(String sentence)
	{
		String temp = sentence;
		temp = temp.trim();
		if(temp.length()==0)	// if 1
			return null;
		if( (temp.charAt(0)<170) || (temp.charAt(0)>255) )	// if 2
			return null;

		if(sentence.endsWith(".")) // if 3
			sentence = sentence.substring(0,sentence.length()-1);

		// String correctedSentence = DoPreprocess(sentence);

		ArrayList parser_output = new ArrayList();
		try
		{
			al_all_words_detail = tokenizer.Tokenize(sentence,this);

			total_no_words = al_all_words_detail.size();

			parser_output = tokenparser.getParsedText(this);
			/*Node parent_node = build_tree.buildTreeNodes(parser_output);

			System.out.println("****************");
			build_tree.preorder(parent_node);
			System.out.println("****************");
			*/
			//System.out.println("----------------");
			//for( int j = 0 ; j<parser_output.size() ; j++)
			//	System.out.println(" "+j+"  "+parser_output.get(j));
			//System.out.println("----------------");
			Validate(parser_output);
		}
		catch(Exception e)
		{
				System.out.println(e+" ---> e at Parser() ");
				e.printStackTrace();
		}
		return parser_output;
		//return getProperOutput(parser_output);
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

	public void Validate(ArrayList parser_output)
	{

		Stack stk_braces = new Stack();
		if(parser_output.size() == 0)
		   return;
		//System.out.println(" --------- ");
		for(int position = 0; position < parser_output.size() ; position++)
		{
			String word = parser_output.get(position).toString();
			//System.out.println("  "+position+" :"+word);
			if(word.endsWith("("))
				stk_braces.push("(");
			else if( word.startsWith(")"))
			{
				if( stk_braces.empty() )
				{
				     //1 System.out.println("====  INCURRECT PARSE  1====");
				     //String tr = "<html><body font face=TAB_ELANGO_Barathi> êó¤ò£è ð°è¢èð¢ðìõ¤ô¢¬ô.    </body></html>" ;
				     String tr = "<html><body font face=TAB_ELANGO_Barathi> Braces mismatched.    </body></html>" ;
					//1 JOptionPane.showMessageDialog(null,tr);
				 }
				else
					stk_braces.pop();
					//String temp = stk_braces.pop().toString();
		    }
		}

		if(stk_braces.empty())
		{
			//1 System.out.println("====  CURRECT PARSE  ====");
			//String tr = "<html><body font face=TAB_ELANGO_Barathi> êó¤ò£è ð°è¢èð¢ðì¢ì¶.    </body></html>" ;
			String tr = "<html><body font face=TAB_ELANGO_Barathi>   Braces matched.    </body></html>" ;
			//JOptionPane.showMessageDialog(null,tr);
		}
		else
		{
			//1 System.out.println("====  INCURRECT PARSE 2 ==== :"+stk_braces.size());
			//String tr = "<html><body font face=TAB_ELANGO_Barathi> êó¤ò£è ð°è¢èð¢ðìõ¤ô¢¬ô.    </body></html>" ;
			String tr = "<html><body font face=TAB_ELANGO_Barathi> Braces mismatched.    </body></html>" ;
			JOptionPane.showMessageDialog(null,tr);
		}
		//1 System.out.println(" ---------");
		//return;
	}


	public String ParseTheSentence(String sentence)
	{
		Parser parser = new Parser();
		ArrayList parser_output = Parse(sentence);
		if(parser_output == null)
				return " êó¤ò£ù õ£è¢è¤òñ¢ Þô¢¬ô.\n Not a proper sentence.";

		return getProperOutput(parser_output);

	}

	public String DoPreprocess(String sentence)
	{
		System.out.println(sentence);
		StringTokenizer stz_sentence = new StringTokenizer(sentence);
		boolean isCorrectSentence = true;
		String newSentence	 = new String();

		int wordCount = stz_sentence.countTokens();

		for(int wordPos = 0; wordPos<wordCount; wordPos++)
		{

			String word = stz_sentence.nextToken();
			System.out.println(" word :"+wordPos+"  "+word);
			boolean isCorrectWord = true;
			String newWord = new String();

			byte[] wordIC = TabConverter.convert(word);

			/** startPlace : This is to store the starting position of the sequence of tamil character. Ex. "ñî°asdè÷¤ù¢" in this string, initially startPlace = 0 ( for ñî°), and then startPlace = 12 (for è÷¤ù¢) in internalCode.
			*/
			int startPlace = 0;

			/** TSBDNEWNT : Tamil sequence Started But Did Not End With Non Tamil sequence. Ex. "ñî°asdè÷¤ù¢" here "è÷¤ù¢" started but didn't end with a non tamil character
			*/
			boolean TSBDNEWNT = false ;

			for(int charPos= 0 ; charPos<wordIC.length ; charPos++)
			{
				if(wordIC[charPos]==0)
				{
					isCorrectSentence = false;
					isCorrectWord = false;

					TSBDNEWNT	= false;

					if(startPlace<charPos)
					{
						// total number of tamil characters which have to be extracted.
						int countTC = 0;
						if(startPlace == 0)
							countTC = charPos;
						else countTC = charPos-startPlace+1;

				//		System.out.println(" From :"+startPlace+"  to :"+(charPos)+"   total :"+countTC);

						// part of Tamil word in Intercal code : i.e "ñî°" in "ñî°asdè÷¤ù¢" in internal code.
						byte[] partTWordIC = new byte[countTC];

				//		System.out.println("111------");
				//		for(int i = 0 ; i<wordIC.length ; i++)
				//			System.out.println(" "+i+"  :"+wordIC[i]);
				//		System.out.println("\n111-----");

						for(int i = startPlace , j =0 ; i < charPos ; i++, j++)
						{
				//			System.out.println(" "+i+"  <-- :"+wordIC[i]);
							partTWordIC[j] = wordIC[i];
						}
						newWord = newWord + TabConverter.revert(partTWordIC);

						System.out.println("          :"+TabConverter.revert(wordIC));
				//		for(int i=0; i<partTWordIC.length;i++)
				//			System.out.println(" "+partTWordIC[i]);
				//		System.out.println(" <--- :"+TabConverter.revert(partTWordIC));
					}


					for(int j=charPos+1; j<wordIC.length; j++)
					{ // this loop is to skip the non Tamil character sequence
						if(wordIC[j]==0)
						{	// to check the end of non Tamil character sequence
							charPos =j;
							startPlace = j+1; // set the new tamil start position as end of non tamil sequence + 1 .
							TSBDNEWNT = true;
							break;
						}
					}
				}
			}

			if(TSBDNEWNT)
			{
				byte[] partTWordIC =new byte[wordIC.length-startPlace];
				//System.out.println(" 	from :"+startPlace+"   to :"+wordIC.length);

				//System.out.println("111------");
				//for(int i = 0 ; i<wordIC.length ; i++)
				//	System.out.print(" "+wordIC[i]);
				//System.out.println("\n111-----");

				for(int i = startPlace, j =0 ; i < wordIC.length ; i++,j++)
				{
						partTWordIC[j] = wordIC[i];
				//		System.out.println("  <-- :"+wordIC[i]);
				}
				//System.out.println("  =====" +partTWordIC.length);

				newWord = newWord + TabConverter.revert(partTWordIC);

				//System.out.println("------ :"+ partTWordIC.length);
				//for(int i=0; i<partTWordIC.length;i++)
				//			System.out.println(" "+partTWordIC[i]);
				//System.out.println("\n-----");

				//System.out.println("          :"+TabConverter.revert(wordIC));
				//System.out.println(" <--- :"+TabConverter.revert(partTWordIC));
			}

			if(!isCorrectWord)
			{
				//System.out.println("	  WORD IS INCORRECT");
				newSentence = newSentence + " " + newWord;
			}
			else
			{	//System.out.println("	  WORD IS CORRECT");
				newSentence = newSentence + " " + word;
			}

		}	// end of for

		//System.out.println(" Final sentence :"+"\n"+newSentence);

		if(isCorrectSentence)
		{
			//System.out.println(" >>>>>>>>>>Sentence is correct ");
			return null;
		}
		else
		{
			//System.out.println(" <<<<<<<<<Sentence is INcorrect ");
			return newSentence ;
		}

	}	// end of DoPreprocess



}