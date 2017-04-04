
package Parser;


import analyser.*;

import java.util.*;

/**
*	Class Name 	: Tokenizer
*	Author		: RCILTS-Tamil
*/


/**
*	This class is used to get the information about each word of the sentence.
*/


public class Tokenizer
{

    /** al_words : arraylist which consistes all words */
	ArrayList al_words = new ArrayList();
	/** al_analysed_words : arraylist which consistes all analysed words as string */
	ArrayList al_analysed_words = new ArrayList();

	ArrayList al_all_words_detail = new ArrayList();

	ArrayList al_clause_markers = new ArrayList();
	ArrayList al_NC_markers = new ArrayList();
	ArrayList al_ADJC_markers = new ArrayList();
	ArrayList al_ADVC_markers = new ArrayList();
	ArrayList al_conjunction = new ArrayList();
	ArrayList al_maRRum	= new ArrayList();

	// this contains the waiting words like 'maRRum' and 'meelum'
	ArrayList al_otherWaitingWords = new ArrayList();

	String sentence ;

    long time_bef,time_aft;
	Parser parser;

	/** al_MEPB_verbs : this contains the mental emotional  and
	* physical/biologial verbs.
	*/
	ArrayList al_MEPB_verbs = new ArrayList();

	/** UACcount : unknown and CN count. This is to count the number of unkowns and CNs in a sentence.
	*/
	int UACcount = 0 ;

    /**
    * Method Tokenize receives the sentence as a String and returns ArrayList of Arraylist, where the acutal
	* word in the column no zero, word type in the column no one and the case marker in the column no. two
    */


    public ArrayList Tokenize(String sentence, Parser parser)
	{
			parser = parser;
			ArrayList al_words_code = new ArrayList(); // arraylist which consists anlysed word

			Stack stk_outputStack = new Stack();

			String previousPOS = new String();

			this.sentence = sentence;

			StringTokenizer stz_sentence = new StringTokenizer(sentence);
			String output="\n";
			Byte output_code[];

	        int wordCount = stz_sentence.countTokens();

			initiate_Verb_Category();
			Methods an_me_methods = new Methods();
			TabMethods co_tm_methods = new TabMethods();
			ByteMeth co_bm_methods = new ByteMeth();
			VSearch co_vs_search = new VSearch();

			for(int i = 0;i<wordCount;i++)
			{
				//1 System.out.println(" Word no, :"+i);
				String word = stz_sentence.nextToken();

				ArrayList al_word_detail = new ArrayList();
				al_word_detail.add(word);
				al_words.add(word);

				if(co_vs_search.dicSearch(word,"/src/connective.txt"))	// if 1
				{
	         		al_word_detail.add("con");
	         		previousPOS = "con";
					al_all_words_detail.add(al_word_detail);

	         		al_clause_markers.add(new Integer(i));

	         		al_NC_markers.add(new Integer(i));

	         		al_analysed_words.add(null);
	         		continue;
				}
                if(co_vs_search.dicSearch(word,"/src/conjunction.txt"))	// if 2
                {
					al_word_detail.add("cnjn");  // for Conjunction
					al_all_words_detail.add(al_word_detail);

					if(previousPOS.equals("verb"))	// if 3
						al_conjunction.add(new Integer(i));
					else
						al_maRRum.add(new Integer(i));

					if( (!previousPOS.equals("verb")) && (!previousPOS.equals("noun")))	// if 4
						al_otherWaitingWords.add(new Integer(i));

					previousPOS = "cnjn";
					al_analysed_words.add(null);
					continue;
				}
				if(co_vs_search.dicSearch(word,"/src/conjunctionAdjacent.txt"))	// if 5
				{
					al_word_detail.add("cnjn-adj");  // for Conjunction adjecent
					previousPOS = "cnjn-adj";
					al_all_words_detail.add(al_word_detail);

					al_analysed_words.add(null);
					continue;
				}


				if(word.equals("ñ¤è"))	// if 6
				{
					// check the next word to find it's tage
					al_word_detail.add("CN");
					previousPOS = "CN";
					al_all_words_detail.add(al_word_detail);

					al_analysed_words.add(null);
					UACcount++;	// increament the unknown and CN count by one.
					continue;
				}


	            Date date_bef = new Date();
	            time_bef = date_bef.getTime();

				stk_outputStack = an_me_methods.checkverb(word);

	            Date date_aft = new Date();
				time_aft = date_aft.getTime();
				//System.out.println(i+"  --> :"+time_bef);
				//System.out.println("  <-- "+time_aft);

				// g etting result from the analyser
				parser.analyser_time =parser.analyser_time+(time_aft-time_bef);

				//1 System.out.println(" Time Taken : "+(time_aft-time_bef));

	            String wholeword = new String();	// used to get the various parts of a result for a word from the analyser
				while (!stk_outputStack.empty())
				{
	                byte[] wordpart_code = (byte[])stk_outputStack.pop();
	                String wordpart = co_tm_methods.revert( wordpart_code ) ;
	                al_words_code.add(wordpart);
	                output  += wordpart +"\n";
					wholeword =wholeword +wordpart;
				}
	            al_analysed_words.add(wholeword);

				String word_tage = getWordTage(wholeword);
				if(word_tage.equals("RP"))
				{
					al_clause_markers.add(new Integer(i));
					al_ADJC_markers.add(new Integer(i));
				}
				else if(word_tage.equals("ADVC_key"))
				{
					al_clause_markers.add(new Integer(i));
					al_ADVC_markers.add(new Integer(i));
				}

				al_word_detail.add(word_tage);
				previousPOS = word_tage;
				if(word.endsWith("ªð£¿¶"))
					al_word_detail.add("Pothu");

	            if(word_tage.equals("noun"))
	            {   // case marker of the noun
					String casemarker = getCaseMarker(wholeword);
					al_word_detail.add(casemarker);
				}
				else if(word_tage.equals("verb"))
				{
					// verb catagory ex. MEPB
					String verbcat = getVerbType(wholeword);
					al_word_detail.add(verbcat);
				}
				else if( word_tage.equals("UNKNOWN"))
					UACcount++;	//increament the unknown and CN count by one.

	            al_all_words_detail.add(al_word_detail);
	        }

 			System.out.println(" 		AFTER USING THE ANALYSER.");
			show(al_all_words_detail);

			while(UACcount!=0)
	        	al_all_words_detail = findTageByHeuristic(al_all_words_detail);

			al_all_words_detail = doFinalCheckMaRRum(al_all_words_detail);

	        System.out.println("\n 		AFTER USING THE HEURISTIC RULES.");
	        show(al_all_words_detail);

			return al_all_words_detail;
	}


