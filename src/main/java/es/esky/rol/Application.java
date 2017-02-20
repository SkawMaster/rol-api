package es.esky.rol;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Api entry point class.
 */
@SpringBootApplication
public class Application {

    private static final int ENCODER_STRENGTH = 10;

    /**
     * Entry point.
     * @param args Application call arguments.
     */
    public static void main(String... args) {
        SpringApplication.run(Application.class);
    }

    /**
     * Configured password encoder bean.
     * @return A configured {@link BCryptPasswordEncoder}.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(ENCODER_STRENGTH);
    }
}
