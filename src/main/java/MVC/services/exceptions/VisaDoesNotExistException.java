package MVC.services.exceptions;

/**
 * Created by varArg on 02.04.2015.
 */
public class VisaDoesNotExistException extends RuntimeException {
    public VisaDoesNotExistException(Throwable cause) {
        super(cause);
    }

    public VisaDoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisaDoesNotExistException(String message) {
        super(message);
    }

    public VisaDoesNotExistException() {
    }
}