    /**
    This method receives the corresponding output string of a word from the Analyser
    \n Note : Unknown can occur here
    */


	public String getWordTage(String analysed_word)
	{
		 System.out.println(" MA result : "+analysed_word);
         analysed_word.trim();

		 if( (analysed_word.indexOf("< Adverbial Suffix >")!= -1) || (analysed_word.indexOf("< adverb >")!= -1)  )	//	if 1
         	return "adv";
         else if( (analysed_word.indexOf("< Adjectival Suffix >")!= -1) || (analysed_word.indexOf("< adjective >")!= -1)  )	// if 2
         	return "adj";
         else if( ( analysed_word.indexOf(" Verb >")!= -1 ) && ( analysed_word.indexOf(" noun >")!= -1 ) ) 	// if 3
         	return "v_noun";  // contains both verb AND noun
         else if( ( analysed_word.indexOf(" Adverbial Participle > ")!= -1 ) || ( analysed_word.indexOf(" verbal participle > ")!= -1 ))	// if 4
         	return "ADVC_key";  	// contains "Adverbial Participle" OR  " verbal participle"
         /** ADVC_v_key is adverb clause key word which comes with the verb i.e not separate  */
         else if(  ( (analysed_word.indexOf("< Verb >")!= -1) || (analysed_word.indexOf("< verb >")!= -1) ) && ( analysed_word.indexOf("< Relative Participle Suffix>")== -1)  )	// if 5
         	return "verb";  // contains "verb" AND NOT contains "Relative participle suffix"
         else if( (analysed_word.indexOf("noun >")!= -1) || (analysed_word.indexOf("Noun >")!= -1) )	// if 6
         	return "noun";
         else if(  (analysed_word.indexOf("< Relative Participle ")!= -1) || (analysed_word.indexOf("< Relative Participle Suffix>")!= -1)  )	// if 7
         	return "RP";
		 else if(analysed_word.indexOf("< case >")!= -1)	// if 8
         	return "noun";
         else
         	return "UNKNOWN";
	}


