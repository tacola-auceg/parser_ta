package analyser;
import java.util.*;

public class Participles
{
	ByteMeth bm = new ByteMeth();
	TabMethods tm = new TabMethods();

	public boolean isEndswithTemporalAdverbialParticiple(byte[] b)
	{
		byte[][] taps = {VVariables.poothu,VVariables.pozhuthu,VVariables.mun,VVariables.munpu,VVariables.munnaal,VVariables.pin,VVariables.pinnaal,VVariables.pinpu,VVariables.piraku,VVariables.utan,VVariables.utane};

		for(int i=0;i<taps.length;i++)
			if(bm.endswith(b,bm.addarray(VVariables.a,taps[i]))||bm.endswith(b,bm.addarray(VVariables.um,taps[i])))
				return true;
		return false;
	}
	public Stack removeTemporalAdverbialParticipls(Stack s)
	{
		byte[] givenString = (byte[])s.pop();

		byte[][] taps = {VVariables.poothu,VVariables.pozhuthu,VVariables.mun,VVariables.munpu,VVariables.munnaal,VVariables.pin,VVariables.pinnaal,VVariables.pinpu,VVariables.piraku,VVariables.utan,VVariables.utane};

		for(int i=0;i<taps.length;i++)
		{
			if(bm.endswith(givenString,taps[i]))
			{
				s.push(bm.addarray(taps[i],tm.convert(" < Temporal Adverbial Participle > ")));
				s.push(bm.subarray(givenString,0,givenString.length-taps[i].length));
			}
		}
		return s;
	}

	public boolean isEndswithManneralAdverbialParticiple(byte[] b)
	{
		byte[][] maps = {VVariables.vaNNam,VVariables.vaaRu,VVariables.padi};

		for(int i=0;i<maps.length;i++)
			if(bm.endswith(b,bm.addarray(VVariables.a,maps[i]))||bm.endswith(b,bm.addarray(VVariables.um,maps[i])))
				return true;
		return false;
	}
	public Stack removeManneralAdverbialParticipls(Stack s)
	{
		byte[] givenString = (byte[])s.pop();

		byte[][] maps = {VVariables.vaNNam,VVariables.vaaRu,VVariables.padi};

		for(int i=0;i<maps.length;i++)
		{
			if(bm.endswith(givenString,maps[i]))
			{
				s.push(bm.addarray(maps[i],tm.convert(" < Manneral Adverbial Participle > ")));
				s.push(bm.subarray(givenString,0,givenString.length-maps[i].length));
			}
		}
		return s;
	}

	public boolean isEndswithLimitativeAdverbialParticiple(byte[] b)
	{
		byte[][] laps = {VVariables.varai,VVariables.varail,VVariables.mattum,VVariables.mattilum};

		for(int i=0;i<laps.length;i++)
			if(bm.endswith(b,bm.addarray(VVariables.a,laps[i]))||bm.endswith(b,bm.addarray(VVariables.um,laps[i])))
				return true;
		return false;
	}
	public Stack removeLimitativeAdverbialParticipls(Stack s)
	{
		byte[] givenString = (byte[])s.pop();

		byte[][] laps = {VVariables.varai,VVariables.varail,VVariables.mattum,VVariables.mattilum};

		for(int i=0;i<laps.length;i++)
		{
			if(bm.endswith(givenString,laps[i]))
			{
				s.push(bm.addarray(laps[i],tm.convert(" < Limitative Adverbial Participle > ")));
				s.push(bm.subarray(givenString,0,givenString.length-laps[i].length));
			}
		}
		return s;
	}


}
