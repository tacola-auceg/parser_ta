

package Parser;

import java.util.*;
import java.awt.Point;

	/**
	This class parses the given sentence using the details given by the Tokenizer class.
	*/

public class TokenParser
{
	ArrayList al_all_words_detail = new ArrayList();

	/** current_index: This is used to store the current word position of the execution
	*   in the sentence.
	*/
    int current_index = 0;
    ArrayList parser_output = new ArrayList();
	ArrayList al_words ;

	boolean noun_state = false;
	boolean verb_state = false;
	boolean clause_exists = false;
	boolean maRRum_exists = false;

	/** list of statuses (ON/OFF) of each word during parsing.	*/
	ArrayList al_WordsStatus = new ArrayList();

	ArrayList al_clause_markers = new ArrayList();
	ArrayList al_NC_markers = new ArrayList();
	ArrayList al_ADJC_markers = new ArrayList();
	ArrayList al_ADVC_markers = new ArrayList();
	ArrayList al_conjunction = new ArrayList();
	ArrayList al_maRRum = new ArrayList();
	// this contains the waiting words like 'maRRum' and 'maalum'
	ArrayList al_otherWaitingWords = new ArrayList();

    StringTokenizer stz_sentence ;
	ArrayList al_start_clause_tokens = new ArrayList();
	ArrayList al_end_clause_tokens = new ArrayList();

	ArrayList al_startGroupTokens = new ArrayList();
	ArrayList al_endGroupTokens = new ArrayList();

	ArrayList al_waitingADVC_portion = new ArrayList();
	ArrayList al_waitingADJC_portion = new ArrayList();
	ArrayList al_NC_portion = new ArrayList();

	Stack stk_waiting_adverb = new Stack();
	Stack stk_waiting_adjective = new Stack();

