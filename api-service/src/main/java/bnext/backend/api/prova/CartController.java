package bnext.backend.api.prova;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CartController {

    @Autowired
    private CartService cartService;

    @RequestMapping(method = RequestMethod.POST, value = "cart")
    public @NotNull String createCart(@RequestBody @NotNull Cart cart) {
        return cartService.createCart(cart);
    }


    @RequestMapping(method = RequestMethod.GET, value = "cart")
    public String getCarts() {
        return cartService.getCarts().toString();
    }


}
