package TREEUI;

import java.util.*;


public class BuildTreeNodes
{
	public Node buildTreeNodes(ArrayList al_elements)
	{
		Node parent_node = new Node(al_elements.get(0).toString());

		Node current_parent = parent_node;
		Node temp_node = new Node(al_elements.get(2).toString());
		temp_node.parent = current_parent;
		current_parent.LMC = temp_node;
	    Node current_node = temp_node;


	    for( int position = 3; position < al_elements.size(); position++)
	    {
			String element = al_elements.get(position).toString();

			if(element.equals("(") )
			{
				Node new_node = new Node(al_elements.get(++position).toString());
				new_node.parent = current_node;
				current_node.LMC = new_node;
				current_parent = current_node;
				current_node = new_node;
			}
			else if(element.equals(")"))
			{
				current_node = current_parent;
				current_parent = current_parent.parent;
			}
			else
			{
				Node new_node = new Node(element);
				new_node.parent = current_parent;
				current_node.RSibling = new_node;
				current_node = new_node;
			}
		}

		return parent_node;

	}

	public void preorder(Node parent_node)
	{
		System.out.println("	:"+parent_node.value.length()+"  "+parent_node.value);

		Node temp_node = parent_node.LMC;
		if(temp_node == null)
			return;
		else
		{
			do
			{
				preorder(temp_node);
				temp_node = temp_node.RSibling;
			}
			while(temp_node != null);
	    }
	}

}
