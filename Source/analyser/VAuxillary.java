package analyser;

import java.util.Stack;
public class VAuxillary
{
	TabMethods tm = new TabMethods();
	ByteMeth me = new ByteMeth();
	byte[] TagAuxVerb = tm.convert(" < auxillary verb > ");
	byte[] TagVerb = tm.convert(" < verb > ");
/**
  * This method is to remove the auxillary aayirru
  * also if the remaining word contains 'tt' at the end
  * then it removes the 't' and adds one 'u'
  * otherwise just adds 'u'
  */
	public Stack checkAayirru(Stack s,byte [] s1)
	{
		if(me.endswith(s1,VVariables.aayirru))
		{
			byte suffix[]=me.subarray(s1,s1.length-VVariables.aayirru.length,s1.length);
			byte rem[]=me.subarray(s1,0,s1.length-VVariables.aayirru.length);
			if(rem.length==0)
			{
				s.push(me.addarray(suffix,TagVerb));
				return s;
			}
			s.push(me.addarray(suffix,TagAuxVerb));
			if(me.endswith(rem,VVariables.tt))
	 			rem = me.addarray(me.subarray(rem,0,rem.length-1),VVariables.u);
	 		else
				rem = me.addarray(rem,VVariables.u);
				s.push(rem);
		}
		return s;
	}

/**
  * This method is to remove the auxillary poyirru
  * also if the remaining word contains 'p' at the end
  * then it removes the 'p'
  */


	public Stack checkPoyirru(Stack s,byte [] s1)
	{
		if(me.endswith(s1,VVariables.poyirru))
		{
			byte suffix[]=me.subarray(s1,s1.length-VVariables.poyirru.length,s1.length);
			byte rem[]=me.subarray(s1,0,s1.length-VVariables.poyirru.length);

			if(rem.length==0)
			{
				s.push(me.addarray(suffix,TagVerb));
				return s;
			}
			s.push(me.addarray(suffix,TagAuxVerb));
			if(me.endswith(rem,VVariables .p))
				rem = me.subarray(rem,0,rem.length-1);
			s.push(rem);
		}
		return s;
	}

/**
  * This method is to remove the auxillary Iru
  * also if the remaining word contains 'y' at the end
  * then it removes the 'y'
  *
  */

	public Stack checkIru(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.iru))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.iru.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.iru.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			if(me.endswith(mainVerb,VVariables.y)||me.endswith(mainVerb,VVariables.iv))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			/*if(me.endswith(mainVerb,VVariables.ae))
			{
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
				s.push("\n"+tm.revert(VVariables.ae)+" (Þ¬ìê¢ªê£ô¢)");
			}*/
			if(me.endswith(mainVerb,VVariables.tt))
				mainVerb = me.addarray(me.subarray(mainVerb,0,mainVerb.length-1),VVariables.u);
			if(me.endswith(mainVerb,VVariables.t))
				mainVerb = me.addarray(mainVerb,VVariables.u);
			s.push(mainVerb);
		}
		return s;
	}

	public Stack  checkKattu(Stack s,byte[] str)
	{
		if(me.endswith(str,VVariables.kattu))
		{
			byte auxVerb[]=me.subarray(str,str.length-VVariables.kattu.length,str.length);
			byte mainVerb[]=me.subarray(str,0,(str.length-VVariables.kattu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			if(me.endswith(mainVerb,VVariables.k))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(mainVerb);
		}
		return s;
	}

	public Stack checkTheer(Stack s,byte[] str)
	{
		if(me.endswith(str,VVariables.theer))
		{
			byte auxVerb[]=me.subarray(str,str.length-VVariables.theer.length,str.length);
			byte mainVerb[]=me.subarray(str,0,(str.length-VVariables.theer.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push("\n"+tm.revert(auxVerb)+" < auxillary verb > ");
			if(me.endswith(mainVerb,VVariables.th))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(mainVerb);
		}
		return s;
	}

	public Stack checkVidu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.vidu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.vidu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.vidu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}


//anand
	public Stack checkKoll(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.koll))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.koll.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.koll.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			if(me.endswith(mainVerb,VVariables.k))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(mainVerb);
		}
		return s;
	}
	public Stack checkPadu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.padu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.padu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.padu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			if(me.endswith(mainVerb,VVariables.p))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(mainVerb);
		}
		return s;
	}
	public Stack checkMaattu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.maattu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.maattu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.maattu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			if(me.endswith(mainVerb,VVariables.p))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(mainVerb);
		}
		return s;
	}

//kaattu
	public Stack checkKaattu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.kaattu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.kaattu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.kaattu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.k))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}

//tholai
	public Stack checkTholai(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.tholai))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.tholai.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.tholai.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.th))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}

//azu
	public Stack checkAzu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.azu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.azu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.azu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}

//kodu
	public Stack checkKodu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.kodu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.kodu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.kodu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.k))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}

//kida
	public Stack checkKida(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.kida))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.kida.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.kida.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.k))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}

//kizi
	public Stack checkKizi(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.kizi))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.kizi.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.kizi.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.k))
	 			mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//thallu
	public Stack checkThallu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.thallu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.thallu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.thallu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.th))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//podu
	public Stack checkPodu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.podu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.podu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.podu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.p))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//poo
	public Stack checkPoo(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.poo))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.poo.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.poo.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
					return s;
			}
			if(me.endswith(mainVerb,VVariables.p))
	 			mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//vaa
	public Stack checkVaa(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.vaa))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.vaa.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.vaa.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		else if(me.endswith(byteVerb,VVariables.varu))
		{
			byte auxVerb[]= VVariables.vaa;//me.subarray(byteVerb,byteVerb.length-VVariables.varu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.varu.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//cey
	public Stack checkCey(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.cey))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.cey.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.cey.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//vai
	public Stack checkVai(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.vai))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.vai.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.vai.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//mudi
	public Stack checkMudi(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.mudi))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.mudi.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.mudi.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//paar
	public Stack checkPaar(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.paar))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.paar.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.paar.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.p))
	 			mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//koodum
	public Stack checkKoodum(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.koodum))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.koodum.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.koodum.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			if(me.endswith(mainVerb,VVariables.k))
	 			mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//koodathu
	public Stack checkKoodathu(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.koodathu))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.koodathu.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.koodathu.length));
			if(mainVerb.length==0)
			{
					s.push(me.addarray(auxVerb,TagVerb));
					return s;
			}
			if(me.endswith(mainVerb,VVariables.k))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//vendum
	public Stack checkVendum(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.vendum))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.vendum.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.vendum.length));
			if(mainVerb.length==0)
				{
					s.push(me.addarray(auxVerb,TagVerb));
					return s;
				}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//vendaam
	public Stack checkVendaam(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.vendaam))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.vendaam.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.vendaam.length));
			if(mainVerb.length==0)
			{
				s.push(me.addarray(auxVerb,TagVerb));
				return s;
			}
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
//illai
	public Stack checkIllai(Stack s,byte[] byteVerb)
	{
		if(me.endswith(byteVerb,VVariables.illai))
		{
			byte auxVerb[]=me.subarray(byteVerb,byteVerb.length-VVariables.illai.length,byteVerb.length);
			byte mainVerb[]=me.subarray(byteVerb,0,(byteVerb.length-VVariables.illai.length));
			if(me.endswith(mainVerb,VVariables.iv))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			else if(me.endswith(mainVerb,VVariables.y))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			s.push(me.addarray(auxVerb,TagAuxVerb));
			s.push(mainVerb);
		}
		return s;
	}
}