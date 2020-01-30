
/**
Thrown when an attempt is made to access and invalid position.

@author      Jeff Edmonds
@version     1.0   March 28, 2015
*/
public class InvalidPositionException extends RuntimeException 
{
 /** 
    Constructs an InvalidKeyException without error message.
  */
 public InvalidPositionException() 
 {
     super();
 }

 /** 
    Constructs an InvalidKeyException with the specified error message.
   
    @param errorMessage Error message.
 */
 public InvalidPositionException(String errorMessage) 
 {
     super(errorMessage);
 }
}