 	public ArrayList getParsedText(Parser parser)
 	{
		al_words = parser.tokenizer.al_words;
        al_all_words_detail = parser.al_all_words_detail;

		al_clause_markers = parser.tokenizer.al_clause_markers;
		al_NC_markers = parser.tokenizer.al_NC_markers;
		al_ADJC_markers = parser.tokenizer.al_ADJC_markers;
		al_ADVC_markers = parser.tokenizer.al_ADVC_markers;
		al_conjunction = parser.tokenizer.al_conjunction;
		al_maRRum = parser.tokenizer.al_maRRum;
		al_otherWaitingWords = parser.tokenizer.al_otherWaitingWords;

		stz_sentence = new StringTokenizer(parser.tokenizer.sentence);

		parser_output.add("S");
		parser_output.add("(");

		// initialise the status of all words as true for word has started, false for word has parsed and null for word hasn't started.
		for( int i=0; i<al_all_words_detail.size(); i++)
			al_WordsStatus.add(null);

		if( al_conjunction.size()!=0)	// if 1
		{
			return constructCompoundSentence();
		}

		if( al_clause_markers.size()!=0)	// if 2
		{
			clause_exists = true;
			intialise_clause_tokens();
			process_clause_markers();
		}

		if(al_maRRum.size()!=0)	// if 3
		{
			maRRum_exists = true;
			intialise_group_tokens();
			process_maRRum();
			/*
			int i = 0;

				System.out.println(" total no. of advcs :"+al_waitingADVC_portion.size());
			for( ;i<al_startGroupTokens.size(); i++ )
			{
				String[] start = (String[]) al_startGroupTokens.get(i);
				String[] end = (String[]) al_endGroupTokens.get(i);
				if( start != null)
					System.out.println(" ====Start "+i+" :"+start[0]+"  "+start[1]);
				if(end!=null)
					System.out.println(" ====End  "+i+" :"+end[0]);
			}
			*/
		}


	  for(int position = current_index  ; position <al_all_words_detail.size() ; position++)
		{
			if(  (al_waitingADVC_portion.size()!=0) || (al_waitingADJC_portion.size()!=0)  || (al_otherWaitingWords.size()!=0) ) 	// if 4
			{
				int next_word_index = is_this_in_waiting_portion(position);
				if( next_word_index!= -1)	// if 5
				{
				   position = next_word_index;
				   continue;
			    }
			}


			int actual_position = position;
			ArrayList word_detail = (ArrayList) al_all_words_detail.get(position);

			if(word_detail.contains("adj"))	// if 6
			{
				stk_waiting_adjective.push(new Integer(position));
				continue;
			}
			else if(word_detail.contains("adv"))	// if 7
			{
				stk_waiting_adverb.push(new Integer(position));
				continue;
			}

			if(maRRum_exists)	// if 8
			{
				String[] temp_startGroupTokens =(String[]) al_startGroupTokens.get(position);
				if(temp_startGroupTokens !=	null)	// if 9
				{
					//1 System.out.println(" maRRum start adding");
					add_in_parser_output( temp_startGroupTokens);
				}
			}

			if(clause_exists)	// if 10
			{
				String[] temp_start_clause_tokens =(String[]) al_start_clause_tokens.get(position);
				if(temp_start_clause_tokens != null)	// if 11
				{
					//1 System.out.println(" class start adding");
					add_in_parser_output( temp_start_clause_tokens);
				 }
			}

			//1 System.out.println("			word index1	:"+position);

			if(word_detail.contains("noun") || word_detail.contains("v_noun"))	// if 12
			{
				//1 System.out.println(" 				Noun State : "+al_WordsStatus.get(position)+"  "+position);

				parser_output.add("NP");
				parser_output.add("(");

				int noADJCs = al_waitingADJC_portion.size();
				if( noADJCs !=0)	// if 13
				{
					int count = noADJCs;
					int previousADJC_end =-1;
					do	//	do 1
					{
						if( (noADJCs>=2) && (count==1) )	// if 14
							checkAndAddMaRRum(previousADJC_end+1);
						Point temp_point = (Point) al_waitingADJC_portion.get(0);
						int tempX = (int)temp_point.getX();
						int tempY = (int)temp_point.getY();
						if(  tempX < position )	// if 15
						{
							parse_the_portion( tempX, tempY );
							al_waitingADJC_portion.remove(0);
							previousADJC_end = tempY;
						}
						else
							break;
						count--;
					}
					while(al_waitingADJC_portion.size()!=0);
				}

				int no_adjs = stk_waiting_adjective.size();
				if(no_adjs != 0)	// if 16
				{
					int count = no_adjs;
					int previous_adjEnd =-1;
					if(no_adjs >= 2)	// if 17
					{
						parser_output.add("adjs");
						parser_output.add("(");
					}

					do	//	do 2
					{
						if( (no_adjs>=2) && (count==1) )	// if 18
							checkAndAddMaRRum(previous_adjEnd+1);
						parser_output.add("Adjective");
						parser_output.add("(");
						int waiting_word_position =( (Integer)stk_waiting_adjective.get(0) ).intValue();
						ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
						String waiting_word =(String) waiting_word_detail.get(0);
						parser_output.add(waiting_word+" , "+waiting_word_position);
						parser_output.add(")");
						previous_adjEnd = waiting_word_position;
						stk_waiting_adjective.remove(0);
						count--;
					}
					while(!stk_waiting_adjective.empty());

					if(no_adjs >=2)	// if 19
						parser_output.add(")");

				}

				/* Note: if the word is verbal noun advers and ADVCs can be added here */
				if(word_detail.contains("v_noun"))
				{
					int no_advs = stk_waiting_adverb.size();
					if(no_advs != 0)	// if 24
					{
						int count = no_advs;
						int previous_advEnd =-1;
						if(no_advs >= 2)	// if 25
						{
							parser_output.add("advs");
							parser_output.add("(");
						}

						do	// do 4
						{
							if( (no_advs>=2) && (count==1) )	// if 26
								checkAndAddMaRRum(previous_advEnd+1);
							parser_output.add("Adverb1");
							parser_output.add("(");
							int waiting_word_position =( (Integer)stk_waiting_adverb.get(0) ).intValue();
							ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
							String waiting_word =(String) waiting_word_detail.get(0);
							parser_output.add(waiting_word+" , "+waiting_word_position);
							parser_output.add(")");
							previous_advEnd = waiting_word_position;
							stk_waiting_adverb.remove(0);
							count--;
						}
						while(!stk_waiting_adverb.empty());

						if(no_advs >=2)	// if 27
							parser_output.add(")");

					}
				}

				parser_output.add("Noun");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				parser_output.add(")");
				parser_output.add(")");
				al_WordsStatus.set(position, new Boolean(false));
				noun_state = false;

			}
			else if(word_detail.contains("verb"))	// if 20
			{
				parser_output.add("VP");
				parser_output.add("(");

				int noADVCs = al_waitingADVC_portion.size();
				if( noADVCs !=0 )	// if 21
				{	//count is to identify the last ADVC to add maRRum
					int count = noADVCs;
					int previousADVC_end =-1;
					do	// do 3
					{
						if( (noADVCs>=2) && (count==1) )	// if 22
							checkAndAddMaRRum(previousADVC_end+1);
						Point temp_point = (Point) al_waitingADVC_portion.get(0);
						int tempX = (int)temp_point.getX();
						int tempY = (int)temp_point.getY();
						if(  tempX < position )	// if 23
						{
							parse_the_portion( tempX, tempY );
							al_waitingADVC_portion.remove(0);
							previousADVC_end = tempY;
						}
						else
							break;
						count--;
					}
					while(al_waitingADVC_portion.size()!=0);
				}

				int no_advs = stk_waiting_adverb.size();
				if(no_advs != 0)	// if 24
				{
					int count = no_advs;
					int previous_advEnd =-1;
					if(no_advs >= 2)	// if 25
					{
						parser_output.add("advs");
						parser_output.add("(");
					}

					do	// do 4
					{
						if( (no_advs>=2) && (count==1) )	// if 26
							checkAndAddMaRRum(previous_advEnd+1);
						parser_output.add("Adverb1");
						parser_output.add("(");
						int waiting_word_position =( (Integer)stk_waiting_adverb.get(0) ).intValue();
						ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
						String waiting_word =(String) waiting_word_detail.get(0);
						parser_output.add(waiting_word+" , "+waiting_word_position);
						parser_output.add(")");
						previous_advEnd = waiting_word_position;
						stk_waiting_adverb.remove(0);
						count--;
					}
					while(!stk_waiting_adverb.empty());

					if(no_advs >=2)	// if 27
						parser_output.add(")");

				}
				parser_output.add("Verb");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				parser_output.add(")");
				parser_output.add(")");
				al_WordsStatus.set(position, new Boolean(false));
				verb_state = false;

			}
			else if(word_detail.contains("con"))	// if 28
			{
				//1 System.out.println("     CONNN");
				parser_output.add(")");
				parser_output.add("Con.");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				//parser_output.add(")");
				//parser_output.add(")");
				//1 String[] temp_end_clause_tokens =(String[]) al_end_clause_tokens.get(position);
				//1 if(temp_end_clause_tokens != null)
				//1					 add_in_parser_output( temp_end_clause_tokens);
			}
			else if(word_detail.contains("cnjn"))
			{
				parser_output.add("Cnjn3");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				parser_output.add(")");
			}



			if(clause_exists)	// if 29
			{
				String[] temp_end_clause_tokens =(String[]) al_end_clause_tokens.get(position);
				if(temp_end_clause_tokens != null)	// if 30
				{
					//1 System.out.println(" class end adding");
					 add_in_parser_output( temp_end_clause_tokens);
				 }
			}
			if(maRRum_exists)	// if 31
			{
				String[] temp_endGroupTokens =(String[]) al_endGroupTokens.get(position);
				if(temp_endGroupTokens != null)	// if 32
				{
					 //1 System.out.println(" maRRum end adding");
					 add_in_parser_output( temp_endGroupTokens);
				 }
			}

		}

		parser_output.add(")");

		return parser_output;
	}

