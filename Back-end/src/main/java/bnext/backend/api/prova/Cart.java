package bnext.backend.api.prova;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table
public class Cart {
    @Id
    @GeneratedValue
    private UUID id;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Item> items = new ArrayList<>();

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
/*@OneToMany( mappedBy = "cart",
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JoinColumn(name="cart_id")
    List<Item> items = new ArrayList<>(); */


    @JsonCreator
    public Cart(@JsonProperty("id") UUID id) {
        this.id = id;
    }

    public Cart() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public @NotNull String toString() {
        System.out.println("Invoked Cart . toString");
        return "Cart{" +
                "id=" + id +
                ", items=" + items +
                '}';
    }
}
