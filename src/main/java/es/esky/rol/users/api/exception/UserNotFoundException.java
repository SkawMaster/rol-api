package es.esky.rol.users.api.exception;

import es.esky.rol.arch.error.exception.APIException;
import org.springframework.http.HttpStatus;

/**
 * @author Cristian Mateos López
 */
public class UserNotFoundException extends APIException {

    public final static String USER_NOT_FOUND_ERROR_CODE = "user_not_found";
    public final static String USER_NOT_FOUND_MSG = "es.esky.rol.user.validation.user_not_found";

    public UserNotFoundException() {
        super(HttpStatus.NOT_FOUND, USER_NOT_FOUND_ERROR_CODE, USER_NOT_FOUND_MSG);
    }
}