	public void add_noun_in_parser_output(int position, String[][] word_details)
	{
		//1 System.out.println("					*1.1");
		if(position==0)
		{
			//1 System.out.println("					*1.2  ");
			//int actual_position = al_words.indexOf(word_details[position][0]);
			//1 System.out.println(" -> : "+parser_output);
			parser_output.add("NP");
			parser_output.add("(");
			parser_output.add("Noun");
			parser_output.add("(");
			parser_output.add(word_details[position][0]+" , "+0);
			parser_output.add(")");
			parser_output.add(")");
			parser_output.add("NC");
			parser_output.add("(");
			parser_output.add("SS");
			parser_output.add("(");
		}
		current_index++;
	}



	public void process_clause_markers()
	{
		process_NC_and_ADJC_clause_markers();
		process_ADVC_clause_markers();
	}

	public void process_NC_and_ADJC_clause_markers()
	{
		int no_clause_markers = 0;
		int total_no_clause_markers = al_clause_markers.size();

		for( ; no_clause_markers < total_no_clause_markers ; no_clause_markers++) // for 1
		{
			Integer int_word_position = (Integer) al_clause_markers.get(no_clause_markers);
			/** word_position ; position of the clasue marker in the sentence */
			int word_position = int_word_position.intValue();

			/** al_next_first_word contains the details of first next word from the position word_position
			  * and al_next_second_word contains the details of the second next word from the position word_position
				and al_previous_word contains the details of the first previous word from the position word_position
			  */
			ArrayList al_next_first_word =  new ArrayList();
			ArrayList al_next_second_word =  new ArrayList();
			ArrayList al_previous_word = new ArrayList();

			if(word_position < al_all_words_detail.size()-2)	// if 1
			{
				al_next_second_word = (ArrayList) al_all_words_detail.get(word_position+1);
				al_next_first_word = (ArrayList) al_all_words_detail.get(word_position+1);
			}
			else if(word_position < al_all_words_detail.size()-1)	// if 2
				al_next_first_word = (ArrayList) al_all_words_detail.get(word_position+1);
			else
				System.out.println(" ****The sentence may be in currect ****"); //25

			if(word_position > 0)	// if 3
				al_previous_word = (ArrayList) al_all_words_detail.get(word_position-1);


			if(al_NC_markers.contains(int_word_position))	// if 4
			{
				//1 System.out.println("			3 ");
				if(is_verb_exists_after(word_position))	//	if 5
				{
					//1 System.out.println("  			4  verb exists");
					if(al_next_first_word.contains("noun") ||
						al_next_first_word.contains("adj") ||
						( al_next_first_word.contains("adv") && al_next_second_word.contains("noun") )  )	// if 6
					{   // noun comes after the con.
						//1 System.out.println("			4.1 noun comes after the con");
						if(al_previous_word.contains("noun"))	//	if 7
						{	String[] temp_start_tokens = { "1NC","("};
							String[] temp_end_tokens = { ")"};
							if(al_NC_markers.size()==1)	//	if 8
								al_start_clause_tokens.set(0, temp_start_tokens);
							else
							{ 	//1 System.out.println(" this has to be done properly");
								al_start_clause_tokens.set(0, temp_start_tokens);
							}

							al_end_clause_tokens.set(word_position,temp_end_tokens);
						}
						else if(al_previous_word.contains("verb"))	// 9
						{	String[] temp_start_tokens = { "2NC","(","SS","(", };
							String[] temp_end_tokens = { ")",")"};
							al_start_clause_tokens.set(0, temp_start_tokens);
							al_end_clause_tokens.set(word_position,temp_end_tokens);
						}
					}
					else
					{   // noun comes before the SS
						 //System.out.println("			4.2  noun comes before SS");
						int no_words = word_position-2;
						for( ; no_words >= 0 ; no_words-- )	// for 2
						{
							ArrayList al_temp_position = (ArrayList) al_all_words_detail.get(no_words);
							//System.out.println("			4.3    :"+no_words+"   :"+al_temp_position) ;
							if( (al_temp_position.contains("noun")) && (al_temp_position.contains("Dat"))  )	// if 10
							{
								 //System.out.println("			4.3.1") ;
								int point_to_proceed = any_verb_exists_between(no_words+1,word_position-1);
								 //System.out.println("  point to proceed :"+point_to_proceed);
								if(point_to_proceed != -1)	// if 11
								{
									ArrayList al_word = (ArrayList) al_all_words_detail.get(point_to_proceed);
									if(al_word.contains("MEPB"))	// if 12
									{  //  ex : âùè¢° ÜõÂè¢° ðöñ¢  ªîó¤»ñ¢ âù¢Á ªîó¤»ñ¢
										//1 System.out.println("			4.3.2") ;
										String[] temp_start_tokens = { "3NC","(","SS","(", };
										String[] temp_end_tokens = { ")",")"};
										al_start_clause_tokens.set(no_words, temp_start_tokens);
										al_end_clause_tokens.set(word_position,temp_end_tokens);
										break;
									}
									else if(!al_word.contains("MEPB"))	// if 13
									{
										//1 System.out.println(" ************************");
										//System.out.println("			4.3.22") ;
										String[] temp_start_tokens = { "7NC","(","SS","(", };
										String[] temp_end_tokens = { ")",")"};
										al_start_clause_tokens.set(no_words, temp_start_tokens);
										al_end_clause_tokens.set(word_position,temp_end_tokens);
										break;
										//continue;
									}
								}

								if(al_previous_word.contains("noun"))	// if 14
								{	String[] temp_start_tokens = { "4NC","("};
									String[] temp_end_tokens = { ")"};
									al_start_clause_tokens.set(no_words+1, temp_start_tokens);
									al_end_clause_tokens.set(word_position,temp_end_tokens);
								}
								else if(al_previous_word.contains("verb"))	// if 15
								{	String[] temp_start_tokens = { "5NC","(","SS","(", };
									String[] temp_end_tokens = { ")",")"};
									al_start_clause_tokens.set(no_words+1, temp_start_tokens);
									al_end_clause_tokens.set(word_position,temp_end_tokens);
								}
								break;
							}
							else if( (al_temp_position.contains("noun")) && (al_temp_position.contains("Nom"))  )
							{
								//System.out.println(" NOMinative noun occures. 5");
								int point_to_proceed = any_verb_exists_between(no_words+1,word_position-1);

								//System.out.println("  point to proceed :"+point_to_proceed);
								if(point_to_proceed != -1)
								{
									ArrayList al_word = (ArrayList) al_all_words_detail.get(point_to_proceed);
									if(!al_word.contains("MEPB"))
									{
										//System.out.println(" ************************");
										//System.out.println("			5.1") ;
										String[] temp_start_tokens = { "8NC","(","SS","(", };
										String[] temp_end_tokens = { ")",")"};
										al_start_clause_tokens.set(no_words, temp_start_tokens);
										al_end_clause_tokens.set(word_position,temp_end_tokens);
										break;
									}
								}
							}

						}

						/*System.out.println("----------------------------"+no_words);
						if( (no_words == 0 ) && (word_details[no_words][1].equals("noun")))
						{
							System.out.println("----------------------");
							String[] temp_start_tokens = { "11NC (","SS (", };
							String[] temp_end_tokens = { ")",")"};
							al_start_clause_tokens.set(1, temp_start_tokens);
							al_end_clause_tokens.set(word_position,temp_end_tokens);
						}
						*/

					}

				}  // end verb exists
				else
				{
					if(al_NC_markers.size()==1)	// if 16
					{
						//1 System.out.println("             this type");
						if( al_previous_word.contains("verb") )	//	if 17
						{
							//with V in S and without Dat noun:
							// ï£ù¢ ªõø¢ø¤ ªðø¢«øù¢ âù¢ð¶ àí¢¬ñ

							String[] temp_start_tokens = { "6NC","(","SS","(", };
							String[] temp_end_tokens = { ")",")"};

							al_start_clause_tokens.set(0, temp_start_tokens);
							al_end_clause_tokens.set(word_position,temp_end_tokens);

						}
						else
						{
							// without V in S and without Dat noun:
							// ex : ªè£ò¢ò£ âù¢ð¶ å¼õ¬èò£ù ðöñ¢

							String[] temp_start_tokens = { "NC","("};
							String[] temp_end_tokens = { ")"};

							al_start_clause_tokens.set(0, temp_start_tokens);
							al_end_clause_tokens.set(word_position,temp_end_tokens);
						}

					}
				}

			} // end of NC

			else if(al_ADJC_markers.contains(int_word_position))	//	if 18
			{
				int previous_key_position = is_keywords_exists_before(word_position);

				// ADJC_noun_posistion is the position of the noun for which this adjective cluase comes
				//int ADJC_noun_position = get_ADJC_noun_position(word_position);

				int ADJC_start_position = -1;

				if(previous_key_position == -1)	// if 19
					ADJC_start_position = 0;
				else	// Inside this , some process has to done appropriate to the previous clause and in ADVC.
					ADJC_start_position = previous_key_position + 1;

				String[] temp_start_tokens = { "ADJC","(", };
				String[] temp_end_tokens = { ")"};
				al_start_clause_tokens.set(ADJC_start_position, temp_start_tokens);
				al_WordsStatus.set(word_position+1, new Boolean(true) );
				al_end_clause_tokens.set(word_position,temp_end_tokens);

				al_waitingADJC_portion.add(new Point(ADJC_start_position,word_position));
			}  // end of ADJC
		}
	}


