package es.esky.rol.arch.error.exception;

import org.springframework.http.HttpStatus;

/**
 * @author Cristian Mateos López
 */
public abstract class APIException extends RuntimeException {

    private final HttpStatus status;
    private final String errorCode;

    public APIException(HttpStatus status, String errorCode, String msg) {
        super(msg);
        this.status = status;
        this.errorCode = errorCode;
    }

    public HttpStatus getStatus() {
        return this.status;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
