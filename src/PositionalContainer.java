/**
   The PositionalContainer interface specifies a collection of elements of
   which each element is associated with a position.  The container
   can be changed.
  
   @author      Franck van Breugel
   @version     1.4	March 2, 2001
   @see Position
*/
public interface PositionalContainer extends InspectablePositionalContainer
{
    /**
       Replaces the element at the specified position in this container with 
       the specified element and returns the replaced element.

       @param position The position the element of which is to be replaced.
       @param element The replacement element.
       @return The replaced element.
    */
    public Object replaceElement(Position position, Object element);

    /**
       Swaps the elements of the specified positions in this container.

       @param first The position the element of which is to be swapped.
       @param second The position the element of which is to be swapped.
    */
    public void swapElements(Position first, Position second);
}