	public void process_ADVC_clause_markers()
	{
		//1 System.out.println("			1");
		int no_clause_markers = 0;
		int total_no_clause_markers = al_clause_markers.size();

		for( ; no_clause_markers < total_no_clause_markers ; no_clause_markers++) // for 1
		{
			Integer int_word_position = (Integer) al_clause_markers.get(no_clause_markers);
			/** word_position ; position of the clasue marker in the sentence */
			int word_position = int_word_position.intValue();

			/* al_next_first_word contains the details of first next word from the position word_position.
			ArrayList al_next_first_word =  new ArrayList();
			// al_next_second_word contains the details of the second next word from the position word_position.
			//ArrayList al_next_second_word =  new ArrayList();

			/**	al_previous_word contains the details of the first previous word from the position word_position.	*/
			ArrayList al_previous_word = new ArrayList();


			if(al_ADVC_markers.contains(int_word_position)) // if 1
			{
				/* if(word_position < al_all_words_detail.size()-2)	// if
				{
					al_next_second_word = (ArrayList) al_all_words_detail.get(word_position+1);
					al_next_first_word = (ArrayList) al_all_words_detail.get(word_position+1);
				}
				else if(word_position < al_all_words_detail.size()-1)	// if
					al_next_first_word = (ArrayList) al_all_words_detail.get(word_position+1);
				*/

				if(word_position > 0)	// if 2
					al_previous_word = (ArrayList) al_all_words_detail.get(word_position-1);

				int previous_key_position = is_keywords_exists_before(word_position);
				int ADVC_start_position = -1;

				if(previous_key_position == -1)	// if 3
					ADVC_start_position = get_ADVC_start_between(0,word_position-1,-1);
				else
				{
					ArrayList al_keyword_detail = (ArrayList)al_all_words_detail.get(previous_key_position);
					if( al_keyword_detail.contains("RP") )	// if 4
					{
						ADVC_start_position = get_ADVC_start_between(previous_key_position+2,word_position-1,2);// previous_key_position+2;
					}
					if( al_keyword_detail.contains("ADVC_key") )	// if 5
					{
						ADVC_start_position = get_ADVC_start_between(previous_key_position+1,word_position-1,3);
					}
					else
					{
						ADVC_start_position = previous_key_position+1 ;//get_ADVC_start_between(previous_key_position+1, word_position-1);
					}
				}

				String[] temp_start_tokens = { "ADVC","(", };;

				String[] temp_end_tokens = { ")"};
				al_start_clause_tokens.set(ADVC_start_position, temp_start_tokens);
				al_end_clause_tokens.set(word_position,temp_end_tokens);

				/* if(al_next_first_word.contains("verb") || al_next_first_word.contains("adv"))
					al_end_clause_tokens.set(word_position+1, temp_end_tokens);   //Here word_position+1 is ok if the next word is verb. But it is not currect if the next word is not Verb for ex adverb,..
				else
				{ */
					//if(previous_key_position != -1)
					//	al_end_clause_tokens.set(word_position+1, temp_end_tokens);

					//1 System.out.println(" adding portion :"+ADVC_start_position+" - "+word_position);

					al_waitingADVC_portion.add(new Point(ADVC_start_position,word_position));
				 // }
			}  // end of ADVC
		}  // end of for loop
	} // end of method


