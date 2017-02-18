package es.esky.rol.arch.error.factory;

import java.io.Serializable;
import java.util.Map;

/**
 * @author Cristian Mateos López
 */
public interface ErrorFactory<T extends Serializable> {
    T create(Map<String, Object> attributes);
}
