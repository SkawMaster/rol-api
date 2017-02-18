package es.esky.rol.arch.jpa;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.lang.annotation.Documented;

/**
 * @author Cristian Mateos López
 */
@Documented
@Service
@Qualifier(JpaService.JPA_QUALIFIER)
public @interface JpaService {
    String JPA_QUALIFIER = "JPA";
}
