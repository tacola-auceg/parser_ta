package analyser;
// infinitive and potentials

import java.util.Stack;

public class VCheckInf
{
	ByteMeth bm = new ByteMeth();
	TabMethods tm = new TabMethods();

	VSearch search = new VSearch();

	public Stack removeInf(Stack s)
	{
		byte[] topElement = (byte[])s.pop();


		if(search.dicSearch(tm.revert(topElement),"./src/cat7.txt"))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-1,topElement.length);
			byte[] yetToRemove = bm.addarray(bm.subarray(topElement,0,topElement.length-1),VVariables.u);
			s.push(bm.addarray(removed,Taggs.TagInfinitive));
			s.push(yetToRemove);
			return s;
		}

		if(bm.endswith(topElement,VVariables.kka))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-3,topElement.length);
			byte[] yetToRemove = bm.subarray(topElement,0,topElement.length-3);
			s.push(bm.addarray(removed,Taggs.TagInfinitive));
			s.push(yetToRemove);
		}
		else if(bm.endswith(topElement,VVariables.ka))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-2,topElement.length);
			byte[] yetToRemove = bm.subarray(topElement,0,topElement.length-2);
			s.push(bm.addarray(removed,Taggs.TagInfinitive));
			if(bm.endswith(yetToRemove,VVariables.t))// ketka --->  kel
				yetToRemove = bm.addarray(bm.subarray(yetToRemove,0,yetToRemove.length-1),VVariables.l1);
			if(bm.endswith(yetToRemove,VVariables.r1))
				yetToRemove = bm.addarray(bm.subarray(yetToRemove,0,yetToRemove.length-1),VVariables.l);
			s.push(yetToRemove);
		}
		else if(bm.endswith(topElement,VVariables.a))
		{
			byte[] removed = bm.subarray(topElement,topElement.length-1,topElement.length);
			byte[] yetToRemove = bm.subarray(topElement,0,topElement.length-1);

/**
  * If the word is endswith y then remove the y
  *
  */
			if(bm.endswith(yetToRemove,VVariables.y)||bm.endswith(yetToRemove,VVariables.l1l1))
				yetToRemove = bm.subarray(yetToRemove,0,yetToRemove.length-1);
/**
  * If the word ends with zh and not in "zh.txt" file OR  \\ vazhu,  makizha
  * If the word ends with t   \\aattu
  * Then add u at  the end \\ peru
  */

			else if((bm.endswith(yetToRemove,VVariables.zh) && !search.dicSearch(tm.revert(yetToRemove),"./src/zh.txt")) || bm.endswith(yetToRemove,VVariables.t) || bm.endswith(yetToRemove,VVariables.r1))
				yetToRemove = bm.addarray(yetToRemove,VVariables.u);
/**
  * If the word is ends with NN  OR If the word ends with ll then remove the N
  */
  			else if(bm.endswith(yetToRemove,VVariables.n1) || bm.endswith(yetToRemove,VVariables.ll))
				yetToRemove = bm.subarray(yetToRemove,0,yetToRemove.length-1);
/**
  *  If the word ends with thar and var take the first byte and add one A
  */		else if(bm.isequal(yetToRemove,VVariables.var) || bm.isequal(yetToRemove,VVariables.thar))
				yetToRemove = bm.addarray(bm.subarray(yetToRemove,0,1),VVariables.aa);

			s.push(bm.addarray(removed,Taggs.TagInfinitive));
			s.push(yetToRemove);
		}
		else
		{
				s.push(topElement);
		}
		return s;
	}


	//  potentials
	public Stack removePotentials(Stack s)
	{
		byte[] with_alaam = (byte[])s.pop();

		if(bm.endswith(with_alaam,VVariables.kkalaam))
		{
			byte[] without_alaam = bm.subarray(with_alaam,0,with_alaam.length-VVariables.kkalaam.length);
			s.push(bm.addarray(VVariables.kkalaam,Taggs.TagPotentialSuffix));
			s.push(without_alaam);
		}
		else if(bm.endswith(with_alaam,VVariables.kalaam))
		{
			byte[] without_alaam = bm.subarray(with_alaam,0,with_alaam.length-VVariables.kalaam.length);
			s.push(bm.addarray(VVariables.kalaam,Taggs.TagPotentialSuffix));
			s.push(without_alaam);
		}
		else if(bm.endswith(with_alaam,VVariables.alaam))
		{
			byte[] without_alaam = bm.subarray(with_alaam,0,with_alaam.length-VVariables.alaam.length);
			s.push(bm.addarray(VVariables.alaam,Taggs.TagPotentialSuffix));
			if(without_alaam[without_alaam.length-1] == without_alaam[without_alaam.length-2])
				without_alaam = bm.subarray(without_alaam,0,without_alaam.length-1);
			s.push(without_alaam);
		}
		return s;
	}

}