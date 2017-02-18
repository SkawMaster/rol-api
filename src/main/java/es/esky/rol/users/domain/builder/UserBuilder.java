package es.esky.rol.users.domain.builder;

import es.esky.rol.users.domain.User;

/**
 * @author Cristian Mateos López
 */
public class UserBuilder {

    private User user;

    private UserBuilder() {
        this.user = new User();
    }

    public static UserBuilder user() {
        return new UserBuilder();
    }

    public UserBuilder withUsername(String username) {
        user.setUsername(username);
        return this;
    }

    public UserBuilder withPassword(String password) {
        user.setPassword(password);
        return this;
    }

    public User build() {
        return this.user;
    }
}
