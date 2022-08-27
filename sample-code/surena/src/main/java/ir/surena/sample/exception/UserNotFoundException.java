package ir.surena.sample.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends SenderException {

    public UserNotFoundException() {
        super("User Not Valid!", HttpStatus.BAD_REQUEST);
    }
}
