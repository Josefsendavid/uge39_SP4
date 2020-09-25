package exceptions;

/**
 *
 * @author David
 */
public class PersonNotFoundException extends Exception{
    public PersonNotFoundException(String message) {
        super(message);
    }
}