	public void process_maRRum()
	{
        int no_maRRum = 0;
		int total_no_maRRum = al_maRRum.size();

		for( ; no_maRRum < total_no_maRRum ; no_maRRum++)
		{
			Integer int_word_position = (Integer) al_maRRum.get(no_maRRum);
			/** word_position ; position of the clasue marker in the sentence */
			int word_position = int_word_position.intValue();
			group_maRRum(word_position);
		} // end of for

	} // end of maRRum


	public void group_maRRum(int wordPosition)
	{
		String groupName = new String();
		int startPosition = -1;
		int endPosition = -1;
		if( wordPosition>0)
		{
			ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(wordPosition-1);
			String pos = (String) al_word_detail.get(1);
			if(pos.equals("noun"))
			{
				groupName = "NPs";
				startPosition = get_maRRumStartPosition(wordPosition-2, "noun");
				endPosition = wordPosition+1;
				//1 System.out.println(" NPs Marrum start :"+startPosition);
			}
			else if(pos.equals("adj"))
			{
				groupName = "adjs";
				startPosition = get_maRRumStartPosition(wordPosition-2, "adj");
				endPosition = wordPosition+1;
				//1 System.out.println(" adjs Marrum start :"+startPosition);
			}
			else if(pos.equals("RP"))
			{
				groupName = "ADJCs";
				startPosition = get_maRRumStartPosition(wordPosition, "RP");
				endPosition = get_maRRumEndPosition(wordPosition,"RP");
				//1 System.out.println(" RPs Marrum start :"+startPosition);
			}
			else if(pos.equals("adv"))
			{
				groupName = "advs";
				startPosition = get_maRRumStartPosition(wordPosition-2, "adv");
				endPosition = wordPosition+1;
				//1 System.out.println(" advs Marrum start :"+startPosition);
			}
			else if(pos.equals("ADVC_key"))
			{
				groupName = "ADVCs";
				startPosition = get_maRRumStartPosition(wordPosition, "ADVC_key");
				endPosition = get_maRRumEndPosition(wordPosition, "ADVC_key");
				//1 System.out.println(" ADVCs Marrum start :"+startPosition+"   End :"+endPosition);
			}

			String[] temp_start_tokens = { groupName,"(", };
			String[] temp_end_tokens = { ")"};
			al_startGroupTokens.set(startPosition, temp_start_tokens);
			al_WordsStatus.set(wordPosition, new Boolean(true) );
			//System.out.println(" <-------- adding end maRRum at:"+endPosition);
			al_endGroupTokens.set(endPosition,temp_end_tokens);
		}
	}

