package bnext.backend.api.feedback;

import bnext.backend.api.car.Car;
import bnext.backend.api.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@DynamicUpdate

public class Feedback {
    //dato che Feedback è @Entity bisogna assegnargli una chiave primaria che è Id
    @Id
    @GeneratedValue
    public UUID idFeedback;
    @Column(nullable = false)
    public String comment;

    public String nomeUtente, cognomeUtente;


    @ManyToOne
    @JoinColumn(name = "userId")
    @JsonBackReference(value = "feedbacks")
    //ATTENZIONE: questa annotazione esclude il campo user dall'essere ritornato in postman
    //per evitare cicli infiniti!
    private User user;

    @ManyToOne
    @JoinColumn(name = "carId")
    @JsonBackReference(value = "CarFeedback")
    private Car car;

//----riga di codice importante per sapere il formato dei dati per i vari oggetti da scrivere su postman
    //----return "Format is "+ new Gson().toJson(tryfeedback) + " Error is ";

    /*@JsonCreator
    public Feedback(@JsonProperty("idFeedback")UUID idFeedback,@JsonProperty("comment") String comment) {
        this.idFeedback = idFeedback;
        this.comment = comment;
    }*/

    @Override
    public String toString() {
        return "Feedback{" +
                "idFeedback=" + idFeedback +
                ", comment='" + comment + '\'' +
                ", user=" + user.toString() +
                //", car=" + car.toString() +
                '}';
    }
}
