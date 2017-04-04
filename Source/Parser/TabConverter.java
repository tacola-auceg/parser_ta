package Parser;

import java.io.*;
import java.util.*;
import java.util.StringTokenizer;

/**
 * This class is used to convert the TAB text in to the internal code and vise versa.
 *  @ version 2002.11.17
 * 	@ author RCILTS-Tamil,MIT.
 */
public class TabConverter
{
	static int SPECIAL_SPACE = 160;

	/** This is to specify that the occurance of an invalid character after a Tamil character. ex. ªx : where x is invalid next character ex. Üª«
	*/
	static byte INVALID_NEXT 	= 	-2;

	/**
	* This method does the reverse process of the method convert.
	* @ wordIICF (Word In Internal Code Format) sequence of numbers for which the equivalent string is required.
	*/
	public static  String revert( byte wordIICF[] )
	{
		short charInTAB = 0;
		/** equivalant code in TAB for the input char which is in internal code */

		short suffix =0;
		/** contains suffix for a character if needed */

		short prefix =0;
		/** contains prefix for a character if needed */

		boolean tamil = false;
		/** stats the current character mode is in Tamil (True) or non-Tamil (False) */
		boolean zero = false;
		/** stats the current character mode is in Tamil (True) or non-Tamil (False) */

		boolean putSuffix = false;
		/** true if there is a suffix to be added with the current consonent.
		*	Otherwise false
		*/

		boolean putprefix = false;
		/** true if there is a suffix to be added with the current consonent.
		*	Otherwise false
		*/

		int codePosition =0;
		// position of one internal code in the sequence of internal codes

		ArrayList al= new ArrayList();

		if( (wordIICF == null) || (wordIICF.length==0) )	//	if 1
			return null;
		if( wordIICF[0] == 0)	// if 2
		{
			zero = true;
			codePosition++;
		}
		else
			zero = false;

		for(  ; codePosition<wordIICF.length; codePosition++)
		{
			if(zero)	//	if 3
			{   // Nontamil
				byte temp = wordIICF[codePosition];
				short add = 0;
				if(temp<0)
				{
					System.out.println(" ----> negative :"+temp);
					charInTAB = (short)(temp + 256);
					//charInTAB = (short)34 + add;
				}
				else
					charInTAB = (short)wordIICF[codePosition];
			}
			else
			{  // Tamil

				// the below two line for special sorting asked by palani
				//if(wordIICF[codePosition]==38)
				//	charInTAB = (short) 32;

				if(  ( wordIICF[codePosition] <= 11) && ( wordIICF[codePosition] >=1 )  )	// if 4
				{
						 charInTAB =(short)( wordIICF[codePosition]+219 ) ;
				}
				else if( wordIICF[codePosition] == 12)	// if 5
				{
					charInTAB = (short)( wordIICF[codePosition]+217 ) ;
					suffix = (short) 247;
					putSuffix=true;
				}
				else if( wordIICF[codePosition] == 13)	// if 6
				{	     // ("akku ");
					charInTAB =(short)( wordIICF[codePosition]+218 ) ;
				}
				else if( 	(wordIICF[codePosition] >= 14)  &&  (wordIICF[codePosition] <= 37) && (wordIICF[codePosition]!= 33) )	// if 7
				{
					if( codePosition+1<wordIICF.length )	// if 8
					{
						switch( wordIICF[codePosition+1] )	// sw 9
						{
							/*
							case 0:
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								suffix = (short) 162;
								putSuffix=true;
								codePosition++;
								break; */

							case 1:
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								codePosition++;
								break;
							case 2:
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								suffix =(short) 163;
								putSuffix = true;
								codePosition++;
								break;

							case 3:
								if( wordIICF[codePosition] == 18 )	// if 10
								{
									charInTAB = (short) 174;
								}
								else
								{
									charInTAB = getProperTABCode(wordIICF[codePosition]);
									suffix =(short) 164;
									putSuffix = true;
								}
								codePosition++;
								break;

							case 4:
								if( wordIICF[codePosition] == 18 )	// if 11
								{
									charInTAB = (short) 175;
								}
								else
								{
									charInTAB = getProperTABCode(wordIICF[codePosition]);
									suffix =(short) 166;
									putSuffix = true;
								}
								codePosition++;
								break;

							case 5:
								if( (wordIICF[codePosition] >= 14)  &&  (wordIICF[codePosition] <= 20)  )	// if 12
									charInTAB = (short)( wordIICF[codePosition]+162 ) ;
								else if( (wordIICF[codePosition] >= 21)  &&  (wordIICF[codePosition] <= 31)  )	// if 13
									charInTAB = (short)( wordIICF[codePosition]+163 ) ;
								else
								{
									charInTAB = getProperTABCode(wordIICF[codePosition]);
									suffix =(short) 167;
									putSuffix = true;
								}
								codePosition++;
								break;

							case 6:
								if( (wordIICF[codePosition] >= 14)  &&  (wordIICF[codePosition] <= 20)  )	// if 14
									charInTAB = (short)( wordIICF[codePosition]+181 ) ;
								else if( (wordIICF[codePosition] >= 21)  &&  (wordIICF[codePosition] <= 25)  )	// if 15
									charInTAB = (short)( wordIICF[codePosition]+182 ) ;
								else if( (wordIICF[codePosition] >= 26)  &&  (wordIICF[codePosition] <= 31)  )	// if 16
									charInTAB = (short)( wordIICF[codePosition]+188 ) ;
								else
								{
									charInTAB = getProperTABCode(wordIICF[codePosition]);
									suffix = (short) 168;
									putSuffix = true;
								}
								codePosition++;
								break;

							case 7 :
								prefix = (short) 170;
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								putprefix = true;
								codePosition++;
								break;

							case 8 :
								prefix = (short) 171;
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								putprefix = true;
								codePosition++;
								break;

							case 9 :
								prefix = (short) 172;
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								putprefix = true;
								codePosition++;
								break;

							case 10 :
								prefix = (short) 170;
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								suffix = (short) 163;
								putprefix = true;
								putSuffix= true;
								codePosition++;
								break;

							case 11 :
								prefix = (short) 171; // 34 hat to be 171.This is due to softview 171-34 problem
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								suffix = (short) 163;
								putprefix = true;
								putSuffix= true;
								codePosition++;
								break;

							case 12 :
								prefix = (short) 170;
								charInTAB = getProperTABCode(wordIICF[codePosition]);
								suffix = (short) 247;
								putprefix = true;
								putSuffix= true;
								codePosition++;
								break;

							default :
					 			charInTAB = getProperTABCode(wordIICF[codePosition]);
								suffix = (short) 162;
								putSuffix = true;
								break;
						}// end of switch

					}  // end inner if
					else
					{
						charInTAB = getProperTABCode(wordIICF[codePosition]);
						suffix = (short) 162;
						putSuffix=true;
					}
				} //end else if
				else if(wordIICF[codePosition] == 33)	// if 17
					charInTAB = (short) 255;
				else if( wordIICF[codePosition] == INVALID_NEXT)
				{

					switch( wordIICF[codePosition+1] )	// sw
					{
						case 1:
							charInTAB = 162;
							codePosition++;
							break;
						case 2:
							charInTAB = 163;
							codePosition++;
							break;

						case 3:
							charInTAB = 164;
							codePosition++;
							break;

						case 4:
							charInTAB = (short) 166;
							codePosition++;
							break;

						case 5:
							charInTAB = (short)167 ;
							codePosition++;
							break;

						case 6:
							charInTAB = (short)168;
							codePosition++;
							break;

						case 7 :
							charInTAB = 170;
							codePosition++;
							break;

						case 8 :
							charInTAB = (short) 171;
							codePosition++;
							break;

						case 9 :
							charInTAB = (short) 172;
							codePosition++;
							break;
					}
				}

			}// end of tamil

			if(putprefix)	// if 18
			{
				al.add(new Character( (char) prefix) );
				putprefix = false;
			}

			al.add(new Character((char)charInTAB) );

			if(putSuffix)	// if 19
			{
				al.add(new Character( (char) suffix ) );
				putSuffix = false;
			}

			//al.add(  new Character( (char) ((short)5) )  ) ;


			if( (codePosition+1<wordIICF.length) && (wordIICF[codePosition+1] == 0) )	// if 20
			{
				//  System.out.println(" zero ");
				codePosition++;
				if(zero)	// if 21
				{
					zero = false;
					continue;
				}
				else
				{
					zero = true;
					continue;
				}
			}
		}// end of for

		String ret = new String();

		for(int j=0; j<al.size();j++)
		{
			ret = ret+( (Character)(al.get(j)) ).charValue() ;
		}
		return ret;
	}	 //end of revert