	public int get_maRRumEndPosition(int position, String pos)
	{
		int count = 0;
		if( pos.equals("ADVC_key") )
		{
			do
			{
				Point temp_point = (Point) al_waitingADVC_portion.get(count);
				count++;
				if(  ( (int)temp_point.getY() < position) )
					continue;
				else
					return (int)temp_point.getY();
			}
			while( count < al_waitingADVC_portion.size() );
		}
		else if( pos.equals("RP") )
		{
			do
			{
				Point temp_point = (Point) al_waitingADJC_portion.get(count);
				count++;
				if(  ( (int)temp_point.getY() < position) )
					continue;
				else
					return (int)temp_point.getY();
			}
			while( count < al_waitingADJC_portion.size() );
		}

		return position+1;
	}

	public int get_maRRumStartPosition(int position, String pos)
	{
		if( ( ! pos.equals("ADVC_key") ) && ( ! pos.equals("RP") ) )
		{
			for( ;position>=0 ; position --)
			{
				ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(position);
				String tempPOS = (String)al_word_detail.get(1);
				if( ! tempPOS.equals(pos) )
					return position+1;
				else
					continue;
			}

			if(position == -1)
				return 0;
		}
		else if( pos.equals("ADVC_key"))
		{
			int noADVCs = al_waitingADVC_portion.size()-1;
			int previousX = -1;
			int previousY = -1;
			int noADVCsBeforeMaRRum = 0;

			do
			{
				Point temp_point = (Point) al_waitingADVC_portion.get(noADVCs);
				int tempX = (int)temp_point.getX();
				int tempY = (int)temp_point.getY();

				if(  tempY > position )
				{
					noADVCs--;
					continue;
				}
				else
				{
					if( ( (previousX==-1) && (previousY==-1) ) ||  ( (previousX-1)== tempY ) )
					{
						previousX = tempX;
						previousY = tempY;
						noADVCsBeforeMaRRum++ ;
						noADVCs--;
						continue;
					}
					else if( (previousX-1) > tempY )
						return previousX;
				}
			}
			while( noADVCs >=0 );

			if(noADVCs == -1)
					return previousX;
		}
		else if( pos.equals("RP"))
		{
			int noADJCs = al_waitingADJC_portion.size()-1;
			int previousX = -1;
			int previousY = -1;
			int noADJCsBeforeMaRRum = 0;

			do
			{
				Point temp_point = (Point) al_waitingADJC_portion.get(noADJCs);
				int tempX = (int)temp_point.getX();
				int tempY = (int)temp_point.getY();

				if(  tempY > position )
				{
					noADJCs--;
					continue;
				}
				else
				{
					if( ( (previousX==-1) && (previousY==-1) ) ||  ( (previousX-1)== tempY ) )
					{
						previousX = tempX;
						previousY = tempY;
						noADJCsBeforeMaRRum++ ;
						noADJCs--;
						continue;
					}
					else if( (previousX-1) > tempY )
						return previousX;
				}
			}
			while( noADJCs >=0 );

			if(noADJCs == -1)
					return previousX;
		}


		return -1;
	}


	public int get_ADJC_noun_position(int position)
	{
		position++;
		for( ;position<al_all_words_detail.size() ; position ++)
		{
			ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(position);
			if( (al_word_detail.contains("noun") ) && (al_word_detail.contains("Nom") ))
				return position;
		}
		return -1;
	}


	public int get_ADVC_start_between(int from,int to, int previousKey)
	{
		//previousKey contains -1: nothing, 2:RP, 3:ADVCkey
		int position = from ;
		if(previousKey == 3)
		{
			for( ;position <= to ; position ++)
			{
				ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(position);
				if( al_word_detail.contains("cnjn") )
					return position+1;
				else if( (al_word_detail.contains("noun") ) && (al_word_detail.contains("Nom") ))
					return position+1;
			}
		}
		else
		{
			for( ;position <= to ; position ++)
			{
				ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(position);
				if( (al_word_detail.contains("noun") ) && (al_word_detail.contains("Nom") ))
					return position+1;
			}
		}
		return from;
	}

	public int get_ADJC_start_between(int from,int to)
	{
		int position = from ;
		if( position == 0)
			return position;
		/*else
		{
			ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(to);
			if( al_word_detail.contains("con") )
			{
				int start = get_NC_start(to);
				return start;
			}
		}
		*/
		// if the previous keyword is ADJC keyword
		// heree find the starting paoint of the ADJC after a ADJC keyword. check wether
		// the previous ADJC is this ADJC are for same NP or different.
		if(al_ADJC_markers.contains(new Integer(from-1)))
		{
			for( ;position <= to ; position ++)
			{
				ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(position);
				if( (al_word_detail.contains("noun") ) && (al_word_detail.contains("Nom") ))
					return position+1;
			}
		}
		return position;
	}


	public int is_keywords_exists_before(int position)
	{
		position--;
		for( ;position>=0 ; position --)
		{
			Integer temp = new Integer(position);
			if(  (al_clause_markers.contains(temp) ) || (al_maRRum.contains(temp) ) )
				return position;
		}
		return -1;
	}

	public boolean is_verb_exists_after(int position)
	{
		position++;
		for( ;position<al_all_words_detail.size() ; position ++)
		{
			ArrayList al_word_detail = (ArrayList)al_all_words_detail.get(position);
			if( (al_word_detail.contains("verb") ) || (al_word_detail.contains("con") ))
			    return true;
		}
		return false;

	}

    // hereee
	public int any_verb_exists_between(int from, int to)
	{
		for( ; from<=to ; from ++ )
		{
			ArrayList al_word_detail = (ArrayList) al_all_words_detail.get(from);
			if( al_word_detail.contains("verb") )
			    return from;
		}
		return -1;
	}


	public void intialise_clause_tokens()
	{
		for(int i = 0 ; i<al_all_words_detail.size() ;i++)
		{
			al_start_clause_tokens.add(null);
			al_end_clause_tokens.add(null);
		}
	}

