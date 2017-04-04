package analyser;

import java.util.*;

public class VVerbalParticiple
{
	TabMethods tm = new TabMethods();
	ByteMeth me = new ByteMeth();

	public Stack checkVbp(Stack s,byte[] mainVerb)
	{
		if(me.endswith(mainVerb,VVariables.y))
		{
			byte verbPart[]=me.subarray(mainVerb,mainVerb.length-1,mainVerb.length);
			mainVerb=me.subarray(mainVerb,0,mainVerb.length-1);

			if(me.endswith(mainVerb,VVariables.e))
			{
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
				if(me.endswith(mainVerb,VVariables.ll))
					mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
				else
					mainVerb = me.addarray(mainVerb,VVariables.u);
			}

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			s.push(mainVerb);
			return s;
		}
		else if(me.endswith(mainVerb,VVariables.e))
		{
			byte verbPart[] = me.subarray(mainVerb,mainVerb.length-1,mainVerb.length);

			mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);

			if((mainVerb.length > VVariables.ll.length)&&me.endswith(mainVerb,VVariables.ll))
				mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);
			else
				mainVerb = me.addarray(mainVerb,VVariables.u);

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			s.push(mainVerb);
			return s;
		}
		else if((me.endswith(mainVerb,VVariables.ththu)||me.endswith(mainVerb,VVariables.nththu)))
		{
			byte verbPart[]=me.subarray(mainVerb,mainVerb.length-3,mainVerb.length);

			mainVerb = me.subarray(mainVerb,0,mainVerb.length-3);

			if(me.endswith(mainVerb,VVariables.va) || me.endswith(mainVerb,VVariables.tha))
				mainVerb[mainVerb.length-1] = 2;

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			s.push(mainVerb);
			return s;
		}
		else if((me.endswith(mainVerb,VVariables.thth)||me.endswith(mainVerb,VVariables.nthth)))
		{
			byte verbPart[]=me.subarray(mainVerb,mainVerb.length-2,mainVerb.length);

			verbPart = me.addarray(verbPart,VVariables.u);
			mainVerb = me.subarray(mainVerb,0,mainVerb.length-2);


 			if(me.endswith(mainVerb,VVariables.va) || me.endswith(mainVerb,VVariables.tha))
				mainVerb[mainVerb.length-1] = 2;

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			s.push(mainVerb);
			return s;
		}
		else if(me.endswith(mainVerb,VVariables.thu))
		{
			byte verbPart[]=me.subarray(mainVerb,mainVerb.length-VVariables.thu.length,mainVerb.length);

			mainVerb = me.subarray(mainVerb,0,mainVerb.length-2);

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			s.push(mainVerb);
			return s;
		}
		else if(me.endswith(mainVerb,VVariables.th))
		{
			byte verbPart[]=me.subarray(mainVerb,mainVerb.length-1,mainVerb.length);

			verbPart = me.addarray(verbPart,VVariables.u);
			mainVerb = me.subarray(mainVerb,0,mainVerb.length-1);

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			s.push(mainVerb);
			return s;
		}
			//anand
		else if(me.endswith(mainVerb,VVariables.kondu) ||me.endswith(mainVerb,VVariables.kond))
		{
			if(me.endswith(mainVerb,VVariables.kond))
				mainVerb = me.addarray(mainVerb,VVariables.u);

			byte verbPart[] = me.subarray(mainVerb,mainVerb.length-3,mainVerb.length);

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			mainVerb = me.addarray(me.subarray(mainVerb,0,mainVerb.length-3),VVariables.l1);

			s.push(mainVerb);
			return s;
		}
		else if(me.endswith(mainVerb,VVariables.nrru))
		{
			if(me.endswith(mainVerb,VVariables.nrr))
				mainVerb = me.addarray(mainVerb,VVariables.u);

			byte verbPart[] = me.subarray(mainVerb,mainVerb.length-3,mainVerb.length);

			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			mainVerb = me.addarray(me.subarray(mainVerb,0,mainVerb.length-3),VVariables.l);
			s.push(mainVerb);
			return s;
		}
		else if(me.endswith(mainVerb,VVariables.ttu))
		{
			byte verbPart[] = me.subarray(mainVerb,mainVerb.length-2,mainVerb.length);
			mainVerb = me.addarray(me.subarray(mainVerb,0,mainVerb.length-2),VVariables.u);
			s.push(me.addarray(verbPart,Taggs.TagVerbalParticiple));
			s.push(mainVerb);
			return s;
		}
		else
		{
			s.push(mainVerb);
			return s;
		}
	}
}