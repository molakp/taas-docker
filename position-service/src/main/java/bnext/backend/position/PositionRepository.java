package bnext.backend.position;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface PositionRepository extends CrudRepository<Position, String> {
    Position findByid(UUID uuid);

}
