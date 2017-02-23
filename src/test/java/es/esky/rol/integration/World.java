package es.esky.rol.integration;

import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

/**
 *
 */
@Component
public class World {
    private HttpEntity response;
    private HttpEntity request;

    public HttpEntity getResponse() {
        return response;
    }

    public void setResponse(HttpEntity response) {
        this.response = response;
    }

    public HttpEntity getRequest() {
        return request;
    }

    public void setRequest(HttpEntity request) {
        this.request = request;
    }
}
