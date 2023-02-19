package bnext.backend.api.prova;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ItemController {

    @Autowired
    private ItemService ItemService;

    @RequestMapping(method = RequestMethod.POST, value = "item")
    public @NotNull String createItem(@RequestBody @NotNull Item item) {
        return ItemService.createItem(item);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "item")
    public @NotNull String deleteItem(@RequestBody @NotNull Item item) {
        return ItemService.deleteItem(item);
    }

    @RequestMapping(method = RequestMethod.GET, value = "item")
    public String getItemsByCart(@RequestBody Cart cart) {
        return ItemService.getItemsByCart(cart).toString();
    }
}
