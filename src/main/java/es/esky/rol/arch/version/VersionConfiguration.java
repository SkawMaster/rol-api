package es.esky.rol.arch.version;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * @author Cristian Mateos López
 */
@Configuration
@PropertySource(value = "classpath:/META-INF/build-info.properties", ignoreResourceNotFound = true)
public class VersionConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(VersionConfiguration.class);

    @Bean
    public EmbeddedServletContainerCustomizer versionContextPath(Environment environment) {
        return new ServletCustomizer(environment);
    }

    private static class ServletCustomizer implements EmbeddedServletContainerCustomizer {

        private final Environment environment;

        ServletCustomizer(Environment environment) {
            this.environment = environment;
        }

        @Override
        public void customize(ConfigurableEmbeddedServletContainer container) {
            if (environment.containsProperty("build.version")) {
                String contextPath = "/v" + environment.getProperty("build.version");
                LOGGER.info(String.format("Context path initialize on: %s", contextPath));
                container.setContextPath(contextPath);
            }
        }
    }
}
