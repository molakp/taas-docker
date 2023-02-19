package bnext.backend.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

//@AllArgsConstructor
// Se metto @NoArgsConstructor e tolgo il costruttore vuoto da errore che non c'è il costruttore default
// anche se c'è l'annotazione, ma che cazzo ne so...
//@NoArgsConstructor
@Getter
@Setter
//@ToString // pure questo non funziona bene, meglio usare quello sotto
@Entity
public class Position {

    @Id //dice che questa e la chiave primaria
    @GeneratedValue
    private UUID id;
    private Double latitude;
    private Double longitude;

    // Se tolgo questo per mettere l'annotazione @NoArgsConstructor da errore...
    public Position() {
    }

    public Position(@JsonProperty("id") UUID id,
                    @JsonProperty("latitude") Double latitude,
                    @JsonProperty("longitude") Double longitude) {

        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    @Override
    public String toString() {
        return "Position{" +
                "id=" + id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
