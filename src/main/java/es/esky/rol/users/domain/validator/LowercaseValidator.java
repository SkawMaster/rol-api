package es.esky.rol.users.domain.validator;

import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/**
 * @author Cristian Mateos López
 */
@Component
class LowercaseValidator implements ConstraintValidator<Lowercase, String> {

    private final Pattern hasLowercase = Pattern.compile("[a-z]");

    @Override
    public void initialize(Lowercase constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return hasLowercase.matcher(value).find();
    }
}
