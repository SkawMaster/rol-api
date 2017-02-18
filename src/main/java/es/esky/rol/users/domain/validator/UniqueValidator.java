package es.esky.rol.users.domain.validator;

import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author Cristian Mateos López
 */
@Component
public class UniqueValidator implements ConstraintValidator<Unique, User> {

    private final UsersService usersService;

    @Autowired
    public UniqueValidator(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public void initialize(Unique constraint) {
    }

    @Override
    public boolean isValid(User user, ConstraintValidatorContext context) {
        return !usersService.exists(user.getUsername());
    }
}
