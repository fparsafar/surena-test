package ir.surena.sample.exception;

import org.springframework.http.HttpStatus;

public class CommonException extends Exception {
    protected int code;
    private HttpStatus httpStatus;
    protected boolean alwaysOk = false;

    public CommonException(HttpStatus status) {
        this.httpStatus = status;
    }

    public CommonException(HttpStatus status, String reason) {
        super(reason);
        this.httpStatus = status;
    }

    public CommonException(HttpStatus status, String reason, int code) {
        super(reason);
        this.code = code;
        this.httpStatus = status;
    }

    public CommonException(HttpStatus status, String reason, Throwable cause) {
        super(reason, cause);
        this.httpStatus = status;
    }

    public CommonException(HttpStatus status, String reason, Throwable cause, int code) {
        super(reason, cause);
        this.code = code;
        this.httpStatus = status;
    }

    public int getCode() {
        return this.code;
    }

    public HttpStatus getHttpStatus() {
        return this.httpStatus;
    }

    public boolean isAlwaysOk() {
        return this.alwaysOk;
    }
}

