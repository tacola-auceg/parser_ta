package analyser;
import java.util.*;


public class Methods
{

	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();
	VVerbalParticiple vbp = new VVerbalParticiple();
	VTenses tense = new VTenses();
	VAuxillary aux = new VAuxillary();
	VGender removegender = new VGender();
	VClitics clitic = new VClitics();
	VMiddleGender middlegender = new VMiddleGender();
	VCheckInf infinitive = new VCheckInf();
	VNegative negative = new VNegative();
	Cases tcase = new Cases();
	PostPosition pospos = new PostPosition();
	Participles part = new Participles();

	BTree nbtree = new BTree();
	BTree vbtree = new BTree();
	BTree nmbtree = new BTree();
	BTree adjtree = new BTree();
	BTree advtree = new BTree();
	BTree partree = new BTree();
	BTree vththtree = new BTree();
	BTree intentree = new BTree();

	//public static ResourceBundle fileBundle = ResourceBundle.getBundle("fileBundle");

	public Methods()
	{

		vbtree.updateDictTree("./src/verb.txt");
		nbtree.updateDictTree("./src/noun.txt");
		nmbtree.updateDictTree("./src/mendingnoun.txt");
		adjtree.updateDictTree("./src/adjective.txt");
		advtree.updateDictTree("./src/adverb.txt");
		partree.updateDictTree("./src/particle.txt");
		vththtree.updateDictTree("./src/ththtakingverbs.txt");
		intentree.updateDictTree("./src/intensifiers.txt");

	}

