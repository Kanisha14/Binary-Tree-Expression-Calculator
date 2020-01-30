import java.util.Iterator;

/**
   An inspectable positional container contains a collection of elements.
   Each element is associated with a position.
 
   @author      Franck van Breugel
   @version     1.1    March 4, 2001
*/
public interface InspectablePositionalContainer extends InspectableContainer 
{
    /**
       Returns an enumeration of all the positions of this sequence.
      
       @return An enumeration of all the positions of this sequence.
    */
    public Iterator positions();
}