	/**
	* Converts the given text to the internal code.
	* Gets a string and checks each character. According to this it
	* will check the next first and second characters if necessary. With
	* this check condition appropriate one or two integers will be added
	* in the output byte[].
	* If a character is non-Tamil character then a zero (‘0’) will be
	* added stating that non Tamil is started and it puts the non-Tamil
	* character’s equivalent ASCII value.
	* If the non-Tamil characters end by a Tamil character it will put a
	* zero (‘0’) stating that non-Tamil ends and then puts the correct
	* numbers in the output bye[].
	* Ex.     Some words and their converted out puts are given bellow.
	* 		Üñ¢ñ£		-	1 23 23 2
	* 		Üabcñ¢ñ£	-	1 0 97 98 99 0 23 23 2
	* 		Apple		-	0 65 112 112 108 101
	*
	* @param inputWord string to be converted from TAB to internal code format.
	*/
	public static byte[] convert(String inputWord)
	{

		byte charIICF = 0;
		/** contians the equivalant value of the input character In Internal
		*	Code Format.
		*/

		byte suffix=0;
		/** contains suffix for a character if needed */

		boolean putSuffix= false;
		/** true if there is a suffix to be added with the current consonent.
		*	Otherwise false
		*/

		boolean tamil = false;
		boolean zero = false;

		ArrayList al_wordIICF = new ArrayList();
		/** al_wordIICF : data structure which contains the Word In Internal
		* Code Format.
		*/


		if(inputWord==null)		//if 1
		{
			byte[] nothing = null;
			return nothing;
		}

		for(int charNo=0 ; charNo<inputWord.length() ; charNo++ )
		{	// charNo : character position number

			// the bellow condition is consider the space character as tamil character
			/*if( (currentChar == 32))
			{
				tamil = true;
			} */

			// current character of the input word
			char currentChar = inputWord.charAt(charNo);

			//System.out.print("\n   input :"+(int)currentChar);
			//check the current input character is Tamil or non-Tamil
			if( (  ( currentChar <=127 )  && ( currentChar >=0 ) ) || (currentChar == SPECIAL_SPACE)	)//if 2
				tamil = false;
			else if( currentChar >= 128)
				tamil = true;
			//if( currentChar == 34)  // This condition is only for Softview due to that 171-34 interchange problem.
			//	tamil = true;

			if( (!tamil) && (!zero) )	//if 3
			{
				al_wordIICF.add( new Byte( (byte) 0 ) );
				zero = true;
			}
			else if( (tamil) && (zero) )	// if 4
			{
					al_wordIICF.add( new Byte( (byte) 0 ) );
				zero = false;
			}


			if(!tamil) 	//if 5
			{
				charIICF = ( byte ) currentChar;
				Byte t = new Byte(charIICF);
				al_wordIICF.add( new Byte( charIICF) );
				continue ;
			}


			else if(tamil)	// if 6
			{	// 123
				//if( (currentChar == 32))
				//charIICF = 38;

				if(  ( currentChar>= 220 )  &&  (  currentChar<=  231 ) )	//	if 7
				{
					if( currentChar == 229 )	//if 8
					{
						if(charNo+1<inputWord.length())	// if 9
						{
							if(  inputWord.charAt(charNo+1)==247  )	// if 10
							{
								if(  charNo+2<inputWord.length() )	// if 11
								{
									if( !checkLa(inputWord.charAt(charNo+2)) ) // if 12
										charIICF = 10;
									else
									{
										charIICF = 12;
										charNo++;
									}
								}
								else
								{
									charIICF = 12;
									charNo++;
								}
							}
							else
								charIICF =   10;
						}
						else
							charIICF =   10;
					}
					else if( currentChar == 231 )	// if 13
					{
						charIICF =  13;
					}
					else
					{
						charIICF =  (byte)(currentChar - 219);
					}
				}

				else if(  (  currentChar>=  176 )  &&  (  currentChar<=  182 ) )	// if 14
				{
					charIICF =  (byte)(currentChar - 162);
					suffix = 5;
					putSuffix = true;
				}

				else if(  (  currentChar>=  184 )  &&  (  currentChar<=  194 ) )	// if 15
				{
					charIICF =  (byte)(currentChar - 163);
					suffix =  5;
					putSuffix = true;
				}

				else if(  (  currentChar>=  195 )  &&  (  currentChar<=  201 ) )	// if 16
				{
					charIICF =  (byte)(currentChar - 181);
					suffix =   6;
					putSuffix = true;
				}

				else if(  (  currentChar>=  203 )  &&  (  currentChar<=  207 ) )	// if 17
				{
					charIICF =  (byte)(currentChar - 182);
					suffix =   6;
					putSuffix = true;
				}

				else if(  (  currentChar>=  214 )  &&  (  currentChar<=  219 ) )	// if 18
				{
					charIICF =  (byte)(currentChar - 188);
					suffix =   6;
					putSuffix = true;
				}

				else if(  (  currentChar>=  232 )  &&  (  currentChar<=  254 ) )	// if 19
				{
					if( (currentChar >= 232) && (currentChar <=249) )	// if 20
					{
						charIICF =  (byte)(currentChar - 218);
					}
					else
					{
						switch( currentChar )
						{
							case 250:
								charIICF = 35;
								break;
							case 251:
								charIICF = 34;
								break;
							case 252:
								charIICF = 32;
								break;
							case 253:
								charIICF = 36;
								break;
							case 254:
								charIICF = 37;
								break;
						}
					}

					if( charNo+1<inputWord.length() )	// if 21
					{
						switch(  inputWord.charAt(charNo+1) )	// sw 22
						{
							case 162:
								charNo++;
								//suffix = 0;
								//putSuffix = true;
								break;

							case 163:
								charNo++;
								suffix	 =   2;
								putSuffix = true;
								break;

							case 164:
								charNo++;
								suffix =   3;
								putSuffix = true;
								break;

							case 166:
								charNo++;
								suffix =   4;
								putSuffix = true;
								break;

							case 167:
								charNo++;
								suffix =  5 ;
								putSuffix = true;
								break;

							case 168:
								charNo++;
								suffix =  6 ;
								putSuffix = true;
								break;

							default :
								suffix =   1;
								putSuffix = true;
								break;
						} // end of switch
					}
					else
					{
						suffix =   1;
						putSuffix = true;
					}
				}
				else if(  currentChar == 172)	// if 23
				{
					if(charNo+1<inputWord.length())	// if 24
					{
						charIICF = getProperInternalCode(inputWord.charAt(charNo+1));
						if(charIICF != INVALID_NEXT)
							charNo++;

						suffix =   9 ;
						putSuffix = true;
					}
					else
					{
						charIICF = INVALID_NEXT;
						suffix = 9;
						putSuffix = true;
					}
				}
				else if(  currentChar == 170)	// if 25
				{ /////
					if(charNo+1<inputWord.length())	// if 26
					{
						charIICF = getProperInternalCode(inputWord.charAt(charNo+1));
						if(charIICF != INVALID_NEXT)
						{
							if( charNo+2<inputWord.length() )	// if 27
								switch(  inputWord.charAt(charNo+2) )	//sw 28
								{
									case 163 :
										suffix =   10 ;
										putSuffix = true;
										charNo+=2;
										break;

									case 247 :
										if( charNo+3<inputWord.length() )	// if 29
										{
											if(!checkLa(inputWord.charAt(charNo+3)))	// if 30
											{
												suffix =   7 ;
												putSuffix = true;
												charNo++;
												break;
											}
										}
										suffix =   12 ;
										putSuffix = true;
										charNo+=2;
										break;

									default :
										suffix =   7 ;
										putSuffix = true;
										charNo++;
										break;
								} // end of switch
							else
							{
								suffix =   7 ;
								putSuffix = true;
								charNo++;
							}
						}
						else
						{	// ªx : where x is invalid next character ex. Üª«
							charIICF = INVALID_NEXT;
							suffix = 7;
							putSuffix = true;
						}
					}
					else
					{	//xª : The word ends with « , ex. Üª
						charIICF = INVALID_NEXT;
						suffix = 7;
						putSuffix = true;
					}
				}
				else if(  currentChar == 171 )	// if 31
				{
					if(charNo+1<inputWord.length())	// if 32
					{
						charIICF = getProperInternalCode(inputWord.charAt(charNo+1));
						if( charIICF != INVALID_NEXT)
						{
							if( (charNo+2<inputWord.length()) &&  (  inputWord.charAt(charNo+2) == 163) )	// if 33
							{
								suffix =   11 ;
								putSuffix = true;
								charNo+=2;
							}
							else
							{
								suffix =   8 ;
								putSuffix = true;
								charNo++;
							}
						}
						else
						{	// «x : where x is invalid next character ex. Ü«ª
							charIICF = INVALID_NEXT;
							suffix = 8;
							putSuffix = true;
						}
					}
					else
					{	//x« : The word ends with « , ex. Ü«
						charIICF = INVALID_NEXT;
						suffix = 8;
						putSuffix = true;
					}
				}
				else if(  currentChar == 174 )	//	if 34
				{
					charIICF =   18;
					suffix =   3 ;
					putSuffix = true;
				}
				/*     else if( (  currentChar == 40 ) && ( i+2<p.length() ) && (  inputWord.charAt(i+2) == 41) )
				{	  // this condition is actully wrong this is due to alcrypt
				charIICF =   18;
				suffix =   3 ;
				putSuffix = true;
				i+=2;
				}  */

				else if(  currentChar == 175 )	// if 35
				{
					charIICF =   18;
					suffix =   4 ;
					putSuffix = true;
				}
				else if(  currentChar == 255 )	// if 36
					charIICF = 33;
				else
				{
					//System.out.println("1111");
					charIICF = INVALID_NEXT;
					putSuffix = true;
					switch(currentChar)
					{
						case 162:
							suffix = 1;
							break;

						case 163:
							suffix	 =   2;
							break;

						case 164:
							suffix =   3;
							break;

						case 166:
							suffix =   4;
							break;

						case 167:
							suffix =  5 ;
							break;

						case 168:
							suffix =  6 ;
							break;
					}
				}

			}//123
			al_wordIICF.add( new Byte( charIICF ) );
			if(putSuffix)
			{
				al_wordIICF.add( new Byte( suffix ));
				putSuffix = false;
			}
		}

		byte wordIICF[] = new byte[al_wordIICF.size()];

		for(int l=0; l<al_wordIICF.size() ;l++ )
		{
			wordIICF[l] =((Byte) al_wordIICF.get(l)).byteValue(); //(Byte)al_wordIICF.get(l);
		}

	return wordIICF;
	}