	public Stack checkverb(String inputString)
	{
		Stack s=new Stack();
		boolean isonlyNoun = false;
		/**
		  * If the given string is endswith k or c or t or p
		  * remove the k or c or t or p
		  */

		if(isEndswithKCTP(inputString))
			inputString = inputString.substring(0,inputString.length()-2);

		/**
		  * check the remaing string in adjective.txt
		  * if presents tag the remaing string as adjective
		  */

		if(adjtree.contains(inputString))
		{
			s.push(tm.convert(inputString+" < adjective > "));
			return s;
		}

		/**
		  * check the remaing string in particle.txt
		  * if presents tag the remaing string as particle
		  */

		else if(intentree.contains(inputString))
		{
			s.push(tm.convert(inputString+" < Intensifier > "));
			return s;
		}
		//intensifiers like saala, chaRRu, mika,...
		else if(partree.contains(inputString))
		{
			s.push(tm.convert(inputString+" < Particle > "));
			return s;
		}
		/**
		  * check the remaing string in adverb.txt
		  * if presents tag the remaing string as adverb
		  */

		else if(advtree.contains(inputString))
		{
			s.push(tm.convert(inputString+" < adverb > "));
			return s;
		}

		/**
		  * check the remaing string in noun.txt
		  * if presents tag the remaing string as noun
		  */

		else if(nbtree.contains(inputString))
		{
			s.push(tm.convert(inputString+" < Noun > "));
			return s;
		}

		/**
		  * check the remaing string in verb.txt
		  * if presents tag the remaing string as verb
		  */

		else if(vbtree.contains(inputString))
		{
			s.push(tm.convert(inputString+" < Verb > "));
			return s;
		}
		/**
		  * if the remaing string is endswith kaaran or kaari or kaar
		  * tag it as noun
		  */
		else if(inputString.endsWith(VVariables.kaaran) ||inputString.endsWith(VVariables.kaar) ||inputString.endsWith(VVariables.kaari))
		{
			s.push(tm.convert(inputString+" < Noun > "));
			return s;
		}
		/**
		  * convert the given string as bytes for further processes
		  */

		byte givenString[] = tm.convert(inputString);

		//1 System.out.println(tm.revert(givenString));

		s.push(givenString);
		/**
		  * If the given string endswith 'a' and not ends with 'kooda' or 'aana' or 'ana' or 'aaka' or 'arra'
		  *  if the given string endswith 'kka' or 'ka'
		  *  	remove infinitive
		  *  else if the last suffix is a and the previous suffix is relative participle suffix like 'tha' or 'ththa'
		  */

		if(me.endswith(givenString,VVariables.a) && !me.endswith(givenString,VVariables.kooda) && !me.endswith(givenString,VVariables.aana) && !me.endswith(givenString,VVariables.ana) && !me.endswith(givenString,VVariables.aaga)&& !me.endswith(givenString,NVariables.arra)&&!me.endswith(givenString,NVariables.udaya))
		{


			if(me.endswith(givenString,VVariables.kka) || me.endswith(givenString,VVariables.ka))
			{
				s = infinitive.removeInf(s);
				s = checkForAux(s);
				if(vbtree.contains(tm.revert((byte[])s.peek())))
				{
					s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
					return s;
				}
			}
			else if(isEndswithRelativeParticipleSuffix(s))
			{
	//this code will repeat after removing the temporal adverbial participles
				s.pop();
				givenString = me.subarray(givenString,0,givenString.length-1);
				s.push(me.addarray(VVariables.a,Taggs.TagRelativeParticipleSuffix));
				s.push(givenString);
				s = tense.checktenses(s);
				s = checkForAux(s);
				if(vbtree.contains(tm.revert((byte[])s.peek())))
				{
					s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
					return s;
				}
			}
			else
			{

				s = infinitive.removeInf(s);
				s = checkForAux(s);

				if(vbtree.contains(tm.revert((byte[])s.peek())))
				{
					s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
					return s;
				}
				else
				{
					while(!s.empty())
						s.pop();

					if(nmbtree.contains(inputString+"ñ¢"))
					{
					  s.push(me.addarray(tm.convert(inputString+"ñ¢"),Taggs.TagNoun));
					  return s;
					}
					else
					{
						s.push(me.addarray(givenString,tm.convert(" < Relative Participle  - Proper Noun> ")));
						return s;
					}
				}
			}
		}

		while(true)
		{
			givenString = (byte[])s.peek();
			if(me.endswith(givenString,VVariables.um))
			{
				if(me.endswith(givenString,VVariables.vendum)||me.endswith(givenString,VVariables.koodum))
					break;
				else
				 {
						s = clitic.removeUm(s);
						if(isEndswithFutureNeuterSuffix(s))
						{
							Object tempobj = s.pop();
							String tempstr = tm.revert((byte[])s.pop());
							s.push(tm.convert(tempstr.substring(0,(tempstr.indexOf(" < "))+3)+"Neuter Gender >"));
							s.push(tempobj);
							s = tense.removeFutureNeuterSuffix(s);
							s = checkForAux(s);

							if(vbtree.contains(tm.revert((byte[])s.peek())))
							{
								s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
								return s;
							}
						}
				 }
			}
			else if(isEndswithThaan(givenString))
				s = clitic.removeThaan(s);
			else if(me.endswith(givenString,VVariables.adaa)||me.endswith(givenString,VVariables.adi))
				s = clitic.removeAdaaAdi(s);
			else if(me.endswith(givenString,VVariables.ae) && !me.endswith(givenString,VVariables.aathey))
				s = clitic.removeAe(s);
			else if(me.endswith(givenString,VVariables.kooda))
				s = clitic.removeKooda(s);
			else if(me.endswith(givenString,VVariables.aa))
				s = clitic.removeAa(s);
			else if(me.endswith(givenString,VVariables.oo))
				s = clitic.removeOo(s);
			else break;
		}
/**
  * code to handle the temporal adverbial participles like poothu poozhuthu vari
  */
		if(part.isEndswithTemporalAdverbialParticiple((byte[])s.peek())) //||||/*||part.isEndswithTemporalAdverbialParticiple((byte[])s.peek())*/)
		{

			s = part.removeTemporalAdverbialParticipls(s);
		}
		else if(part.isEndswithManneralAdverbialParticiple((byte[])s.peek()))
		{
			s = part.removeManneralAdverbialParticipls(s);
		}
		else if(part.isEndswithLimitativeAdverbialParticiple((byte[])s.peek()))
		{
			s = part.removeLimitativeAdverbialParticipls(s);
		}
			//s = part.removeAdverbialParticipls(s);

	    if(isEndswithRelativeParticipleSuffix(s)||(me.endswith((byte[])s.peek(),VVariables.um)))
		{
			byte[] firstElement = (byte[]) s.pop();
			boolean gotoTense = true;

			if(me.endswith(firstElement,VVariables.a))
			{
				firstElement = me.subarray(firstElement,0,firstElement.length-1);
				s.push(me.addarray(VVariables.a,Taggs.TagRelativeParticipleSuffix));
			}
			else if(me.endswith(firstElement,VVariables.um))
			{
				firstElement = me.subarray(firstElement,0,firstElement.length-2);
				s.push(me.addarray(VVariables.um,Taggs.TagRelativeParticipleSuffix));
				if(me.endswith(firstElement,VVariables.y))
				{
					firstElement = me.subarray(firstElement,0,firstElement.length-1);
					s.push(me.addarray(VVariables.y,tm.convert(" < Sandhi > ")));
					gotoTense = false;
				}
				else if(me.endswith(firstElement,VVariables.kk))
				{
					firstElement = me.subarray(firstElement,0,firstElement.length-2);
					s.push(me.addarray(VVariables.kk,tm.convert(" < VP (Infinitive ) Suffix > ")));
					gotoTense = false;
				}
			 }
			 s.push(firstElement);
			 if(gotoTense)
				s = tense.checktenses(s);
			 s = checkForAux(s);
			 if(vbtree.contains(tm.revert((byte[])s.peek())))
			 {
			 	s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
				return s;
			 }
			 return s;
		  }

			if(pospos.checkPostPositions(s))
					isonlyNoun = true;


		if(nbtree.contains(tm.revert((byte[])s.peek())))
		{
			s.push(me.addarray((byte[])s.pop(),Taggs.TagNoun));
			return s;
		}




		if(isonlyNoun == false)
		{
			givenString = (byte[])s.peek();
			if(me.endswith(givenString,VVariables.alaam))
			{
				s = infinitive.removePotentials(s);
			}

			else if(me.endswith(givenString,VVariables.aathey))
			{
				s = negative.removeAathey(s);
				s = infinitive.removeInf(s);
			}
			else if(me.endswith(givenString,VVariables.aatheergal))
			{
				s = negative.removeAatheergal(s);
				s = infinitive.removeInf(s);
			}
			else if(me.endswith(givenString,VVariables.aamal))
			{
				s = negative.removeAamal(s);
				s = infinitive.removeInf(s);
			}
			else if(me.endswith(givenString,VVariables.aathu))
			{
				s = negative.removeAathu(s);
				s = infinitive.removeInf(s);
			}
			else if(me.endswith(givenString,VVariables.aavittal))
			{
				s = negative.removeAavittal(s);
				s = infinitive.removeInf(s);
			}
			else if(me.endswith(givenString,VVariables.ungal))
			{
				s = negative.removeUngal(s);
				s = checkForAux(s);
			}


			else if(me.endswith((byte[])s.peek(),VVariables.thal))
				s = clitic.removeThal(s);
			else if(isEndswithVbp(s))
				s = vbp.checkVbp(s,(byte[])s.pop());

			s = checkForAux(s);

			if(vbtree.contains(tm.revert((byte[])s.peek())))
			{
				s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
				return s;
			}


			if(me.endswith((byte[])s.peek(),VVariables.aal))
			{
				if(isEndswithTMaal(s))// is endswith (tensemarker + aal) but not endswith (thth + aal)
				{
				   s = clitic.removeAal(s);
			       s = tense.checktenses(s);
				}// remove conditional aal
				else if(isEndswithThThaal(s)) // is endswith (thth+aal)
				{
					 Object tempObject = s.peek();
					 s = tcase.remove_aal(s);
					 s = removeOblique(s);
					 if(nmbtree.contains(tm.revert((byte[])s.peek())))
					 {
						s.push(me.addarray((byte[])s.pop(),Taggs.TagNoun));
					   return s;
					 }
					 else
					 {
						  s.pop();
						  s.pop();
						  s.pop();
						  s.push(tempObject);
						  s = clitic.removeAal(s);
			       	  	  s = tense.checktenses(s);
			       	  	  if(vbtree.contains(tm.revert((byte[])s.peek())))
			       	  	  {
							s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
							return s;
						  }

			       	  // check with thth taking verbs
					 }
				}
			}
		}
		if(isEndswithCase(s))
		{
			s = removeCases(s);
			if(isEndswithOblique(s))
			  s = removeOblique(s);
		}

		if(isEndswithPluralMarker(s))
			s = removePluralMarker(s);

		if(nbtree.contains(tm.revert((byte[])s.peek())))
		{
			s.push(me.addarray((byte[])s.pop(),Taggs.TagNoun));
			return s;
		}

		if(isEndswithPronouns(s))
		{
			s = middlegender.checkMiddleGender(s);
			s = tense.checktenses(s);
			if(vbtree.contains(tm.revert((byte[])s.peek())))
				s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));

		}
		if(isEndswithAuxInfEnd(s))
			s = removeAuxInfEnd(s);
		else if(isEndswithAuxVbpEnd(s))
		{
			s = removeAuxVbpEnd(s);
			s = vbp.checkVbp(s,(byte[])s.pop());
		}
		else if(isEndswithGenderEndings(s))
		{
			s = removegender.checkaalaan(s,(byte[])s.pop());
			if(me.endswith((byte[])s.peek(),VVariables.maatt))
				s.push(me.addarray((byte[])s.pop(),VVariables.u));
			s = tense.checktenses(s);

		}

		s = checkForAux(s);

		if(vbtree.contains(tm.revert((byte[])s.peek())))
			s.push(me.addarray((byte[])s.pop(),Taggs.TagVerb));
