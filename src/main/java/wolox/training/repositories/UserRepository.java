package wolox.training.repositories;

import org.springframework.data.repository.CrudRepository;
import wolox.training.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findFirstByName(String name);
    User findByUsername(String name);
}
