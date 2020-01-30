/**
   The InspectableBinaryTree interface specifies a binary tree the elements of which
   are associated with positions.
  
   @author      Franck van Breugel
   @version     1.3    March 4, 2001
*/
public interface InspectableBinaryTree extends InspectableTree 
{
    /**
       Returns the left child of the specified position.

       @param position The position of which the left child is to be returned.
       @return The left child of the specified position.
       @exception InvalidPositionException if the specified position does not have 
       a left child.
    */
    public Position leftChild(Position position) throws InvalidPositionException;

    /**
       Returns the right child of the specified position.

       @param position The position of which the right child is to be returned.
       @return The right child of the specified position.
       @exception InvalidPositionException if the specified position does not have a 
       right child.
    */
    public Position rightChild(Position position) throws InvalidPositionException;

    /**
       Returns the sibling of the specified position.

       @param position The position of which the sibling is to be returned.
       @exception InvalidPositionException if the specified position does not
       have a sibling.
    */
    public Position sibling(Position position) throws InvalidPositionException;
}
