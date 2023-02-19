package bnext.backend.api.prova;


import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public @NotNull String createCart(@NotNull Cart cart) {

        cartRepository.save(cart);
        return "saved cart " + cart;


    }

    public @NotNull List<Cart> getCarts() {
        List<Cart> carts = new ArrayList<>();
        cartRepository.findAll().forEach(carts::add);
        if (carts.isEmpty())
            System.out.println("There's no car to show, the list is empty");
        return carts;
    }
}
