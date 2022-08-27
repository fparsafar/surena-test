package ir.surena.sample.exception;

import org.springframework.http.HttpStatus;


public  class SenderException extends CommonException {

    public SenderException(String errorMessage, HttpStatus httpStatus) {
        super(httpStatus, errorMessage);
    }

    public SenderException(HttpStatus status, String reason, int code) {
        super(status, reason, code);
    }
}
