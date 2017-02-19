package es.esky.rol.arch.error;

import java.io.Serializable;

/**
 * @author Cristian Mateos López
 */
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

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
