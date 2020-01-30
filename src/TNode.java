import java.util.Iterator;

/**
   The class TNode implements the interface Position and is used in the
   implementation of the interface Tree by means of a linked structure.
  
   @author     Franck van Breugel
   @version    1.2    June 23, 2000
*/
public class TNode implements Position 
{
    private Object element;       // element stored in this node
    private TNode parent;         // parent of this node
    private Iterator children;      // children of this node
    
    /** Constructs an empty node. */
    public TNode() {}

    /**
       Constructs a node with the specified element, parent and children.
      
       @param element The element stored in this node.
       @param parent The parent of this node.
       @param children The childen of this node.
    */
    public TNode(Object element, TNode parent, Iterator children) 
    {
         this.element = element;
         this.parent = parent;
         this.children = children;
    }

    public Object element() 
    {
        return element;
    }

    /**
       Returns the parent of this node.
      
       @return The parent of this node.
    */
    public TNode getParent() 
    {
        return parent;
    }

    /**
       Returns the children of this node.
      
       @return The children of this node.
    */
    public Iterator getChildren()
    {
        return children;
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
       Set the parent of this node to the specified node.
      
       @param parent The parent of this node.
    */
    public void setParent(TNode parent) 
    {
        this.parent = parent;
    }

    /**
       Set the children of this node to the specified collection.
      
       @param children The children of this node.
    */
    public void setChildren(Iterator children)
    {
        this.children = children;
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




