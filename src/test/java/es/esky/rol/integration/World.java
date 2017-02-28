package es.esky.rol.integration;

import es.esky.rol.users.domain.User;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class World {
    private HttpEntity response;
    private User userLogged;

    public HttpEntity getResponse() {
        return response;
    }

    public void setResponse(HttpEntity response) {
        this.response = response;
    }

    public User getUserLogged() {
        return userLogged;
    }

    public void setUserLogged(User userLogged) {
        this.userLogged = userLogged;
    }
}
