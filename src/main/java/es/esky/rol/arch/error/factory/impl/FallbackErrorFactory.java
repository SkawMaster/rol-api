package es.esky.rol.arch.error.factory.impl;

import es.esky.rol.arch.error.entity.JsonError;
import es.esky.rol.arch.error.factory.ErrorFactory;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Cristian Mateos López
 */
public class FallbackErrorFactory implements ErrorFactory<JsonError> {

    @Override
    public JsonError create(Map<String, Object> attributes) {
        List<JsonError> errors = new ArrayList<>();
        if (attributes.containsKey("errors")) {
            for (Object error : (List) attributes.get("errors")) {
                ObjectError fieldError = (ObjectError) error;
                errors.add(new JsonError(fieldError.getCode(), fieldError.getDefaultMessage()));
            }
        }
        String code = attributes.get("status").toString();
        String message = attributes.get("error").toString();
        return new JsonError(code, message, errors);
    }
}
