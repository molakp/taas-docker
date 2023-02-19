package bnext.backend.api.reservation;

import bnext.backend.api.car.Car;
import bnext.backend.api.position.Position;
import bnext.backend.api.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;


// in questo modo JPE sa che deve creare una tabella chiamata Reservation e questa tabella avrà tante colonne
// quante sono le variabili d'istanza, devo inoltre specificare qual'è la chiave primaria con l'annotazione @Id
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Reservation {
    @ToString.Include
    @Id
    @GeneratedValue
    @Column(nullable = false)
    private UUID reservationId;
    @ToString.Include
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+01:00")
    private Date startOfBook;
    @ToString.Include
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "GMT+01:00")
    private Date endOfBook;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name = "userId")
    @JsonBackReference(value = "UserReservations")
    private User user;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn
    @JsonBackReference(value = "CarReservations")
    //@JoinColumn(name = "id")
    private Car car;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Position destination;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Position startPosition;
    /*public Reservation(UUID reservationId) {
        this.reservationId = reservationId;
    }
*/
    // da rivedere, utile per aggiungere una nuova reservation
    /*public Reservation(@JsonProperty("user")User user,@JsonProperty("car")Car car, @JsonProperty("startPosition") Position startPosition,@JsonProperty("destination")Position destination,@JsonProperty("startOfBook") Date startOfBook,@JsonProperty("endOfBook") Date endOfBook,@JsonProperty("reservationTime") int reservationTime) {
        this.user = user;
        this.car = car;
        this.destination = destination;
        this.startPosition = startPosition;
        this.startOfBook = startOfBook;
        this.endOfBook = endOfBook;
        this.reservationTime = reservationTime;
    }





        public Reservation( Car car,User user, String customerId, Position destination, Position  startPosition, Long reservationId) {
            this.user = user;
            this.car = car;
            this.destination = destination;
            this.startPosition = startPosition;
            this.reservationId = reservationId;
        }

        public Reservation(Long reservationId, User user, Position destination, Position startingPost, Date pickupTime, int reservationTime) {
            this.reservationId = reservationId;

            this.user = user;
            this.destination = destination;
            this.startPosition = startingPost;
            this.pickupTime = pickupTime;
            this.reservationTime = reservationTime;
        }
    */
 /*  // Genera gli ID univoci
    public UUID generateUUID(){
        //.toString() per avere una stringa
        UUID uniqueID = UUID.randomUUID();

        //System.out.println("Ecco l'id : "+ uniqueID);
        return uniqueID;
    }

*/
}
