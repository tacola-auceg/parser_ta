package analyser;

import java.util.Stack;

public class VTenses
{
	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();

	public Stack checktenses(Stack s)
	{
		byte [] s1 = (byte[]) s.pop();

		if(me.endswith(s1,VVariables.in))
		{
			byte r[]=me.subarray(s1,s1.length-2,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-2));

			if(me.endswith(a,VVariables.t))
				a = me.addarray(a,VVariables.u);
			if(me.endswith(a,VVariables.th)||me.endswith(a,VVariables.iv)||me.endswith(a,VVariables.r1)||me.endswith(a,VVariables.k))
				a = me.addarray(a,VVariables.u);

			s.push(me.addarray(r,Taggs.TagPastTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.e))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));

			if(me.endswith(a,VVariables.t)||me.endswith(a,VVariables.th)||me.endswith(a,VVariables.iv)||me.endswith(a,VVariables.r1)||me.endswith(a,VVariables.k))
				a = me.addarray(a,VVariables.u);

			s.push(me.addarray(r,Taggs.TagPastTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.nthth))
		{
			byte a1[]=me.subarray(s1,s1.length-2,s1.length);
			byte a2[]=me.subarray(s1,0,(s1.length-2));

			if(me.isequal(a2,VVariables.va))
				a2 = VVariables.vaa;
			if(me.isequal(a2,VVariables.tha))
				a2 = VVariables.thaa;

			s.push(me.addarray(a1,Taggs.TagPastTenseMarker));
			s.push(a2);
		}
		else if(me.endswith(s1,VVariables.thth))
		{
			byte a1[]=me.subarray(s1,s1.length-2,s1.length);
			byte a2[]=me.subarray(s1,0,(s1.length-2));

			if(me.isequal(a2,VVariables.se))
				a2 = VVariables.saa; //ê£

			s.push(me.addarray(a1,Taggs.TagPastTenseMarker));
			s.push(a2);
		}
		else if(me.endswith(s1,VVariables.th))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));

			s.push(me.addarray(r,Taggs.TagPastTenseMarker));
			s.push(a);

			return s;
		}
		else if(me.endswith(s1,VVariables.nn))
		{
			byte a1[]=me.subarray(s1,s1.length-1,s1.length);
			byte a2[]=me.subarray(s1,0,(s1.length-2));

			a2 = me.addarray(a2,VVariables.l);

			s.push(me.addarray(a1,Taggs.TagPastTenseMarker));
			s.push(a2);
		}
		else if(me.endswith(s1,VVariables.n))
		{
			byte a1[]=me.subarray(s1,s1.length-1,s1.length);
			byte a2[]=me.subarray(s1,0,(s1.length-1));

			s.push(me.addarray(a1,Taggs.TagPastTenseMarker));
			s.push(a2);
		}
		else if(me.endswith(s1,VVariables.t))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));

			if(me.isequal(a,VVariables.kan))
			{
				a = VVariables.kaan;//tm.convert("è£í¢");
				s.push(me.addarray(r,Taggs.TagPastTenseMarker));
				s.push(a);
			}
			if(me.endswith(a,VVariables.n1))
			{
				a = me.subarray(a,0,a.length-1);
				a = me.addarray(a,VVariables.l1);
			}
			if(me.endswith(s1,VVariables.tt))
			{
				if(me.startswith(s1,tm.convert("«è")))
				{
					a = me.subarray(a,0,a.length-1);
					a = me.addarray(a,VVariables.l1);
				}
				else
					a = me.addarray(a,VVariables.u);
			}
			s.push(me.addarray(r,Taggs.TagPastTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.pp))
		{
			byte a1[]=me.subarray(s1,s1.length-2,s1.length);
			byte a2[]=me.subarray(s1,0,(s1.length-2));

			s.push(me.addarray(a1,Taggs.TagFutureTenseMarker));
			s.push(a2);
		}
		else if(me.endswith(s1,VVariables.iv))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));

			s.push(me.addarray(r,Taggs.TagFutureTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.p))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));

			if(me.isequal(a,VVariables.ket))//tm.convert("«èì¢")))
				a = VVariables.kel;//tm.convert("«è÷¢");
			if(me.isequal(a,VVariables.kar1))
				a = VVariables.kalsmall;//tm.convert("èô¢");
			if(me.endswith(a,VVariables.r1))
			{
				a = me.subarray(a,0,a.length-1);
				a = me.addarray(a,VVariables.l);
			}
			s.push(me.addarray(r,Taggs.TagFutureTenseMarker));
			s.push(a);
		}


		//conversion is completed upto this line 9/4/2 11/15/50

		else if(me.endswith(s1,VVariables.kkir))
		{
			byte r[]=me.subarray(s1,s1.length-4,s1.length);
			byte r2[]=me.addarray(r,VVariables.u);
			byte a[]=me.subarray(s1,0,(s1.length-4));

			if(me.endswith(a,VVariables.t))
				a = me.addarray(a,VVariables.u);

			s.push(me.addarray(r2,Taggs.TagPresentTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.kkindr))
		{
	 		byte r[]=me.subarray(s1,s1.length-5,s1.length);
			byte r2[]=me.addarray(r,VVariables.u);
			byte a[]=me.subarray(s1,0,(s1.length-5));

			s.push(me.addarray(r2,Taggs.TagPresentTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.kir))
		{
			byte r[]=me.subarray(s1,s1.length-3,s1.length);
			byte r2[]=me.addarray(r,VVariables.u);
			byte a[]=me.subarray(s1,0,(s1.length-3));

			if(me.endswith(a,VVariables.r1))
			{
				a = me.subarray(a,0,a.length-1);
				a = me.addarray(a,VVariables.l);
			}
			if(me.isequal(a,VVariables.ket))//tm.convert("«èì¢")))
				a = VVariables.kel;//tm.convert("«è÷¢");
			if(me.endswith(a,VVariables.t))
				a = me.addarray(a,VVariables.u);
			s.push(me.addarray(r2,Taggs.TagPresentTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.kindr))
		{
			byte r[]=me.subarray(s1,s1.length-4,s1.length);
			byte r2[]=me.addarray(r,VVariables.u);
			byte a[]=me.subarray(s1,0,(s1.length-4));

			if(me.isequal(a,VVariables.ket))//,tm.convert("«èì¢")))
				a = VVariables.kel;//tm.convert("«è÷¢");
			if(me.endswith(a,VVariables.r1))
			{
				a = me.subarray(a,0,a.length-1);
				a = me.addarray(a,VVariables.l);
			}
			s.push(me.addarray(r2,Taggs.TagPresentTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.rr1))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));
			byte a1[]=me.subarray(s1,0,(s1.length-2));

			if(me.isequal(a1,VVariables.ka) || me.isequal(a1,VVariables.vi))
				a = me.addarray(a1,VVariables.l);
			else
				a = me.addarray(a,VVariables.u);
			s.push(me.addarray(r,Taggs.TagPastTenseMarker));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.r1))
		{
			byte r[]=me.subarray(s1,s1.length-1,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-1));
			if(me.endswith(a,VVariables.n))
			{
				byte r2[] = me.subarray(a,0,a.length-1);
				a = me.addarray(r2,VVariables.l);
			}
			s.push(me.addarray(r,Taggs.TagPastTenseMarker));
			s.push(a);
		}
		else
			s.push(s1);
		return s;
	}
	Stack removeFutureNeuterSuffix(Stack s)
	{
		byte[] givenWord = (byte[])s.pop();

		byte[] removedSuffix;
		byte[] remainingString;
		if(me.endswith(givenWord,VVariables.kk))
		{
			removedSuffix = me.subarray(givenWord,givenWord.length-2,givenWord.length);
			remainingString = me.subarray(givenWord,0,givenWord.length-2);
			s.push(me.addarray(removedSuffix,Taggs.TagFutureTenseMarker));
			s.push(remainingString);
		}
		else if(me.endswith(givenWord,VVariables.ll)||me.endswith(givenWord,VVariables.l1l1)||me.endswith(givenWord,VVariables.yy)||me.endswith(givenWord,VVariables.n1n1))
		{
			removedSuffix = me.subarray(givenWord,givenWord.length-1,givenWord.length);
			remainingString = me.subarray(givenWord,0,givenWord.length-1);

			s.push(me.addarray(removedSuffix,Taggs.TagDoublingConsonant));
			s.push(remainingString);
		}
		else if(me.endswith(givenWord,VVariables.r1k))
		{
			removedSuffix = me.subarray(givenWord,givenWord.length-2,givenWord.length);
			remainingString = me.subarray(givenWord,0,givenWord.length-2);

			s.push(me.addarray(removedSuffix,Taggs.TagFutureTenseMarker));
			s.push(me.addarray(remainingString,VVariables.l));
		}
		else if(me.endswith(givenWord,VVariables.zh)||me.endswith(givenWord,VVariables.k) ||me.endswith(givenWord,VVariables.t)||me.endswith(givenWord,VVariables.r1))
		{
			s.push(me.addarray(givenWord,VVariables.u));
		}
		else if(me.endswith(givenWord,VVariables.ar))
		{
			removedSuffix = me.subarray(givenWord,givenWord.length-2,givenWord.length);
			remainingString = me.subarray(givenWord,0,givenWord.length-2);

			s.push(me.addarray(removedSuffix,Taggs.TagFutureTenseMarker));
			s.push(me.addarray(remainingString,VVariables.aa));
		}
		return s;
	}
}