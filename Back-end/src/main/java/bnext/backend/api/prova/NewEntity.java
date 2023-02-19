package bnext.backend.api.prova;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "new_entity")
public class NewEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private UUID id;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}