package es.esky.rol.arch.domain;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.io.Serializable;

/**
 * Store an api error response.
 */
public class ApiError implements Serializable {
    private static final long serialVersionUID = 1L;

    private final String code;
    private final String message;

    /**
     * Construct an api error.
     * @param code    Unique error identification.
     * @param message Message error.
     */
    public ApiError(final String code, final String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Get code error identification.
     *
     * @return Error identification.
     */
    @JsonGetter
    public String getCode() {
        return code;
    }

    /**
     * Get message error.
     *
     * @return Message error.
     */
    @JsonGetter
    public String getMessage() {
        return message;
    }
}
