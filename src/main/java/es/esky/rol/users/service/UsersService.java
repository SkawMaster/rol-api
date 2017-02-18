package es.esky.rol.users.service;

import es.esky.rol.users.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * @author Cristian Mateos López
 */
public interface UsersService {
    Page<User> findByCriteria(Pageable page);

    User findByUsername(String username);

    void deleteByUsername(String username);

    User save(User user);

    boolean exists(String username);
}
