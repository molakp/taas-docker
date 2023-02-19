package bnext.backend.api.car;

import bnext.backend.api.user.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface CarRepository extends CrudRepository<Car, UUID> {
    // findBy"Property", formato particolare grazie alla quale non è necessario definire questo metodo, CrudRepository sa già cosa fare.
    @NotNull List<Car> findByUser(User user);

    @NotNull Car findByPlateNumber(String plateNumber);
}
