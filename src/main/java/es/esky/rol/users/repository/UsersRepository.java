package es.esky.rol.users.repository;

import es.esky.rol.users.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author Cristian Mateos López
 */
public interface UsersRepository extends PagingAndSortingRepository<User, String> {
}
