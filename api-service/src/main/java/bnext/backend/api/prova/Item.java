package bnext.backend.api.prova;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table

public class Item {
    @Id
    @GeneratedValue
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "cart_id", nullable = false)

    Cart cart;


    @JsonCreator
    public Item(@JsonProperty("id") UUID id, @JsonProperty("cart") Cart cart, @JsonProperty("name") String name) {
        this.name = name;
        this.id = id;
        this.cart = cart;
    }

    public Item() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    @Override
    public @NotNull String toString() {
        System.out.println("Invoked item . toString");
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cart=" + cart.getId() +
                '}';
    }
}
