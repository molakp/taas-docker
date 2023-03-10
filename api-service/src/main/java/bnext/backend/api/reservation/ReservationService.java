package bnext.backend.api.reservation;

import bnext.backend.api.car.Car;
import bnext.backend.api.car.CarService;
import bnext.backend.api.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    // Ottengo automaticamente un'istanziazione dell'oggetto grazie a Spring, quindi utilizzo i metodi predefiniti
    // della classe CrudRepository
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private CarService carService;


    @Autowired
    private UserService userService;

    // recupero tutte le prenotazioni
    public @NotNull List<Reservation> getAllReservation() {
        ArrayList reservations = new ArrayList();
        reservationRepository.findAll().forEach(reservations::add);
        return reservations;
    }

    public Optional<Reservation> getReservation(@NotNull UUID id) {

        if (reservationRepository.findById(id).isEmpty()) {
            System.out.println("No reservations with this id in the database");
            return Optional.empty();
        }

        // Prendo dai Reservation inseriti nel database, quello identificato da "id"
        return reservationRepository.findById(id);
    }

    public @NotNull String addReservation(Reservation reservation) {
        //System.out.println("Date********\nStartOfBook : "+ reservation.getStartOfBook()+"\nEndOfBook : "+reservation.getEndOfBook());
        List<Car> availableCars = searchAvailableCars(reservation.getStartOfBook(), reservation.getEndOfBook());
        // booleano che mi indica se la macchina che vogliamo prenotare ?? presente nella lista delle macchine disponibili
        boolean isPresent = false;
        // vado a vedere se la macchina che vogliamo prenotare ?? disponibile (cio?? se ?? presente nella lista availableCars)
        for (Car car : availableCars)
            if (reservation.getCar().getCarId().equals(car.getCarId())) {
                isPresent = true;
                // ho trovato la macchina, ?? presente, quindi non c'?? bisogno di scorrere tutta la lista
                break;
            }
        if (!isPresent) return "Car in this date isn't available, is already booked";
        reservationRepository.save(reservation);
        return "RESERVATION SUCCESFULLY ADDED";
    }

    public @NotNull String updateReservation(@NotNull Reservation reservation) {
        // verifico che esista la prenotazione con l'id indicato
        if (reservationRepository.findById(reservation.getReservationId()).isEmpty())
            return "No reservation with id '" + reservation.getReservationId() + "' in the database";

        Reservation modifiedReservation = reservationRepository.findById(reservation.getReservationId()).get();

        ReflectionUtils.doWithFields(reservation.getClass(), field -> {
            field.setAccessible(true);
            // il controllo sulla lista evita di assegnare una nuova lista se quella passata ?? vuota
            // Le liste sono gestite dopo
            if (field.get(reservation) != null && !field.getName().equals("id") && !(field.get(reservation) instanceof List)) {

                // Se il field non ?? null allora accedo ai field della macchina salvata
                ReflectionUtils.doWithFields(modifiedReservation.getClass(), old_field -> {
                    old_field.setAccessible(true);
                    // se ?? lo stesso campo allora lo aggiorno
                    if (old_field.getName().equals(field.getName())) {
                        System.out.println("Updating field " + old_field.getName() + ": " + field.get(reservation));
                        old_field.set(modifiedReservation, field.get(reservation));

                    }
                });
            }
        });
        //questo metodo basta gia ad aggiornare la macchina
        reservationRepository.save(modifiedReservation);
        return "RESERVATION SUCCESSFULLY UPDATED";
    }

    // Elimino semplicemente la prenotazione identificata dall'Id passato come parametro dall'utente
    public @NotNull String deleteReservation(@NotNull UUID id) {
        if (reservationRepository.findById(id).isEmpty())
            return "No reservations with this id in the database";

        reservationRepository.deleteById(id);
        return "SUCCESFULLY DELETED";
    }

    public @NotNull List<Car> searchAvailableCars(@NotNull Date startDataOfBook, @NotNull Date endDataOfBook) {
        // Lista che conterr?? le macchine disponibili
        List<Car> availableCars = new ArrayList<>();
        Set<Car> availableCarsSet = new HashSet<Car>();
        // Itero su tutte le macchine e
        for (Car currentCar : carService.getAllCars()) {
            // verifico che la macchina sia disponibile
            if (currentCar.getAvailabilityPresent()) {
                // se la macchina non ?? presente in nessuna prenotazione, posso aggiungerla a quelle disponibili
                // senza fare ulteriori controlli
                if (currentCar.getReservation().isEmpty()) {
                    availableCars.add(currentCar);
                    availableCarsSet.add(currentCar);
                    // quindi passo alla prossima macchina
                    continue;
                }
                // Altrimenti recupero tutte le prenotazioni della macchina corrente
                List<Reservation> allReservationsOfCar = new ArrayList<>();
                reservationRepository.findAll().forEach(reservation -> {
                    if (currentCar.getCarId().equals(reservation.getCar().getCarId()))
                        allReservationsOfCar.add(reservation);
                });
                boolean isAviable = true;
                // Quindi aggiungo opportunamente alle macchine disponibili
                for (Reservation currentReservation : allReservationsOfCar) {
                    // Caso in cui la prenotazione che vogliamo fare ?? prima della prenotazione gi?? fatta
                    Timestamp timestampEndDataOfBook = new Timestamp(endDataOfBook.getTime());
                    Timestamp timestampStartDataOfBook = new Timestamp(startDataOfBook.getTime());

                    if (Objects.requireNonNull(timestampEndDataOfBook).before(currentReservation.getStartOfBook())
                            || Objects.requireNonNull(timestampStartDataOfBook).after(currentReservation.getEndOfBook())) {
                    } else isAviable = false;

                    //availableCars.add(currentCar);
                    //availableCarsSet.add(currentCar);

                    // Caso in cui la prenotazione che vogliamo fare ?? dopo della prenotazione gi?? fatta
                    /*else if (Objects.requireNonNull(timestampStartDataOfBook).after(currentReservation.getEndOfBook())) {
                        availableCars.add(currentCar);
                        availableCarsSet.add(currentCar);
                    }*/

                    //if (endDataOfBook.before(currentReservation.getStartOfBook()) && endDataOfBook.before(currentReservation.getStartOfBook())) availableCars.add(currentCar);
                }
                if (isAviable) {
                    availableCars.add(currentCar);
                    availableCarsSet.add(currentCar);
                }

            }
        }
        List<Car> result = new ArrayList<>(availableCarsSet);
        return result;
    }

    public List<Reservation> findByCarOwnerId(UUID id) {

        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

        reservationList.addAll(this.getAllReservation().stream().filter(
                x ->
                        x.getCar().getUser().getUserId().equals(id)
        ).collect(Collectors.toList()));

        return reservationList;


    }


    public String addReservationWithPosition(Reservation reservation) {

        //System.out.println("Date********\nStartOfBook : "+ reservation.getStartOfBook()+"\nEndOfBook : "+reservation.getEndOfBook());
        List<Car> availableCars = searchAvailableCars(reservation.getStartOfBook(), reservation.getEndOfBook());
        // booleano che mi indica se la macchina che vogliamo prenotare ?? presente nella lista delle macchine disponibili
        boolean isPresent = false;
        // vado a vedere se la macchina che vogliamo prenotare ?? disponibile (cio?? se ?? presente nella lista availableCars)
        for (Car car : availableCars)
            if (reservation.getCar().getCarId().equals(car.getCarId())) {
                isPresent = true;
                // ho trovato la macchina, ?? presente, quindi non c'?? bisogno di scorrere tutta la lista
                break;
            }
        if (!isPresent) return "Car in this date isn't available, is already booked";

        reservationRepository.save(reservation);
        return "RESERVATION SUCCESFULLY ADDED";
    }
}
