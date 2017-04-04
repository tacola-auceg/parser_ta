package analyser;

import java.util.Stack;
public class VGender
{
	VVerbalParticiple vbp = new VVerbalParticiple();
	VTenses tense = new VTenses();
	VAuxillary aux = new VAuxillary();
	ByteMeth me = new ByteMeth();
	TabMethods tm = new TabMethods();

	public Stack checkaalaan(Stack s,byte [] s1)
	{


		if(me.endswith(s1,VVariables.aarkal)||me.endswith(s1,VVariables.eerkal))
		{
			byte r[]=me.subarray(s1,s1.length-5,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-5));

			if(me.endswith(s1,VVariables.aarkal))
				s.push(me.addarray(r,tm.convert(" < 3rd person plural > ")));
			else if(me.endswith(s1,VVariables.eerkal))
				s.push(me.addarray(r,tm.convert(" < 2rd person plural > ")));
			s.push(a);
		}

		else if(me.endswith(s1,VVariables.aan) || me.endswith(s1,VVariables.aal1)
			||me.endswith(s1,VVariables.aar)||me.endswith(s1,VVariables.um)
			||me.endswith(s1,VVariables.om)||me.endswith(s1,VVariables.ein)
			||me.endswith(s1,VVariables.aai)||me.endswith(s1,VVariables.eer)
			||me.endswith(s1,VVariables.ar))
		{
			byte r[]=me.subarray(s1,s1.length-2,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-2));
			if(me.endswith(s1,VVariables.aai))
				s.push(me.addarray(r,tm.convert(" < 2nd per. sing. > ")));
			else if(me.endswith(s1,VVariables.aal1))
				s.push(me.addarray(r,tm.convert(" < 3rd per. Fem. sing.>")));
			else if(me.endswith(s1,VVariables.aan))
				s.push(me.addarray(r,tm.convert(" < 3rd per. Mas. sing. > ")));
			else if(me.endswith(s1,VVariables.ein))
				s.push(me.addarray(r,tm.convert(" < 1st per. Sing. > ")));
			else if(me.endswith(s1,VVariables.om))
				s.push(me.addarray(r,tm.convert(" < 1st per. Plur. > ")));
			else if(me.endswith(s1,VVariables.um))
				s.push(me.addarray(r,tm.convert(" < Neuter Gender > ")));
			else if(me.endswith(s1,VVariables.aar))
				s.push(me.addarray(r,tm.convert(" < 3rd per. sing. Fem./Mas. >")));
			else if(me.endswith(s1,VVariables.ar))
				s.push(me.addarray(r,tm.convert(" < 3rd per. plur.  >")));
			else if(me.endswith(s1,VVariables.eer))
				s.push(me.addarray(r,tm.convert(" < 2rd per. sing. >")));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.ana)||me.endswith(s1,VVariables.adhu))
		{
			byte r[]=me.subarray(s1,s1.length-3,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-3));

			if(me.endswith(s1,VVariables.ana))
				s.push(me.addarray(r,tm.convert(" < Neuter plural > ")));
			else if(me.endswith(s1,VVariables.adhu))
				s.push(me.addarray(r,tm.convert(" < Neuter singular > ")));
			s.push(a);
		}
		else if(me.endswith(s1,VVariables.anar))
		{
			byte r[]=me.subarray(s1,s1.length-4,s1.length);
			byte a[]=me.subarray(s1,0,(s1.length-4));

			s.push(me.addarray(r,tm.convert(" < 3rd person plural > ")));
			s.push(a);
		}
		return s;
	}
}