import java.util.Iterator;

/**
   An inspectable container contains a collection of elements.
 
   @author      Franck van Breugel
   @version     1.1    March 1, 2001
*/
public interface InspectableContainer 
{
    /** 
       Returns the size of this container. 

       @return The size of this container.
    */
    public int size();

    /** 
       Tests if this container is empty. 

       @return true if this container is empty, false otherwise.
    */
    public boolean isEmpty();

    /**
       Returns the collection of elements of this container.
     
       @return The collection of elements of this container.
    */
    public Iterator elements();
}
