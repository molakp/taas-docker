package bnext.backend.api.user;

import bnext.backend.api.car.Car;
import bnext.backend.api.feedback.Feedback;
import bnext.backend.api.reservation.Reservation;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Builder
public class User {
    @Id
    @GeneratedValue
    private UUID userId;
    private String name;
    private String surname;
    //@JsonFormat(pattern = "yyyy-MM-dd")
    //@DateTimeFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private int active = 1;
    // Per inserire più ruoli, inserirli tutti nella stessa stringa divisi con ","

    private String permissions;

    /*@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<UserRole> userRoles = new HashSet<>();*/


    private String roles = "USER";

    /*Se cancello user si cancellano anche tutte le sue macchine
     * Con mappedBy sto dicendo a spring che nella classe Car l'utente è chiamato user,
     * quindi lui sa su quale attributo di Car fare la join
     * */
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonManagedReference(value = "ownedCars")
    private List<Car> ownedCars = new ArrayList<>();

    /*Se cancello user si cancellano anche tutte le sue prenotazioni*/
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE, orphanRemoval = true)
    @JsonManagedReference(value = "UserReservations")
    private List<Reservation> reservations = new ArrayList<>();

    /*Se cancello user si cancellano anche i suoi feedbacks, unidirezionale da user*/
    @ToString.Exclude
    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonManagedReference(value = "feedbacks")
    private List<Feedback> feedbacks = new ArrayList<>();

    /*public List<String> getRoleList(){
        if(this.roles != null){
            return Arrays.asList(this.roles.split(","));
        }
        return new ArrayList<>();
    }*/


}