	public void intialise_group_tokens()
	{
		for(int i = 0 ; i<al_all_words_detail.size() ;i++)
		{
			al_startGroupTokens.add(null);
			al_endGroupTokens.add(null);
		}
	}


	public void intialiseWordsStatus()
	{
		for( int i=0; i<al_all_words_detail.size(); i++)
			al_WordsStatus.add(null);
	}

	public void add_in_parser_output(String[] clause_tokens)
	{
		for(int i = 0 ; i< clause_tokens.length ; i++)
			parser_output.add(clause_tokens[i]);
	}

	/**  This function receives the position of a word in a sentence and checks whether it is starting position of any
	*   waiting portion of that sentence or not.If so it will return the end position of that waiting portion, otherwise it returns -1.
	*/
	public int is_this_in_waiting_portion(int position)
	{
		if( al_waitingADVC_portion.size() != 0 )
		{
			for(int no_waiting_portion = 0 ; no_waiting_portion<al_waitingADVC_portion.size();no_waiting_portion++)
			{
				Point temp_point = (Point)al_waitingADVC_portion.get(no_waiting_portion) ;
				if(  temp_point.getX() == position  )
					return (int)temp_point.getY();
			}
		}

		if( al_waitingADJC_portion.size() !=0 )
		{
			for(int no_waiting_portion = 0 ; no_waiting_portion<al_waitingADJC_portion.size();no_waiting_portion++)
			{
				Point temp_point = (Point)al_waitingADJC_portion.get(no_waiting_portion) ;
				if(  temp_point.getX() == position  )
					return (int)temp_point.getY();
			}
		}
		if( al_otherWaitingWords.size() !=0 )
		{
			if(al_otherWaitingWords.contains(new Integer(position)))
				return position++;
		}

		return -1;
	}

	public void checkAndAddMaRRum(int position)
	{
		if( al_maRRum.contains(new Integer(position)) )
		{
			parser_output.add("Cnjn2");
			parser_output.add("(");
			ArrayList word_detail = (ArrayList) al_all_words_detail.get(position);
			parser_output.add(word_detail.get(0)+" , "+position);
			parser_output.add(")");
		}
	}


