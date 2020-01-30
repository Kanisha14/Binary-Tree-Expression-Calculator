import java.util.Vector;
import java.util.Iterator;

/**
   LinkedBinaryTree class implements the BinaryTree interface by means
   of a linked structure consisting of nodes.

   @author     Franck van Breugel
   @version    by Jeff Edmonds   March 28, 2015
   @see BTNode
 */
public class LinkedBinaryTree implements BinaryTree 
{
	private BTNode root;  // pointer to the root of this binary tree
	private int size;     // size of this binary tree

    /** Constructs a binary tree with an empty node. **/
    public LinkedBinaryTree() 
    {
        root = new BTNode();
        size = 1;
    }

	/**
       Constructs a binary tree consisting of a single node storing the
       specified element.

       @param element The element stored in the root.
	 */
	public LinkedBinaryTree(Object element) 
	{
		root = new BTNode(element, null, null, null);
		size = 1;
	}

	/**
       Constructs a binary tree consisting of a root which stores
       the specified element, which has the first specified binary tree as its
       left subtree and the second specified binary tree as its right subtree.

       @param element The element stored in the root.
       @param left The left subtree of the root.
       @param right The right subtree of the root.
	 */
	public LinkedBinaryTree(Object element, BinaryTree left, BinaryTree right) 
	{
		BTNode leftRoot = (BTNode) left.root();
		BTNode rightRoot = (BTNode) right.root();
		root = new BTNode(element, leftRoot, rightRoot, null);
		if(leftRoot!=null) leftRoot.setParent(root);    // Jeff Added the if.
		if(rightRoot!=null) rightRoot.setParent(root);
		size = left.size() + right.size() + 1;
	}

	public void setSize(int size) 						// Jeff Added this.
	{
		this.size = size;
		
	}

	public int size()  
	{
		return size;
	}

	public boolean isEmpty() 
	{
		return (size == 0);
	}

	public void setRoot(Position position) 				// Jeff Added this.
	{
		this.root = (BTNode) position;
	}

	public Position root()
	{
		return root;
	}

	public Position parent(Position position) throws InvalidPositionException 
	{
		if (isRoot(position)) 
		{
			throw new InvalidPositionException("Root has no parent.");
		}
		BTNode node = (BTNode) position;
		return node.getParent();
	}

	public Iterator children(Position position)
	{
		BTNode node = (BTNode) position;
		Vector vector = new Vector();
		if (isInternal(node)) 
		{
			vector.addElement(node.getLeft());
			vector.addElement(node.getRight());
		}
		return vector.iterator();
	}

	public boolean isInternal(Position position)
	{
		BTNode node = (BTNode) position;
		return (node.getLeft() != null || node.getRight() != null);
	}

	public boolean isExternal(Position position)
	{
		return (!isInternal(position));
	}

	public boolean isRoot(Position position)
	{
		BTNode node = (BTNode) position;
		return (node == root);
	}

	/**
       Adds the elements stored in the subtree rooted at the specified
       node to the specified vector. 
       A preorder traversal is used to visit the nodes of the subtree.

       @param node Root of the subtree the elements of which are added
       to the specified verctor.
       @param vector The vector to which the elements are added.
	 */
	private void preorderElements(BTNode node, Vector vector) 
	{
		if (node != null) 
		{
			vector.addElement(node.element());
			preorderElements(node.getLeft(), vector);
			preorderElements(node.getRight(), vector);
		}
	}

	public Iterator elements() 
	{
		Vector vector = new Vector();
		preorderElements(root, vector);
		return vector.iterator();
	}

	/**
       Adds the positions in the subtree rooted at the specified
       node to the specified vector. 
       A preorder traversal is used to visit the nodes of the subtree.

       @param node Root of the subtree the positions of which are added
       to the specified verctor.
       @param vector The vector to which the positions are added.
	 */
	private void preorderPositions(BTNode node, Vector vector) 
	{
		if (node != null) 
		{
			vector.addElement(node);
			preorderPositions(node.getLeft(), vector);
			preorderPositions(node.getRight(), vector);
		}
	}

	public Iterator positions() 
	{
		Vector vector = new Vector();
		preorderPositions(root, vector);
		return vector.iterator();
	}

	public void swapElements(Position first, Position second) 
	{
		BTNode firstNode = (BTNode) first;
		BTNode secondNode = (BTNode) second;
		Object firstElement = firstNode.element();
		Object secondElement = secondNode.element();
		firstNode.setElement(secondElement);
		secondNode.setElement(firstElement);
	}

	public Object replaceElement(Position position, Object element)
	{
		BTNode node = (BTNode) position;
		Object temp = node.element();
		node.setElement(element);
		return temp;
	}

	public Position leftChild(Position position) throws InvalidPositionException 
	{
		BTNode node = (BTNode) position;
		if (node.getLeft() == null) 
		{
			throw new InvalidPositionException("Node has no left child.");
		}
		return node.getLeft();
	}

	public Position rightChild(Position position) throws InvalidPositionException 
	{
		BTNode node = (BTNode) position;
		if (node.getRight() == null) 
		{
			throw new InvalidPositionException("Node has no right child.");
		}
		return node.getRight();
	}

	public Position sibling(Position position) throws InvalidPositionException
	{
		Position parent = parent(position);
		return (position == leftChild(parent)) ? rightChild(parent) : leftChild(parent);
	}

	public void expandExternal(Position position) throws InvalidPositionException 
	{
		if (isInternal(position)) 
		{
			throw new InvalidPositionException("Invalid position.");
		}
		BTNode node = (BTNode) position;
		BTNode leftChild = new BTNode(null, null, null, node);
		BTNode rightChild = new BTNode(null, null, null, node);
		node.setLeft(leftChild);
		node.setRight(rightChild);
		size += 2;
	}

	public Object removeAboveExternal(Position position) throws InvalidPositionException 
	{
		if (isInternal(position) || isRoot(position)) 
		{
			throw new InvalidPositionException("Invalid position.");
		}
		BTNode parent = (BTNode) parent(position);
		BTNode sibling = (BTNode) sibling(position);
		if (isRoot(parent)) 
		{
			root = sibling;
			sibling.setParent(null);
		} 
		else 
		{
			BTNode grandparent = (BTNode) parent(parent);
			if (grandparent.getLeft() == parent) 
			{
				grandparent.setLeft(sibling);
			} 
			else 
			{
				grandparent.setRight(sibling);
			}
			sibling.setParent(grandparent);
		}
		size -= 2;
		return parent.element();
	}

    /**
    Returns a string representation of the subtree of this binary
    tree rooted at the specified node.
   
    @param node The root of the subtree.
    @return A string representation of the subtree of this binary
    tree rooted at the specified node.
 */
 private String preorderPrint(BTNode node) 
 {
     if (node == null) 
     {
         return " ";
     } 
     else 
     {
         return (node.element().toString() + "(" + preorderPrint(node.getLeft()) + "," + preorderPrint(node.getRight()) + ")");
     }
 }

 /**
    Returns a string representation of this binary tree.
   
    @returns A string representation of this binary tree.
 */
 public String toString() 
 {
     return preorderPrint(root);
 } 
}


