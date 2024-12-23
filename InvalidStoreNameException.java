/**
 * InvalidStoreNameException
 *
 * This class represents a custom Exception, thrown when an Invalid Store Name is detected.
 */
public class InvalidStoreNameException extends Exception {
    public InvalidStoreNameException(String message) {
        super (message);
    }

    public InvalidStoreNameException() {
        super();
    }
}