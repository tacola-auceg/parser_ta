package analyser;
import java.util.*;

public class Cases
{
	TabMethods tm = new TabMethods();
	ByteMeth me = new ByteMeth();

	public Stack check_ukkaaga(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-NVariables.ukkaaga.length,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.ukkaaga.length));

		s.push(me.addarray(removedString,Taggs.TagBenCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_ukkaana(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-NVariables.ukkaana.length,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.ukkaana.length));

		s.push(me.addarray(removedString,Taggs.TagBenCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_ilirunthu(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-NVariables.ilirunthu.length,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.ilirunthu.length));

		s.push(me.addarray(removedString,Taggs.TagAblCase));
		if(me.endswith(remainingString,NVariables.y))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,VVariables.pp)||me.endswith(remainingString,VVariables.th))
			remainingString = me.addarray(remainingString,NVariables.u);
		s.push(remainingString);
		return s;
	}

	public Stack check_itamirunthu(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-NVariables.itamirunthu.length,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.itamirunthu.length));

		if(me.endswith(remainingString,NVariables.y))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		s.push(me.addarray(removedString,Taggs.TagAblCase));
		s.push(remainingString);
		return s;
	}

	public Stack checkitam(Stack s)
	{
		byte givenString[] = (byte[]) s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-4,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.itam.length));

		s.push(me.addarray(removedString,Taggs.TagLocCase));
		s.push(remainingString);
		return s;
	}

	public Stack checkin(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-2,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.il.length));

		s.push(me.addarray(removedString,Taggs.TagGenCase));

		if(me.endswith(remainingString,NVariables.y))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,NVariables.pp)||me.endswith(remainingString,VVariables.nthth)||me.endswith(remainingString,NVariables.iv))
			remainingString = me.addarray(remainingString,NVariables.u);
		s.push(remainingString);
		return s;
	}

	public  Stack check_ai(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-1,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.ai.length));

		s.push(me.addarray(removedString,Taggs.TagAccCase));

		if(me.endswith(remainingString,NVariables.nn) || me.endswith(remainingString,NVariables.iv)|| me.endswith(remainingString,NVariables.nn1)|| me.endswith(remainingString,NVariables.y))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,NVariables.ch)||me.endswith(remainingString,NVariables.ch)||me.endswith(remainingString,NVariables.pp)||me.endswith(remainingString,NVariables.rr1)||( me.endswith(remainingString,NVariables.th)&& !me.endswith(remainingString,NVariables.thth)))
			remainingString = me.addarray(remainingString,NVariables.u);

		s.push(remainingString);

		return s;
	}

	public  Stack checkil(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-2,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.il.length));

		s.push(me.addarray(removedString,Taggs.TagLocCase));

		if(!me.endswith(remainingString,NVariables.thth) && me.endswith(remainingString,NVariables.th)||me.endswith(remainingString,NVariables.k)||me.endswith(remainingString,NVariables.t))
			remainingString = me.addarray(remainingString,NVariables.u);
		else if(me.endswith(remainingString,NVariables.y) || me.endswith(remainingString,NVariables.n1)|| me.endswith(remainingString,NVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		else if(me.endswith(remainingString,NVariables.rr1))
		{
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
			remainingString = me.addarray(remainingString,NVariables.u);
		}

		s.push(remainingString);

		return s;
	}

	public Stack check_utan(Stack s)
	{
		byte givenString[] = (byte[]) s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-4,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.utan.length));

		if(me.endswith(remainingString,NVariables.nn)||me.endswith(remainingString,NVariables.y)||me.endswith(remainingString,NVariables.iv)||me.endswith(remainingString,NVariables.nn1))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,NVariables.p))
			remainingString = me.addarray(remainingString,NVariables.u);
		s.push(me.addarray(removedString,Taggs.TagSocCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_athu(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-3,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,givenString.length-NVariables.athu.length);

		if(me.endswith(remainingString,NVariables.iv)||me.endswith(remainingString,NVariables.nn))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		s.push(me.addarray(removedString,Taggs.TagGenCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_odu(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-3,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.odu.length));

		if(me.endswith(remainingString,NVariables.y)||me.endswith(remainingString,NVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);

		s.push(me.addarray(removedString,Taggs.TagSocCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_kkaaga(Stack s)
	{
		byte givenString[] = (byte[]) s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-NVariables.kkaaga.length,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.kkaaga.length));

		s.push(me.addarray(removedString,Taggs.TagBenCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_udaya(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-5,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.udaya.length));

		if(me.endswith(remainingString,NVariables.nn))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);

		s.push(me.addarray(removedString,Taggs.TagGenCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_akku(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-NVariables.akku.length,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.akku.length));

		s.push(me.addarray(removedString,Taggs.TagDatCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_akka(Stack s)
	{
		byte givenString[] = (byte[]) s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-3,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.akka.length));

		if(me.endswith(remainingString,NVariables.y)||me.endswith(remainingString,NVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		else if(me.endswith(remainingString,NVariables.iv) ||me.endswith(remainingString,NVariables.k)|| me.endswith(remainingString,NVariables.t)|| me.endswith(remainingString,NVariables.th))
			remainingString = me.addarray(remainingString,NVariables.u);
		s.push(me.addarray(removedString,Taggs.TagAdverbialSuffix));
		s.push(remainingString);
		return s;
	}
	public Stack check_aana(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-NVariables.aana.length,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.aana.length));

		if(me.endswith(remainingString,NVariables.y))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		else if(me.endswith(remainingString,NVariables.iv) ||me.endswith(remainingString,NVariables.k)|| me.endswith(remainingString,NVariables.t)|| me.endswith(remainingString,NVariables.th))
			remainingString = me.addarray(remainingString,NVariables.u);
		s.push(me.addarray(removedString,Taggs.TagAdjectivalSuffix));
		s.push(remainingString);
		return s;
	}

	public Stack check_kku(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-3,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.kku.length));

		s.push(me.addarray(removedString,Taggs.TagDatCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_ukku(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-4,givenString.length);
		byte remainingString[]=me.subarray(givenString,0,(givenString.length-NVariables.ukku.length));

		if(me.endswith(remainingString,NVariables.p)||me.endswith(remainingString,NVariables.ch)||me.endswith(remainingString,NVariables.th))
			remainingString = me.addarray(remainingString,NVariables.u);
		if(me.endswith(remainingString,NVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		s.push(me.addarray(removedString,Taggs.TagDatCase));
		s.push(remainingString);
		return s;
	}

	public Stack check_irku(Stack s)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=me.subarray(givenString,givenString.length-4,givenString.length);
		byte a[]=me.subarray(givenString,0,(givenString.length-NVariables.irku.length));

		s.push(me.addarray(removedString,Taggs.TagDatCase));
		s.push(a);
		return s;
	}

	public Stack remove_aal(Stack s)
	{
		byte[] givenString = (byte[]) s.pop();
		byte removedString[] = me.subarray(givenString,givenString.length-VVariables.aal.length,givenString.length);
		byte rem[] = me.subarray(givenString,0,givenString.length-VVariables.aal.length);

		s.push(me.addarray(removedString,Taggs.TagInsCase));
		if(me.endswith(rem,NVariables.y))
			rem = me.subarray(rem,0,rem.length-1);
		else if(me.endswith(rem,NVariables.k))
			rem = me.addarray(rem,NVariables.u);
		s.push(rem);
		return s;
	}
	public Stack remove_arru(Stack s)
	{
		byte[] givenString = (byte[]) s.pop();
		byte removedString[] = me.subarray(givenString,givenString.length-NVariables.arru.length,givenString.length);
		byte rem[] = me.subarray(givenString,0,givenString.length-NVariables.arru.length);
		s.push(me.addarray(removedString,Taggs.TagAdverbialSuffix));
		s.push(rem);
		return s;
	}
	public Stack remove_arra(Stack s)
	{
		byte[] givenString = (byte[]) s.pop();
		byte removedString[] = me.subarray(givenString,givenString.length-NVariables.arra.length,givenString.length);
		byte rem[] = me.subarray(givenString,0,givenString.length-NVariables.arra.length);
		s.push(me.addarray(removedString,Taggs.TagAdjectivalSuffix));
		s.push(rem);
		return s;
	}


}
