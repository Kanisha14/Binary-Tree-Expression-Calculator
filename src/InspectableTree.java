import java.util.Iterator;

/**
   The InspectableTree interface specifies an ordered tree the elements of which
   are associated with positions.  The tree cannot be changed.
  
   @author      Franck van Breugel
   @version     1.4    March 4, 2001
   @see Position
*/
public interface InspectableTree extends InspectablePositionalContainer 
{
    /**
       Returns the root of this tree.

       @return The root of this tree.
    */
    public Position root();

    /**
       Returns the parent of the specified position.

       @param position The position the parent of which is to be returned.
       @return The parent of the specified position.
       @exception InvalidPositionException if the specified position is the root of
       the tree.
    */
    public Position parent(Position position) throws InvalidPositionException;

    /**
       Returns the children of the specified position.

       @param position The position the children of which are to be returned.
       @return The children of the specified position.
    */
    public Iterator children(Position position);

    /**
       Tests if the specified position is internal.

       @param position The position to be tested to be internal.
       @return true id the specified position is internal, false otherwise.
    */
    public boolean isInternal(Position position);

    /**
       Tests if the specified position is external.

       @param position The position to be tested to be external.
       @return true id the specified position is external, false otherwise.
    */
    public boolean isExternal(Position position);

    /**
       Tests if the specified position is the root of this tree.

       @param position The position to be tested to be the root of this tree.
       @return true id the specified position is the root, false otherwise.
    */
    public boolean isRoot(Position position);
}
