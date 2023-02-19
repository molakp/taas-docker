package bnext.backend.api.prova;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository itemRepository;

    public @NotNull String createItem(@NotNull Item item) {

        itemRepository.save(item);
        return "saved cart " + item;


    }

    public List<Item> getItemsByCart(Cart cart) {
        return itemRepository.getItemsByCart(cart);
    }

    public @NotNull String deleteItem(@NotNull Item item) {
        itemRepository.delete(item);
        return "Deleted item " + item.getId();
    }
}
