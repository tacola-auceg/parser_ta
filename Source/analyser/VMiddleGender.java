package analyser;

import java.util.Stack;

public class VMiddleGender
{
	public Stack checkMiddleGender(Stack s)
	{
		TabMethods tm = new TabMethods();
		ByteMeth me = new ByteMeth();



		byte[] topElement = (byte[])s.pop();

	/*if(me.endswith(topElement,VVariables.avan) || (me.endswith(topElement,VVariables.aval)
			|| (me.endswith(topElement,VVariables.adhu)) || (me.endswith(topElement,VVariables.avar)||(me.endswith(topElement,VVariables.avargal))*/
		if(me.endswith(topElement,VVariables.avarkal))
		{
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.avarkal.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.avarkal.length);

			s.push(me.addarray(remove,tm.convert(" < avargal - noun > ")));
			if(remain.length==0)
				return s;
			s.push(remain);
		}

		else if(me.endswith(topElement,VVariables.avan))
		{
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.avan.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.avan.length);

			s.push(me.addarray(remove,tm.convert(" < avan - noun > ")));
			if(remain.length==0)
				return s;
			s.push(remain);

		}
		else if(me.endswith(topElement,VVariables.aval1))
		{
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.aval1.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.aval1.length);

			s.push(me.addarray(remove,tm.convert(" < aval - noun > ")));
			if(remain.length==0)
				return s;
			s.push(remain);
		}
		else if(me.endswith(topElement,VVariables.avar))
		{
			byte[] remove = me.subarray(topElement,topElement.length-VVariables.avar.length,topElement.length);
			byte[] remain = me.subarray(topElement,0,topElement.length-VVariables.avar.length);

			s.push(me.addarray(remove,tm.convert(" < avar - noun > ")));
			if(remain.length==0)
				return s;
			s.push(remain);
		}

		return s;
	}
}