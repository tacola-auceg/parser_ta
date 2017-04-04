package TREEUI;

public class Node
{
	String value="";// ;//= new String();
	Node parent = null;
	Node LMC = null;
	Node RSibling = null;

	public Node( String tag)
	{
		value = tag;
	}

	public String getValue()
	{
		return value;
	}

}