	/** This method is to check if the cnjn is added into al_otherWaitingWords
		while it occur between two unknow. If it is so, then after heuristic
		process this method is called to check these cnjn and to remove this
		from al_otherWaitingWords.
	*/
	public ArrayList doFinalCheckMaRRum(ArrayList al_all_word_detail)
	{
		int count = al_all_word_detail.size();

		for(int row_position  = 0; row_position<count ;row_position++)
		{
			ArrayList al_word_detail = (ArrayList)al_all_word_detail.get(row_position);
			if( al_word_detail.contains("cnjn") &&  (row_position > 0)   )
			{
				ArrayList al_previous = (ArrayList)al_all_word_detail.get(row_position-1);
				if(al_previous.contains("noun"))  // Ex.Þó£ñù¢ ðóîù¢ ñø¢Áñ¢ ôè¢û¢ñùù¢ è£ì¢´è¢°ð¢ «ð£ù£ó¢è÷¢
					if(al_otherWaitingWords.contains(new Integer(row_position)))
							al_otherWaitingWords.remove(new Integer(row_position));
			}
		}

		return al_all_word_detail;
	}



	/**
    This method solves the unknown words using

    */

	public ArrayList findTageByHeuristic(ArrayList al_all_word_detail)
	{
		int count = al_all_word_detail.size();

		// in this loop the word 'miga' and 'athi' are identified as adv or adj by bigram
		for(int row_position  = 0; row_position<count ;row_position++)
		{
			ArrayList al_word_detail = (ArrayList)al_all_word_detail.get(row_position);
			ArrayList al_next =  new ArrayList();
			if(row_position < count-1)	// if 1
				al_next = (ArrayList)al_all_word_detail.get(row_position+1);

			if( (row_position >=0) && (al_word_detail.contains("CN")) )	// if 2
			{
				if( (al_next.contains("adv")) || (al_next.contains("verb")) || (al_next.contains("ADVC_key")))	// if 3
				{
					al_word_detail.set(1,"adv");
					UACcount--;	//decreament the unknown and CN count by one.
				}
				else if( al_next.contains("adj"))	// if 4
				{
					al_word_detail.set(1,"adj");
					UACcount--;	//decreament the unknown and CN count by one.
				}
			}
		}


		for( int row_position  = 0; row_position<count ;row_position++)
		{
			ArrayList al_word_detail = (ArrayList)al_all_word_detail.get(row_position);

			if(al_word_detail.contains("UNKNOWN"))	//	if 5
			{
				ArrayList al_previous = new ArrayList();;
				ArrayList al_next =  new ArrayList();
				String tage = new String();
				if(row_position>0)	// if 6
					 al_previous = (ArrayList)al_all_word_detail.get(row_position-1);
				if(row_position < count-1)	// if 7
					al_next = (ArrayList)al_all_word_detail.get(row_position+1);

				// this if condition is to identify more than one noun with or without conjunction
				if(al_next.contains("UNKNOWN")) // if 8
				{
					// cnjnPosition : to get and store the next conjunction position
					int cnjnPosition = get_ConjunctionPosition(row_position+2);
					if(cnjnPosition!=-1) // if 9
					{ // if cnjn present.
						System.out.println("Pos :"+row_position+"  noun0");
						al_word_detail.set(1,"noun");
						System.out.println("Pos :"+(row_position+1)+"  noun1");
						al_next.set(1,"noun");
						row_position = row_position+2;
						for(;row_position<cnjnPosition ;row_position++)
						{
							ArrayList temp_word = (ArrayList)al_all_word_detail.get(row_position);
							if(temp_word.contains("UNKNOWN") ) // if 10
							{
								System.out.println("Pos :"+row_position+"  noun2");
								temp_word.set(1,"noun");
							}
						}

						ArrayList al_last_one = (ArrayList)al_all_word_detail.get(cnjnPosition+1);
						if( al_last_one.contains("UNKNOWN")	) // if 11
						{
							System.out.println("Pos :"+row_position+"  noun3");
							al_last_one.set(1,"noun");
							row_position = cnjnPosition+2;
						}
						else
							row_position = cnjnPosition+1;
						continue;
					}
				}

				if(row_position==0) // if 12
				{	// if the first word is unknown
					System.out.println("Pos :"+row_position+"  noun4");
					tage = "noun";
				}
				else if(row_position==count-1) // if 13
				{	// if the last word is unknown
					if(al_previous.contains("adj")) // if 14
					{
						System.out.println("Pos :"+row_position+"  noun5");
						tage = "noun";
					}
					else if( al_previous.contains("RP")) // if 15
					{
						System.out.println("Pos :"+row_position+"  noun6");
						tage = "noun";
					}
					else
						tage = "verb";
				}
				else
				{
					if( (al_previous.contains("noun")) && ( al_next.contains("noun") ) ) // if 16
					{
						System.out.println("Pos :"+row_position+"  noun7");
						tage = "noun";
					}
					else if(al_previous.contains("adj")) // if 17
					{
						System.out.println("Pos :"+row_position+"  noun8");
						tage = "noun";
					}
					else if(al_previous.contains("adv")) // if 18
					{
						// note : the below three lines where added newly due to the occurence of noun between adv and adv_key  "ïù¢ø£è Þó£ñ¬ù Ü®î¢¶"
						if(al_next.contains("ADVC_key") || al_next.contains("verb")) // if 19
						{
							System.out.println("Pos :"+row_position+"  noun9");
							tage = "noun";
						}
						else
							tage = "adv";
					}
					else
					{
						System.out.println("Pos :"+row_position+"  noun10");
						tage = "noun";
					}
				}

				al_word_detail.set(1,tage);
				if(tage.equals("noun")) // if 20
				{
					// add the case marker for the noun
					String word = (String)al_analysed_words.get(row_position);
					al_word_detail.add(getCaseMarker(word));
				}
				else if(tage.equals("verb")) // if 21
				{
					// add the verb catagory for the verb
					String word = (String) al_analysed_words.get(row_position);
					al_word_detail.add(getVerbType(word));
				}

				UACcount--; //decreament the unknown and CN count by one.
				if(UACcount == 0)
					return al_all_word_detail;
			}	// if ends

		}	// for ends
		return al_all_word_detail;
	}


