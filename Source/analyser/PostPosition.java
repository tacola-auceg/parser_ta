package analyser;
import java.util.Stack;

public class PostPosition
{
	ByteMeth bm = new ByteMeth();
	TabMethods tm = new TabMethods();

	public boolean checkPostPositions(Stack s)
	{
		byte[][] pospos = {VVariables.moolam,VVariables.varai,VVariables.illaamal,VVariables.vasam,VVariables.thorum,VVariables.maththiyil,VVariables.pakkam,VVariables.mel,VVariables.kizh,VVariables.muthal,VVariables.vazhiyaaka,VVariables.parththu,VVariables.nokki,VVariables.sur1r1i,VVariables.thaandi,VVariables.thavira,VVariables.otti,VVariables.appaal,VVariables.appuram,VVariables.ul,VVariables.ullae,VVariables.pin,VVariables.pinnae,VVariables.piraku,VVariables.mun,VVariables.vel1iyae,VVariables.itaiyae,VVariables.vittu,VVariables.pola,VVariables.vita,VVariables.munnittu,VVariables.poruththavarai,VVariables.mattum,VVariables.maathiram,VVariables.naduvae,VVariables.arukil,VVariables.pathil,VVariables.uriya,VVariables.thakuntha,VVariables.maar1aaka,VVariables.neeraaka,VVariables.kurukkae,VVariables.patr1i,VVariables.kuriththu/*,VVariables.kondu,VVariables.kondu*/};

		byte[] givenBytes = (byte[])s.peek();
		boolean isHaving = false;

		for(int i=0;i<pospos.length;i++)
			if(bm.endswith(givenBytes,pospos[i]))
			{
				remove_PosPos(s,pospos[i]);
				isHaving = true;
				break;
			}
		return isHaving;
	}
	public void remove_PosPos(Stack s,byte[] toremove)
	{
		byte givenString[] = (byte[])s.pop();
		byte removedString[]=bm.subarray(givenString,givenString.length-toremove.length,givenString.length);
		byte remainingString[]=bm.subarray(givenString,0,(givenString.length-toremove.length));

		if(bm.endswith(remainingString,VVariables.k)||bm.endswith(remainingString,NVariables.ch)||bm.endswith(remainingString,VVariables.th)||bm.endswith(remainingString,VVariables.p))
			remainingString = bm.subarray(remainingString,0,remainingString.length-2);
		if(bm.endswith(remainingString,VVariables.y))
			remainingString = bm.subarray(remainingString,0,remainingString.length-1);
		s.push(bm.addarray(removedString,Taggs.TagPosPos));
		s.push(remainingString);
	}
}