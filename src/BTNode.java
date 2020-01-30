/**
   The class BTNode implements the interface Position and is used in the
   implementation of the interface BinaryTree by means of a linked structure.
  
   @author     Franck van Breugel
   @version    1.2    June 23, 2000
*/
public class BTNode implements Position 
{
    private Object element;         // element stored in this node
    private BTNode left;            // left child of this node
    private BTNode right;           // right child of this node
    private BTNode parent;          // parent of this node
    
    /** Constructs an empty node. */
    public BTNode() {}

    /**
       Constructs a Node with the specified element, left child, right child 
       and parent.
      
       @param element The element stored in this node.
       @param left The left child of this node.
       @param right The right child of this node.
       @param parent The parent of this node.
    */
    public BTNode(Object element, BTNode left, BTNode right, BTNode parent) 
    {
         this.element = element;
         this.left = left;
         this.right = right;
         this.parent = parent;
    }

    public Object element() 
    {
        return element;
    }

    /**
       Returns the left child of this node.
      
       @return The left child of this node.
    */
    public BTNode getLeft() 
    {
        return left;
    }

    /**
       Returns the right child of this node.
      
       @return The right child of this node.
    */
    public BTNode getRight() 
    {
        return right;
    }

    /**
       Returns the parent of this node.
      
       @return The parent of this node.
    */
    public BTNode getParent() 
    {
        return parent;
    }

    /**
       Set the element of this node to the specified element.
      
       @param element The element of this node.
    */
    public void setElement(Object element) 
    {
        this.element = element;
    }

    /**
       Set the left child of this node to the specified node.
      
       @param left The left child of this node.
    */
    public void setLeft(BTNode left) 
    {
        this.left = left;
    }

    /**
       Set the right child of this node to the specified node.
      
       @param right The right child of this node.
    */
    public void setRight(BTNode right) 
    {
        this.right = right;
    }

    /**
       Set the parent of this node to the specified node.
      
       @param parent The parent of this node.
    */
    public void setParent(BTNode parent) 
    {
        this.parent = parent;
    }

    /** 
       Returns a string representation of the element stored in this node. 
      
       @return A string representation of the element stored in this node.
    */
    public String toString() 
    {
        return element.toString();
    }
}
