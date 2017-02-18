package es.esky.rol.arch.error;

import es.esky.rol.arch.error.factory.ErrorFactory;
import es.esky.rol.arch.error.factory.impl.FallbackErrorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.*;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

/**
 * @author Cristian Mateos López
 */
@Configuration
public class ErrorConfiguration {

    private final ServerProperties serverProperties;

    @Autowired
    public ErrorConfiguration(ServerProperties serverProperties) {
        this.serverProperties = serverProperties;
    }

    @Bean
    public ErrorFactory errorService() {
        return new FallbackErrorFactory();
    }

    @Bean
    public es.esky.rol.arch.error.ErrorController errorController(ErrorAttributes errorAttributes, ErrorFactory errorService) {
        return new es.esky.rol.arch.error.ErrorController(errorAttributes, serverProperties.getError(), errorService);
    }

    @Bean
    public ErrorAttributes errorAttributes() {
        return new DefaultErrorAttributes();
    }

    @Bean
    public ErrorPageCustomizer errorPageCustomizer() {
        return new ErrorPageCustomizer(serverProperties);
    }

    private static class ErrorPageCustomizer implements ErrorPageRegistrar, Ordered {

        private final ServerProperties properties;

        ErrorPageCustomizer(ServerProperties properties) {
            this.properties = properties;
        }

        @Override
        public void registerErrorPages(ErrorPageRegistry registry) {
            ErrorPage errorPage = new ErrorPage(this.properties.getServletPrefix() + this.properties.getError().getPath());
            registry.addErrorPages(errorPage);
        }

        @Override
        public int getOrder() {
            return 0;
        }
    }
}
