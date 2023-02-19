package bnext.backend.api.prova;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface ItemRepository extends CrudRepository<Item, UUID> {

    @NotNull List<Item> getItemsByCart(Cart cart);
}
