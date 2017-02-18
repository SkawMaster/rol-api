package es.esky.rol.arch.error.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * @author Cristian Mateos López
 */
public class JsonError implements Serializable {

    private final String code;
    private final String message;
    private final List<JsonError> errors;

    public JsonError(String code, String message) {
        this.code = code;
        this.message = message;
        this.errors = Collections.emptyList();
    }

    public JsonError(String code, String message, List<JsonError> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
    }

    @JsonProperty
    public String getCode() {
        return this.code;
    }

    @JsonProperty
    public String getMessage() {
        return this.message;
    }

    @JsonProperty
    @JsonInclude(Include.NON_EMPTY)
    public List<JsonError> getErrors() {
        return this.errors;
    }
}
