package wolox.training.repositories;

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import wolox.training.models.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findFirstByName(String name);
    Optional<User> findByUsername(String name);
}