	/**
	*  Used to check the 'La' character(29) comes as a combination
	*  three letters or separate letter.
	*
	* @ param nexttola next character of 'la' in the text.
	* @ return boolean true if 'La' comes in three letter consonant i.e 'auv'
	*				   false if 'La' comes in two letters consonant
	*/
	public static boolean checkLa(char nexttola)
	{
		switch(nexttola)
		{
			case 162:   return false;
			case 163:   return false;
			case 164:   return false;
			case 166:   return false;
			default :   		return true;
		}
	}


	/**
	* Used to check whether the given word is Tamil word or non-Tamil word.
	* It checks the first character. If this is Tamil then the word is Tamil
	* word. Otherwise it is a non-Tamil word. Mixed words are not considered here.
	* That is they are decided with their first character.
	*
	* @ param word word whose language has to be identified.
	* @ boolean true if the word is tamil, otherwise false.
	*
	*/
	public static boolean checkLanguage(String word)
	{
		if(word.length() == 1)
		{
			if( (word.charAt(0) <=255 )  && ( word.charAt(0) >=170 )  )
				return true;
			else
				return false;
		}
		return false;
	}


	public static short getProperTABCode(byte internalCode)
	{
		short tabCode = 0;
		if(  (internalCode >= 14) && (internalCode <= 31)  )
			tabCode = (short)( internalCode+218 ) ;
		else if( internalCode == 32 )	tabCode = (short) 252;
		else if( internalCode == 34 )	tabCode = (short) 251;
		else if( internalCode == 35 )	tabCode = (short) 250;
		else if( internalCode == 36 )	tabCode = (short) 253;
		else if( internalCode == 37 )	tabCode = (short) 254;
		return tabCode;
	}

	public static byte getProperInternalCode(char tabCode)
	{
		byte internalCode = 0;
		if( (tabCode >= 232) && (tabCode <=249) )
		{
			internalCode =  (byte)(tabCode - 218);
		}
		else
		{
			switch( tabCode )
			{
				case 250:
					internalCode = 35;
					break;
				case 251:
					internalCode = 34;
					break;
				case 252:
					internalCode = 32;
					break;
				case 253:
					internalCode = 36;
					break;
				case 254:
					internalCode = 37;
					break;
				default :
					internalCode = INVALID_NEXT;
					break;
			}
		}
		return internalCode;
	}


}