	public void parse_the_portion(int from, int to)
	{
		//1 System.out.println(" ----------->part of the sentence parsing: "+from+"-"+to);
		for(int position = from  ; position <= to ; position++)
		{
			int actual_position = position;

			ArrayList word_detail = (ArrayList) al_all_words_detail.get(position);

			if(maRRum_exists)
			{
				String[] temp_startGroupTokens =(String[]) al_startGroupTokens.get(position);
				if(temp_startGroupTokens != null)
				{
					//1  System.out.println(" maRRum srat adding");
					 add_in_parser_output( temp_startGroupTokens);
				 }
			}

			if(clause_exists)
			{
				String[] temp_start_clause_tokens =(String[]) al_start_clause_tokens.get(position);
				if(temp_start_clause_tokens != null)
				{
					 //1 System.out.println(" clause start adding");
					 add_in_parser_output( temp_start_clause_tokens);
				 }
			 }
			//1 System.out.println("			word index2	:"+position);
			if(word_detail.contains("adj"))
			{
				if(noun_state) // NP has started
				{
					parser_output.add("Adjective");
					parser_output.add("(");
					parser_output.add(word_detail.get(0)+" , "+actual_position);
					parser_output.add(")");
				}
				else  		// NP hasn't started yet
				{
					parser_output.add("NP");
					parser_output.add("(");
					parser_output.add("Adjective");
					parser_output.add("(");
					parser_output.add(word_detail.get(0)+" , "+actual_position);
					parser_output.add(")");
					noun_state = true;
				}
			}
			else if(word_detail.contains("noun"))
			{
				if(noun_state)
				{
					parser_output.add("Noun");
					parser_output.add("(");
					parser_output.add(word_detail.get(0)+" , "+actual_position);
					parser_output.add(")");
					parser_output.add(")");
					noun_state = false;
				}
				else
				{
					parser_output.add("NP");
					parser_output.add("(");
					parser_output.add("Noun");
					parser_output.add("(");
					parser_output.add(word_detail.get(0)+" , "+actual_position);
					parser_output.add(")");
					parser_output.add(")");
				}
			}
			else if(word_detail.contains("adv"))
			{
				stk_waiting_adverb.push(new Integer(position));
			}
			else if(word_detail.contains("verb"))
			{
				if(verb_state)
				{
					//1 System.out.println(" 		verb_state : ON");
					if(!stk_waiting_adverb.empty())
					{
						parser_output.add("Adverb4");
						parser_output.add("(");
						int waiting_word_position =( (Integer)stk_waiting_adverb.pop() ).intValue();
						ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
						String waiting_word =(String) waiting_word_detail.get(0);
						parser_output.add(waiting_word+" , "+waiting_word_position);
						parser_output.add(")");
					}
					parser_output.add("Verb");
					parser_output.add("(");
					parser_output.add(word_detail.get(0)+" , "+actual_position);
					parser_output.add(")");
					parser_output.add(")");
					verb_state = false;
				}
				else
				{
					//1 System.out.println(" 		verb_state : ON");
					parser_output.add("VP");
					parser_output.add("(");
					if(!stk_waiting_adverb.empty())
					{
						parser_output.add("Adverb5");
						parser_output.add("(");
						int waiting_word_position =( (Integer)stk_waiting_adverb.pop() ).intValue();
						ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
						String waiting_word =(String) waiting_word_detail.get(0);
						parser_output.add(waiting_word+" , "+waiting_word_position);
						parser_output.add(")");
					}
					parser_output.add("Verb");
					parser_output.add("(");
					parser_output.add(word_detail.get(0)+" , "+actual_position);
					parser_output.add(")");
					parser_output.add(")");
				}
				verb_state = false;


			}
			else if(word_detail.contains("con"))
			{
				//1 System.out.println("     CONNN");
				parser_output.add(")");
				parser_output.add("Con.");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				//parser_output.add(")");
				//parser_output.add(")");
				//1 String[] temp_end_clause_tokens =(String[]) al_end_clause_tokens.get(position);
				//1 if(temp_end_clause_tokens != null)
				//1					 add_in_parser_output( temp_end_clause_tokens);
			}
			else if(word_detail.contains("RP"))
			{
				parser_output.add("VP");
				parser_output.add("(");
				if( al_waitingADVC_portion.size()!=0 )
				{
					do
					{
						Point temp_point = (Point) al_waitingADVC_portion.get(0);
						if(  ( (int)temp_point.getX() < position) )
						{
							parse_the_portion( (int)temp_point.getX(), (int)temp_point.getY());
							al_waitingADVC_portion.remove(0);
						}
						else
							break;
					}
					while(al_waitingADVC_portion.size()!=0);
				}

				if(!stk_waiting_adverb.empty())
				{
					do
					{
						parser_output.add("Adverb1");
						parser_output.add("(");
						int waiting_word_position =( (Integer)stk_waiting_adverb.pop() ).intValue();
						ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
						String waiting_word =(String) waiting_word_detail.get(0);
						parser_output.add(waiting_word+" , "+waiting_word_position);
						parser_output.add(")");
					}
					while(!stk_waiting_adverb.empty());
				}

				if(!stk_waiting_adjective.empty())
				{
					for( int adjective_no = stk_waiting_adjective.size()-1; adjective_no >= 0 ; adjective_no--)
					{
						int waiting_word_position =( (Integer)stk_waiting_adjective.get(adjective_no) ).intValue();
						if(waiting_word_position < actual_position)
						{
							parser_output.add("adjective7.1");
							parser_output.add("(");
							ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
							String waiting_word =(String) waiting_word_detail.get(0);
							parser_output.add(waiting_word+" , "+waiting_word_position);
							parser_output.add(")");

							stk_waiting_adjective.remove(adjective_no);
							adjective_no --;
						}
					}
				}

				parser_output.add("RP11");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				parser_output.add(")");
				parser_output.add(")");
				al_WordsStatus.set(position, new Boolean(false));
			}
			else if(word_detail.contains("ADVC_key"))
			{
				System.out.println(" ADVC in PPP");
				if(!verb_state)
				{
					parser_output.add("VP");
					parser_output.add("(");
				}
				else verb_state = false;
				if(!stk_waiting_adverb.empty())
				{
					for( int adverb_no = stk_waiting_adverb.size()-1; adverb_no >= 0 ; adverb_no--)
					{
						int waiting_word_position =( (Integer)stk_waiting_adverb.get(adverb_no) ).intValue();
						if(waiting_word_position < actual_position)
						{
							parser_output.add("Adverb7.1");
							parser_output.add("(");
							ArrayList waiting_word_detail = (ArrayList) al_all_words_detail.get(waiting_word_position);
							String waiting_word =(String) waiting_word_detail.get(0);
							parser_output.add(waiting_word+" , "+waiting_word_position);
							parser_output.add(")");

							stk_waiting_adverb.remove(adverb_no);
							adverb_no --;
						}
					}

				}

				parser_output.add("key");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				parser_output.add(")");
				parser_output.add(")");

			}

			else if(word_detail.contains("cnjn"))
			{
				parser_output.add("Cnjn3");
				parser_output.add("(");
				parser_output.add(word_detail.get(0)+" , "+actual_position);
				parser_output.add(")");
			}

			if(clause_exists)
			{
				String[] temp_end_clause_tokens =(String[]) al_end_clause_tokens.get(position);
				if(temp_end_clause_tokens != null)
				{
					//1 System.out.println(" class end adding2");
					add_in_parser_output( temp_end_clause_tokens);
				 }
			}

			if(maRRum_exists)
			{
				String[] temp_endGroupTokens =(String[]) al_endGroupTokens.get(position);
				if(temp_endGroupTokens != null)
				{
					//1 System.out.println(" maRRum end adding2 at :"+position);
					add_in_parser_output( temp_endGroupTokens);
				 }
			}

		} // end of for loop

	} // parse_the_portion ends

	ArrayList constructCompoundSentence()
	{
		int count = ( (Integer)al_conjunction.get(0) ).intValue();

		String subSentence = new String();
		for(int i=0;i<count;i++)
			subSentence = subSentence+" "+stz_sentence.nextToken();

		//1 System.out.println(" SubSentence is :\n"+subSentence);
		ArrayList SSoutput = new ArrayList();

		Parser SSparser = new Parser();
		SSoutput = SSparser.Parse(subSentence);

		SSoutput.set(0,"SS");
		SSoutput.add(0,"(");
		SSoutput.add(0,"S");

		int addIndex = SSoutput.size();
		SSoutput.add(addIndex,"CNJN");
		SSoutput.add(addIndex+1,"(");
		SSoutput.add(addIndex+2,stz_sentence.nextToken());
		SSoutput.add(addIndex+3,")");
		SSoutput.add(addIndex+4,")");
		return SSoutput;

	}

} // end of class TokenParser
