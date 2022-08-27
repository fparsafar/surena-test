package ir.surena.sample.exception;

import org.springframework.http.HttpStatus;

public class NotValidRequestException extends SenderException {

    public NotValidRequestException(String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }

    public NotValidRequestException(String message, int code) {
        super(HttpStatus.BAD_REQUEST,message, code);
    }
}
