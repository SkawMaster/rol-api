package es.esky.rol.users.repository;

import es.esky.rol.users.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UsersRepository extends PagingAndSortingRepository<User, String> {
}
