package MVC.util.Cashe;

import java.io.IOException;

/**
 * Created by en on 06.05.2015.
 */
public class DoesNotExistException extends RuntimeException {
    public DoesNotExistException(Throwable cause) {
        super(cause);
    }

    public DoesNotExistException(String message, Throwable cause) {
        super(message, cause);
    }

    public DoesNotExistException(String message) {
        super(message);
    }

    public DoesNotExistException() {
    }
}
