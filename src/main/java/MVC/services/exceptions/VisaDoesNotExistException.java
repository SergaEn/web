package MVC.services.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Created by varArg on 02.04.2015.
 */

@ResponseStatus(value = HttpStatus.CONFLICT)
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
