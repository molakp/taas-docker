package bnext.backend.api.position;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;


@Getter
@Setter
@ToString
@Entity
public class Position {

    @Id() //dice che questa e la chiave primaria
    @GeneratedValue
    private UUID id;
    private Double latitude;
    private Double longitude;


    public Position() {
    }

    public Position(@JsonProperty("id") UUID id,
                    @JsonProperty("latitude") Double latitude,
                    @JsonProperty("longitude") Double longitude) {

        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
    }


}