	public String getCaseMarker(String analysed_word)
	{
		 analysed_word.trim();
		 int case_start = analysed_word.indexOf("case");

		 if(case_start ==-1)
			return "Nom";
		 else if(case_start != 0 )
		 {
			 String temp = analysed_word.substring(0,case_start-1);
			 String case_marker = temp.substring(temp.lastIndexOf(" ")+1);
			 return case_marker.substring(0,3);
		 }

		return "";
	}


	public String getVerbType(String analysed_word)
	{
		try
		{
			 analysed_word.trim();
			 String root_verb = analysed_word.substring(0,analysed_word.indexOf(" "));

			 if(al_MEPB_verbs.contains(root_verb))
					return "MEPB";
		}
		catch(Exception e)
		{
			//1 System.out.println(" prolem with the verb.");
			System.out.println(" ex at heree  at word no. ");
		}

		return "";
	}

	public void initiate_Verb_Category()
	{
		// verbs : contains the mental , emotional, physical and biological verbs
		String[] verbs = { "¹ó¤","ªîó¤","ð¤®","õô¤","Üø¤","Ã²"};
		for(int no_verbs = 0 ; no_verbs < verbs.length ; no_verbs++)
			al_MEPB_verbs.add(verbs[no_verbs]);
	}

	void show(ArrayList al_all_word_detail)
	{
		for( int row_position  = 0; row_position<al_all_word_detail.size();row_position++)
		{
			ArrayList al_word_detail = (ArrayList)al_all_word_detail.get(row_position);
			System.out.print("\n  "+row_position);
			for(int column_position = 1; column_position<al_word_detail.size();column_position++)
				System.out.print("   "+ (String)al_word_detail.get(column_position));
		}
		System.out.println("\n");
	}


	public int get_ConjunctionPosition(int position)
	{
		position++;
		for( ;position<al_all_words_detail.size() ; position ++)
		{
			ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(position);
			if(al_word_detail.contains("UNKNOWN"))
				continue;
			else if(al_word_detail.contains("cnjn"))
				return position ;
			else if( (!al_word_detail.contains("cnjn")) && (!al_word_detail.contains("UNKNOWN")) )
				return -1;
		}
		return -1;
	}




} // end of class