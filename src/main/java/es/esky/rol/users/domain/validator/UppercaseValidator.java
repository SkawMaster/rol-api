package es.esky.rol.users.domain.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Cristian Mateos López
 */
@Component
class UppercaseValidator implements ConstraintValidator<Uppercase, String> {

    private final Pattern hasUppercase = Pattern.compile("[A-Z]");

    @Override
    public void initialize(Uppercase constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasUppercase.matcher(value).find();
    }
}