/*		else
		{
			while(!s.empty())
				s.pop();
			System.out.println(tm.revert(givenString));
			s.push(me.addarray(givenString,tm.convert(" < May Be Noun > ")));
		}*/

		return s;

	}



	public Stack removeCases(Stack s)
	{
		byte[] topElement = (byte[])s.peek();

		if(me.endswith(topElement,NVariables.aal))
			s = tcase.remove_aal(s);
		else if(me.endswith(topElement,NVariables.il))
			s = tcase.checkil(s);
		else if(me.endswith(topElement,NVariables.in))
			s = tcase.checkin(s);
		else if(me.endswith(topElement,NVariables.ukkaaga))
			s = tcase.check_ukkaaga (s);
		else if(me.endswith(topElement,NVariables.ukkaana))
			s = tcase.check_ukkaana (s);
		else if(me.endswith(topElement,NVariables.kkaaga))
			s = tcase.check_kkaaga (s);
		else if(me.endswith(topElement,NVariables.akka))
			s = tcase.check_akka(s);
		else if(me.endswith(topElement,NVariables.aana))
			s = tcase.check_aana(s);
		else if(me.endswith(topElement,NVariables.ukku))
			s = tcase.check_ukku(s);
		else if(me.endswith(topElement,NVariables.akku))
			s = tcase.check_akku(s);
		else if(me.endswith(topElement,NVariables.kku))
			s = tcase.check_kku(s);
		else if(me.endswith(topElement,NVariables.irku))
			s = tcase.check_irku(s);
		else if(me.endswith(topElement,NVariables.ai))
			s = tcase.check_ai(s);
		else if(me.endswith(topElement,NVariables.ilirunthu))
			s = tcase.check_ilirunthu(s);
		else if(me.endswith(topElement,NVariables.itamirunthu))
			s = tcase.check_itamirunthu(s);
		else if(me.endswith(topElement,NVariables.udaiyathu))
			s.push("This method is not available");
		else if(me.endswith(topElement,NVariables.udaya))
			s = tcase.check_udaya (s);
		else if(me.endswith(topElement,NVariables.utan))
			s = tcase.check_utan(s);
		else if(me.endswith(topElement,NVariables.odu))
			s = tcase.check_odu(s);
		else if(me.endswith(topElement,NVariables.itam))
			s = tcase.checkitam(s);
		else if(me.endswith(topElement,NVariables.arra))
			s = tcase.remove_arra(s);
		else if(me.endswith(topElement,NVariables.arru))
			s = tcase.remove_arru(s);
		return s;
	}

	boolean isEndswithCase(Stack s)
	{
		byte tempbyte[] = (byte[])s.peek();
		if(me.endswith(tempbyte,VVariables.illai))
			return false;
		if(me.endswith(tempbyte,NVariables.il)||me.endswith(tempbyte,NVariables.ai)
			||me.endswith(tempbyte,NVariables.in)||me.endswith(tempbyte,NVariables.ukkaaga)
			||me.endswith(tempbyte,NVariables.kkaaga)||me.endswith(tempbyte,NVariables.akka)
			||me.endswith(tempbyte,NVariables.ukku)||me.endswith(tempbyte,NVariables.kku)
			||me.endswith(tempbyte,NVariables.irku)||me.endswith(tempbyte,NVariables.ilirunthu)
			||me.endswith(tempbyte,NVariables.itamirunthu)||me.endswith(tempbyte,NVariables.udaya)
			||me.endswith(tempbyte,NVariables.utan)||me.endswith(tempbyte,NVariables.odu)
			||me.endswith(tempbyte,NVariables.itam)||me.endswith(tempbyte,NVariables.aal)
			||me.endswith(tempbyte,NVariables.aana)||me.endswith(tempbyte,NVariables.arru)
			||me.endswith(tempbyte,NVariables.arra))
			return true;
		else
			return false;
	}
