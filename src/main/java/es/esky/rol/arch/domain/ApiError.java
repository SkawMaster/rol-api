package es.esky.rol.arch.domain;

import java.io.Serializable;

public class ApiError implements Serializable {
    private static final long serialVersionUID = 1L;

    private String code;
    private String message;

    public ApiError(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
