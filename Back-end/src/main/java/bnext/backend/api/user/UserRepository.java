package bnext.backend.api.user;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {
    //@NotNull User findByUsername(String username);
    @NotNull User findByName(String name);

    Optional<User> findByUsername(String userName);


}
