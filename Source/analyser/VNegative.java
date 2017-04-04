package analyser;

import java.util.*;

public class VNegative
{
	TabMethods tm =new TabMethods();
	ByteMeth bm = new ByteMeth();

	public Stack removeAathey(Stack s)
	{
		byte[] fullBytes = (byte[])s.pop();
		byte[] remainingBytes = bm.addarray(bm.subarray(fullBytes,0,fullBytes.length-VVariables.aathey.length),VVariables.a);

		s.push(bm.addarray(VVariables.aathey,tm.convert(" < 2nd per. neg. sing. > ")));
		s.push(remainingBytes);
		return s;
	}

	public Stack removeAatheergal(Stack s)
	{
		byte[] fullBytes = (byte[])s.pop();
		byte[] remainingBytes = bm.addarray(bm.subarray(fullBytes,0,fullBytes.length-VVariables.aatheergal.length),VVariables.a);

		s.push(bm.addarray(VVariables.aatheergal,tm.convert(" < 2nd per. neg. plur. > ")));
		s.push(remainingBytes);
		return s;
	}
	public Stack removeAamal(Stack s)
	{
		byte[] fullBytes = (byte[])s.pop();
		byte[] remainingBytes = bm.addarray(bm.subarray(fullBytes,0,fullBytes.length-VVariables.aamal.length),VVariables.a);

		s.push(bm.addarray(VVariables.aamal,tm.convert(" < aamal - neg. com. > ")));
		s.push(remainingBytes);
		return s;
	}
	public Stack removeAathu(Stack s)
	{
		byte[] fullBytes = (byte[])s.pop();
		byte[] remainingBytes = bm.addarray(bm.subarray(fullBytes,0,fullBytes.length-VVariables.aathu.length),VVariables.a);

		s.push(bm.addarray(VVariables.aathu,tm.convert(" < aathu - neg. > ")));
		s.push(remainingBytes);
		return s;
	}
	public Stack removeAavittal(Stack s)
	{
		byte[] fullBytes = (byte[])s.pop();
		byte[] remainingBytes = bm.addarray(bm.subarray(fullBytes,0,fullBytes.length-VVariables.aavittal.length),VVariables.a);

		s.push(bm.addarray(VVariables.aavittal,tm.convert(" < aavitaal - neg.  > ")));
		s.push(remainingBytes);
		return s;
	}
	public Stack removeUngal(Stack s)
	{
		byte[] fullBytes = (byte[])s.pop();
		byte[] remainingBytes = bm.subarray(fullBytes,0,fullBytes.length-VVariables.ungal.length);

		s.push(bm.addarray(VVariables.ungal,tm.convert(" < Ungal >")));

		if(bm.endswith(remainingBytes,VVariables.y)|| bm.endswith(remainingBytes,VVariables.n1)||bm.endswith(remainingBytes,VVariables.l))
			remainingBytes = bm.subarray(remainingBytes,0,remainingBytes.length-1);
		if(bm.endswith(remainingBytes,VVariables.zh)||bm.endswith(remainingBytes,VVariables.k) || bm.endswith(remainingBytes,VVariables.t)||bm.endswith(remainingBytes,VVariables.r1))
			remainingBytes = bm.addarray(remainingBytes,VVariables.u);

		s.push(remainingBytes);
		return s;
	}
}