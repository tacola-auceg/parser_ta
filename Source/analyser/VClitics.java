
package analyser;

import java.util.Stack;
public class VClitics
{
	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();

	public Stack removeUm(Stack s)
	{

		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.um.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.um.length);

		s.push(me.addarray(removedSuffix,Taggs.TagCliticum));

		if((me.endswith(remainingString,VVariables.y)&&!me.endswith(remainingString,VVariables.yy))||me.endswith(remainingString,VVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,VVariables.rr1))
			remainingString = me.addarray(remainingString,VVariables.u);

		s.push(remainingString);

		return s;
	}
	public Stack removeThaan(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.thaan.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.thaan.length);

		s.push(me.addarray(removedSuffix,Taggs.TagClitic));

		if(me.endswith(remainingString,VVariables.th)||me.endswith(remainingString,VVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);

		s.push(remainingString);

		return s;
	}
	public Stack removeAdaaAdi(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.adi.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.adi.length);

		s.push(me.addarray(removedSuffix,Taggs.TagClitic));

		if(me.endswith(remainingString,VVariables.y))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);

		s.push(remainingString);

		return s;
	}

	public Stack removeAe(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.ae.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.ae.length);

		s.push(me.addarray(removedSuffix,Taggs.TagClitic));

		if(me.endswith(remainingString,VVariables.y)||me.endswith(remainingString,VVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,VVariables.r1)||me.endswith(remainingString,VVariables.k))
			remainingString = me.addarray(remainingString,VVariables.u);

		s.push(remainingString);

		return s;
	}
	public Stack removeKooda(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.kooda.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.kooda.length);

		s.push(me.addarray(removedSuffix,Taggs.TagClitic));

		if(me.endswith(remainingString,VVariables.k))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);

		s.push(remainingString);

		return s;
	}

	public Stack removeAal(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.aal.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.aal.length);

		s.push(me.addarray(removedSuffix,Taggs.TagConditionalSuffix));
		s.push(remainingString);

		return s;
	}

	public Stack removeAa(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.aa.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.aa.length);

		s.push(me.addarray(removedSuffix,Taggs.TagClitic));

		if(me.endswith(remainingString,VVariables.y)||me.endswith(remainingString,VVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,VVariables.r1))
			remainingString = me.addarray(remainingString,VVariables.u);

		s.push(remainingString);

		return s;
	}
	public Stack removeOo(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.oo.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.oo.length);

		s.push(me.addarray(removedSuffix,Taggs.TagClitic));

		if(me.endswith(remainingString,VVariables.y)||me.endswith(remainingString,VVariables.iv))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);
		if(me.endswith(remainingString,VVariables.r1))
			remainingString = me.addarray(remainingString,VVariables.u);

		s.push(remainingString);

		return s;
	}

	public Stack removeThal(Stack s)
	{
		byte toBeAnalyzed[] = (byte[])s.pop();
		byte removedSuffix[] = me.subarray(toBeAnalyzed,toBeAnalyzed.length-VVariables.thal.length,toBeAnalyzed.length);
		byte remainingString[]= me.subarray(toBeAnalyzed,0,toBeAnalyzed.length-VVariables.thal.length);

		s.push(me.addarray(removedSuffix,Taggs.TagThal));

		if(me.endswith(remainingString,VVariables.th))
			remainingString = me.subarray(remainingString,0,remainingString.length-1);

		s.push(remainingString);

		return s;
	}

}