/**
  * If the given string endswith thaan the previous string should endswith
  *  either a pronoun or a clitic  or a case
  */
	boolean isEndswithThaan(byte[] givenWord)
	{
		if(me.before_endswith(givenWord,VVariables.avan,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.aval1,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.avar,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.adhu,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.avai,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.avarkal,VVariables.thaan)
			||me.before_endswith(givenWord,NVariables.ku,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.aal,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.odu,VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.um,VVariables.thaan)
			||me.before_endswith(givenWord,me.addarray(VVariables.kooda,VVariables.th),VVariables.thaan)
			||me.before_endswith(givenWord,VVariables.ae,VVariables.thaan)
			||me.before_endswith(givenWord,me.addarray(VVariables.ai,VVariables.th),VVariables.thaan))
				return true;
		else
			return false;
	}


	public Stack removeAuxInfEnd(Stack s)
	{
		byte[] topelement= (byte[])s.pop();

		if(me.endswith(topelement,VVariables.vendum))
			s = aux.checkVendum(s,topelement);
		if(me.endswith(topelement,VVariables.vendaam))
			s = aux.checkVendaam(s,topelement);
		if(me.endswith(topelement,VVariables.koodum))
			s = aux.checkKoodum(s,topelement);
		if(me.endswith(topelement,VVariables.koodathu))
			s = aux.checkKoodathu(s,topelement);
		if(me.endswith(topelement,VVariables.illai))
			s = aux.checkIllai(s,topelement);

		s = infinitive.removeInf(s);

		return s;
	}

	public Stack removeAuxVbpEnd(Stack s)
	{
		byte[][] auxVbpEnd = {VVariables.aayirru,VVariables.poyirru};

		Object obj = new Object();

		obj = s.pop();

		byte bfullstring[] = (byte[])obj;

		if(me.endswith(bfullstring,auxVbpEnd[0]))
			s = aux.checkAayirru(s,bfullstring);
		else if(me.endswith(bfullstring,auxVbpEnd[1]))
			s = aux.checkPoyirru(s,bfullstring);

		return s;
	}

	public Stack checkAuxCat1(Stack s)
	{
		Object obj = new Object();

		obj = s.pop();

		byte bfullstring[] = (byte[])obj;

		if(me.endswith(bfullstring,VVariables.vidu))
			s = aux.checkVidu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.koll))
			s = aux.checkKoll(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kattu))
			s = aux.checkKattu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kaattu))
			s = aux.checkKaattu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.tholai))
			s = aux.checkTholai(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.azu))
			s = aux.checkAzu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kodu))
			s = aux.checkKodu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kida))
			s = aux.checkKida(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.kizi))
			s = aux.checkKizi(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.thallu))
			s = aux.checkThallu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.podu))
			s = aux.checkPodu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.theer))
			s = aux.checkTheer(s,bfullstring);
		else s.push(obj);

		if( ((byte[]) s.peek())[0]!=0)
		{
			s = vbp.checkVbp(s,(byte[])s.pop());
			s = checkForAux(s);
		}
		return s;
	}

	public Stack checkAuxCat3(Stack s)
	{
		Object obj = new Object();
		obj = s.pop();
		byte bfullstring[] = (byte[])obj;
		if(me.endswith(bfullstring,VVariables.iru))
			s = aux.checkIru(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.poo))
			s = aux.checkPoo(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.vaa)||me.endswith(bfullstring,VVariables.varu))
			s = aux.checkVaa(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.vai))
			s = aux.checkVai(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.mudi))
			s = aux.checkMudi(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.paar))
			s = aux.checkPaar(s,bfullstring);
		else
			s.push(obj);
		if( ((byte[]) s.peek())[0]!=0)
		{
			s = vbp.checkVbp(s,(byte[])s.pop());
			s = infinitive.removeInf(s);
			s = checkForAux(s);
		}
		return s;
	}

	public Stack checkAuxCat2(Stack s)
	{
		Object obj = new Object();
		obj = s.pop();
		byte bfullstring[] = (byte[])obj;

		if(me.endswith(bfullstring,VVariables.padu))
			s = aux.checkPadu(s,bfullstring);
		else if(me.endswith(bfullstring,VVariables.maattu))
			s = aux.checkMaattu(s,bfullstring);

		s = infinitive.removeInf(s);
		s = checkForAux(s);
		return s;
	}

	public Stack checkForAux(Stack s)
	{

		byte bfullstring[] = (byte[]) s.peek();

		if(bfullstring.length > 0)
		{
			if(me.endswith(bfullstring,VVariables.vidu)||me.endswith(bfullstring,VVariables.koll)
				||me.endswith(bfullstring,VVariables.theer)||me.endswith(bfullstring,VVariables.kaattu)||me.endswith(bfullstring,VVariables.tholai)||me.endswith(bfullstring,VVariables.azu)||me.endswith(bfullstring,VVariables.kodu)||me.endswith(bfullstring,VVariables.kida)||me.endswith(bfullstring,VVariables.kizi)||me.endswith(bfullstring,VVariables.thallu))
				s = checkAuxCat1(s);
			else if(me.endswith(bfullstring,VVariables.iru)||me.endswith(bfullstring,VVariables.poo)||me.endswith(bfullstring,VVariables.vaa)||me.endswith(bfullstring,VVariables.varu)||me.endswith(bfullstring,VVariables.vai)||me.endswith(bfullstring,VVariables.mudi)||me.endswith(bfullstring,VVariables.paar))
				s = checkAuxCat3(s);
			else if(me.endswith(bfullstring,VVariables.padu)||me.endswith(bfullstring,VVariables.maattu))
				s = checkAuxCat2(s);
		}
		return s;
	}

	boolean isEndswithAuxVbpEnd(Stack s)
	{
		byte[][] auxVbpEnd = {VVariables.aayirru,VVariables.poyirru};
		byte[] givenWord = (byte[])s.peek();

		for(int i=0; i < auxVbpEnd.length; i++)
			if(me.endswith(givenWord,auxVbpEnd[i]))
				return true;
		return false;
	}

	boolean isEndswithVbp(Stack s)//refer palani
	{
		byte[][] vbpList = {VVariables.thu,VVariables.ththu,VVariables.nththu,VVariables.nrru,VVariables.n1tu,VVariables.rr1u,VVariables.e};
		byte[] givenWord = (byte[])s.peek();

		if(me.endswith(givenWord,NVariables.athu)||me.endswith(givenWord,VVariables.adhu)||me.endswith(givenWord,NVariables.ilirunthu)||me.endswith(givenWord,NVariables.itamirunthu)||me.endswith(givenWord,VVariables.paduttu)||me.endswith(givenWord,VVariables.pozhuthu)||me.endswith(givenWord,VVariables.poothu))
			return false;
		for(int i=0; i < vbpList.length; i++)
			if(me.endswith(givenWord,vbpList[i]))
				return true;
		return false;
	}

	boolean isEndswithAuxInfEnd(Stack s)
	{
		byte[][] auxInfEnd = {VVariables.vendum,VVariables.vendaam,VVariables.koodum,VVariables.koodathu,
							VVariables.illai};
		byte[] givenWord = (byte[])s.peek();

		for(int i=0; i < auxInfEnd.length; i++)
			if(me.endswith(givenWord,auxInfEnd[i]))
				return true;
		return false;
	}

	boolean isEndswithGenderEndings(Stack s)
	{
		byte[][] genderEnding = { VVariables.ar,VVariables.aan,VVariables.aal1,VVariables.aar,VVariables.adhu,
											VVariables.ana,VVariables.aarkal,VVariables.eerkal,VVariables.ein,
											VVariables.om,VVariables.aai,VVariables.aai,VVariables.anar};
		byte[] givenWord = (byte[])s.peek();

		for(int i=0; i < genderEnding.length; i++)
			if(me.endswith(givenWord,genderEnding[i]))
				return true;
		return false;
	}

	boolean isEndswithPronouns(Stack s)
	{
		byte[][] pronouns = { VVariables.avan,VVariables.aval1,VVariables.avar,
									VVariables.avai,VVariables.avarkal};
		byte[] givenWord = (byte[])s.peek();

		for(int i=0; i < pronouns.length; i++)
			if(me.endswith(givenWord,pronouns[i]))
				return true;
		return false;
	}

	boolean isEndswithOblique(Stack s)
	{
		byte[][] obliques = {VVariables.ththu,VVariables.thth,NVariables.tt,NVariables.arru};

		byte[] givenWord = (byte[])s.peek();

		for(int i=0;i < obliques.length;i++)
			if(me.endswith(givenWord,obliques[i]))
				return true;
		return false;
	}

	boolean isEndswithPluralMarker(Stack s)
	{
		if(me.endswith((byte[])s.peek(),NVariables.kal))
			if(me.endswith((byte[])s.peek(),VVariables.makkal) || me.endswith((byte[])s.peek(),VVariables.magal)|| me.endswith((byte[])s.peek(),VVariables.aarkal))
				return false;
			else return true;
		else
			return false;
	}

	public Stack removePluralMarker(Stack s)
	{

		byte[] givenWord = (byte[])s.pop();

		if(me.endswith(givenWord,NVariables.kkal))
		{
			byte pluralSuffix[] = me.subarray(givenWord,givenWord.length-NVariables.kkal.length,givenWord.length);
			byte remainingString[] = me.subarray(givenWord,0,givenWord.length-NVariables.kkal.length);

			s.push(me.addarray(pluralSuffix,Taggs.TagPlural));
			s.push(remainingString);
		}

		else if(me.endswith(givenWord,NVariables.kal))
		{
			byte pluralSuffix [] = me.subarray(givenWord,givenWord.length-NVariables.kal.length,givenWord.length);
			byte remainingString[] = me.subarray(givenWord,0,givenWord.length-NVariables.kal.length);

			s.push(me.addarray(pluralSuffix,Taggs.TagPlural));
			if(me.endswith(remainingString,NVariables.ng))
				remainingString = me.addarray(me.subarray(remainingString,0,remainingString.length-1),NVariables.m);
			else if(me.endswith(remainingString,NVariables.t))
				remainingString = me.addarray(me.subarray(remainingString,0,remainingString.length-1),NVariables.l1);
			s.push(remainingString);
		}

		return s;
	}

	public Stack removeOblique(Stack s)
	{

		byte toremove[] = (byte[])s.pop();

		if(me.endswith(toremove,VVariables.ththu))
		{
			byte[] removed = me.subarray(toremove,toremove.length-VVariables.ththu.length,toremove.length);
			byte[] remainder = me.addarray(me.subarray(toremove,0,toremove.length-VVariables.ththu.length),NVariables.m);

			s.push(me.addarray(removed,Taggs.TagOblique));
			s.push(remainder);
		}
		else if(me.endswith(toremove,NVariables.thth))
		{
			byte[] removed = me.subarray(toremove,toremove.length-NVariables.thth.length,toremove.length);
			byte[] remainder = me.addarray(me.subarray(toremove,0,toremove.length-NVariables.thth.length),NVariables.m);

			s.push(me.addarray(removed,Taggs.TagOblique));
			s.push(remainder);
		}
		else if(me.endswith(toremove,NVariables.tt))
		{
			byte[] removed = me.subarray(toremove,toremove.length-NVariables.t.length,toremove.length);
			byte[] remainder = me.addarray(me.subarray(toremove,0,toremove.length-NVariables.t.length),NVariables.u);

			s.push(me.addarray(removed,Taggs.TagOblique));
			s.push(remainder);
		}
		else if(me.endswith(toremove,NVariables.arru))
		{
			byte[] removed = me.subarray(toremove,toremove.length-NVariables.arru.length,toremove.length);
			byte[] remainder = me.subarray(toremove,0,toremove.length-NVariables.arru.length);


			s.push(me.addarray(removed,Taggs.TagOblique));

			if(me.endswith(remainder,NVariables.iv))
				remainder = me.subarray(remainder,0,remainder.length-1);

			s.push(remainder);
		}
		else
			s.push(toremove);
		return s;
	}

	boolean isEndswithRelativeParticipleSuffix(Stack s)
	{
		byte tenseMarkers[][] = {VVariables.kir,VVariables.th,VVariables.nth,VVariables.n,VVariables.thth,VVariables.t,VVariables.r1,VVariables.rr1,VVariables.nn,VVariables.n1tu,VVariables.tt/*VVariables.arukiru,VVariables.arukir,,VVariables.aruv,VVariables.r1p,VVariables.tpVVariables,pp,VVariables.nr1,VVariables.an1t,,VVariables.nr1*/};
		byte[] givenWord = (byte[])s.peek();
		for(int i=0;i<tenseMarkers.length;i++)
			if(me.before_endswith(givenWord,tenseMarkers[i],VVariables.a))
				return true;
		return false;
	}
	boolean isEndswithTenseMarker(Stack s)
	{
		byte tenseMarkers[][] = {VVariables.kiru,VVariables.kir,VVariables.th,VVariables.nth,VVariables.n,VVariables.thth,VVariables.t,VVariables.in,VVariables.r1,VVariables.rr1,VVariables.nn,VVariables.n1tu,VVariables.tt/*VVariables.arukiru,VVariables.arukir,,VVariables.aruv,VVariables.r1p,VVariables.tpVVariables,pp,VVariables.nr1,VVariables.an1t,,VVariables.nr1*/};

		byte[] givenWord = (byte[])s.peek();

		if(me.endswith(givenWord,NVariables.utan)||me.endswith(givenWord,VVariables.aan))
			return false;
		for(int i=0;i<tenseMarkers.length;i++)
			if(me.endswith(givenWord,tenseMarkers[i]))
				return true;
		return false;
	}
	boolean isEndswithKCTP(String inputString)
	{
		if(inputString.endsWith("è¢")||inputString.endsWith("ê¢")||inputString.endsWith("î¢")||inputString.endsWith("ð¢"))
			return true;
		return false;
	}
	boolean isEndswithFutureNeuterSuffix(Stack s)
	{
		 byte futureNeuterSuffixes[][] = {VVariables.y,VVariables.yy,VVariables.zh,VVariables.kk,VVariables.n1n1,VVariables.t,VVariables.r1,VVariables.ll,VVariables.l1l1,VVariables.tk,VVariables.r1k,VVariables.ar};
		 byte[] givenWord = (byte[])s.peek();

		 for(int i=0;i<futureNeuterSuffixes.length;i++)
		 	if(me.endswith(givenWord,futureNeuterSuffixes[i]))
		 		return true;
		 return false;

	}
	boolean isEndswithTMaal(Stack s)
	{
		 byte tenseMarkers[][] = {VVariables.th,VVariables.nth,VVariables.t,VVariables.in,VVariables.r1,VVariables.nn,VVariables.tt/*VVariables.arukiru,VVariables.arukir,,VVariables.aruv,VVariables.r1p,VVariables.tpVVariables,pp,VVariables.nr1,VVariables.an1t,,VVariables.nr1*/};

		 byte[] givenWord = (byte[])s.peek();

		 if(me.before_endswith(givenWord,VVariables.thth,VVariables.aal))
			return false;
	 	 for( int i=0;i<tenseMarkers.length;i++)
		 	if(me.before_endswith(givenWord,tenseMarkers[i],VVariables.aal))
		 		return true;
		 return false;
	}

	boolean isEndswithThThaal(Stack s)
	{
		 byte[] givenWord = (byte[])s.peek();

		 if(me.before_endswith(givenWord,VVariables.thth,VVariables.aal))
		 {
			return true;
		 }
		  return false;
	}
}