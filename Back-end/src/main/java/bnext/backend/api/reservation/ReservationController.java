package bnext.backend.api.reservation;

import bnext.backend.api.car.Car;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@RestController
public class ReservationController {

    // Recupero la/le variabile/i d'istanza, in modo da essere subito accessibili
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;


    // Reqest mapping ha come default una richiesta di get, quindi dico cosa fare in caso di richiesta GET
    // all'endpoint indicato, in questo caso (/reservations)
    @RequestMapping("/reservations")
    public @NotNull List<Reservation> getAllReservation() {
        return reservationService.getAllReservation();
    }

    // Uguale a quella sopra, l'unica differenza è che introduciamo la possibilità di inserire più alternative al posto di "id",
    // quindi passiamo la parola appena scritta dall'utente come variabile di input al metodo "getReservation" tramite il comando @PathVariable
    // ================ "id" è una variabile, utile a mostrare solo una specifica prenotazione =====================
    @RequestMapping("/reservations/user={id}")
    // @PathVariable{foo}, è possibile ad esempio inserire due parole ma passare come parametro solo "foo" ad esempio,
    // in questo caso "/reservations/{id}" diventerà "/reservations/{foo}"
    public Optional<Reservation> getReservationById(@PathVariable @NotNull UUID id) {
        // Torna una reservation dato ul suo ID

        return reservationService.getReservation(id);
    }

    @RequestMapping("/reservations/car-owner-id={userID}")
    // @PathVariable{foo}, è possibile ad esempio inserire due parole ma passare come parametro solo "foo" ad esempio,
    // in questo caso "/reservations/{id}" diventerà "/reservations/{foo}"
    public List<Reservation> getReservationForCarsOwnedByUser(@PathVariable @NotNull UUID userID) {
        return reservationService.findByCarOwnerId(userID);
    }

    // Adesso specifichiamo che vogliamo fare una richiesta POST, gestita digitando l'endpoint "/reservations" nell'URL,
    // quindi si aggiunge una determinata prenotazione, passato tramite body dall'utente(lo specifichiamo con il comando @RequestBody).
    // Utilizzando l'app Postman, dopo aver impostato come tipo di richiesta POST, vado in body, inserisco le informazioni ES:{
    //    "reservationId": "AA0a2s55w2",
    //    "carId": "bb1E3R94q8",
    //    "customerId": "qw7T8h42k8",
    //     ecc...
    //}
    // quindi vado in headers e metto come key "Content-Type" e come value "application/json", quindi posso mandare la richiesta( pulsante SEND)
    @RequestMapping(method = RequestMethod.POST, value = "/reservations/add")
    public @NotNull String addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }


    // aggiorno una determinata prenotazione, identificato dall'id passato dall'utente
    @RequestMapping(method = RequestMethod.PUT, value = "/reservations/update")
    public @NotNull String updateReservation(@RequestBody @NotNull Reservation reservation) {
        return reservationService.updateReservation(reservation);
    }

    //elimino una determinata prenotazione, identificata dall'Id
    @RequestMapping(method = RequestMethod.DELETE, value = "/reservations/delete={id}")
    public @NotNull String deleteReservation(@PathVariable @NotNull UUID id) {
        return reservationService.deleteReservation(id);
    }

    // Metodo che cerca tutte le macchine disponibili in una determinata data
    @PostMapping("/reservations/availableCars")
    public @NotNull List<Car> searchAvailableCars(@RequestBody @NotNull String payload) throws JSONException {
        Date startOfBookParsed, endOfBookParsed;
        JSONObject obj = new JSONObject(payload);
        try {
            startOfBookParsed = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse((String) obj.get("startOfBook"));
            endOfBookParsed = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse((String) obj.get("endOfBook"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

        return reservationService.searchAvailableCars(startOfBookParsed, endOfBookParsed);
    }
}
