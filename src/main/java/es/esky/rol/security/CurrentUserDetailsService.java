package es.esky.rol.security;

import es.esky.rol.users.api.exception.UserNotFoundException;
import es.esky.rol.users.domain.User;
import es.esky.rol.users.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CurrentUserDetailsService implements UserDetailsService {

    private final UsersService usersService;

    @Autowired
    public CurrentUserDetailsService(UsersService usersService) {
        this.usersService = usersService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;
        try {
            user = usersService.findByUsername(username);
        } catch (UserNotFoundException e) {
            String error = String.format("User %s was not found.", username);
            throw new UsernameNotFoundException(error, e);
        }
        return new CurrentUserDetails(user);
    }
}
