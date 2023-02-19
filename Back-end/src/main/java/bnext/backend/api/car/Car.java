package bnext.backend.api.car;

import bnext.backend.api.feedback.Feedback;
import bnext.backend.api.position.Position;
import bnext.backend.api.reservation.Reservation;
import bnext.backend.api.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;
import org.jetbrains.annotations.NotNull;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Car {
    @Id
    @GeneratedValue
    private UUID carId;

    private String name, carModel;
    @Column(nullable = false, unique = true)
    private String plateNumber;
    private Integer battery, priceHour, priceKm;
    @Column(nullable = false)
    private Boolean availabilityPresent;


    /*Propago tutte le operazioni tranne da DELETE perché se cancello una macchina
     * non devo cancellare anche l'utente che la possiede!*/

    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference(value = "ownedCars")
    //ATTENZIONE: questa annotazione esclude il campo user dall'essere ritornato in postman
    //per evitare cicli infiniti!
    private User user;

    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true, mappedBy = "car")
    @JsonManagedReference(value = "CarReservations")
    private List<Reservation> reservation = new ArrayList<>();

  /*  @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private Position position;*/
    @Column
    private String positionID;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "CarFeedback")
    private List<Feedback> feedback = new ArrayList<>();

/*@JsonCreator
    public Car(@JsonProperty("id")UUID id, @JsonProperty("name") String name) {
        this.id = id;

        this.name = name;

    }*/

    @Override
    public @NotNull String toString() {
        return "Car{" +
                "id=" + carId +
                ", name='" + name + '\'' +
                ", carModel='" + carModel + '\'' +
                ", extraData='" + plateNumber + '\'' +
                ", battery=" + battery +
                ", priceHour=" + priceHour +
                ", priceKm=" + priceKm +
                ", availabilityPresent=" + availabilityPresent +
                ", user=" + user.toString() +
                ", reservation=" + reservation +
                ", feedback=" + feedback +
                ", positionID=" + positionID +
                '}';
    }

}
