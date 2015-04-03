package MVC.services.exceptions;


public class VisaExistsException extends RuntimeException {
    public VisaExistsException() {
    }

    public VisaExistsException(String message) {
        super(message);
    }

    public VisaExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public VisaExistsException(Throwable cause) {
        super(cause);
    }
}
