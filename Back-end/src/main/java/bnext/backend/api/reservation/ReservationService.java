package bnext.backend.api.reservation;

import bnext.backend.api.car.Car;
import bnext.backend.api.car.CarService;
import bnext.backend.api.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

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
        // booleano che mi indica se la macchina che vogliamo prenotare è presente nella lista delle macchine disponibili
        boolean isPresent = false;
        // vado a vedere se la macchina che vogliamo prenotare è disponibile (cioè se è presente nella lista availableCars)
        for (Car car : availableCars)
            if (reservation.getCar().getCarId().equals(car.getCarId())) {
                isPresent = true;
                // ho trovato la macchina, è presente, quindi non c'è bisogno di scorrere tutta la lista
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
            // il controllo sulla lista evita di assegnare una nuova lista se quella passata è vuota
            // Le liste sono gestite dopo
            if (field.get(reservation) != null && !field.getName().equals("id") && !(field.get(reservation) instanceof List)) {

                // Se il field non è null allora accedo ai field della macchina salvata
                ReflectionUtils.doWithFields(modifiedReservation.getClass(), old_field -> {
                    old_field.setAccessible(true);
                    // se è lo stesso campo allora lo aggiorno
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
        // Lista che conterrà le macchine disponibili
        List<Car> availableCars = new ArrayList<>();
        Set<Car> availableCarsSet = new HashSet<Car>();
        // Itero su tutte le macchine e
        for (Car currentCar : carService.getAllCars()) {
            // verifico che la macchina sia disponibile
            if (currentCar.getAvailabilityPresent()) {
                // se la macchina non è presente in nessuna prenotazione, posso aggiungerla a quelle disponibili
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
                    // Caso in cui la prenotazione che vogliamo fare è prima della prenotazione già fatta
                    Timestamp timestampEndDataOfBook = new java.sql.Timestamp(endDataOfBook.getTime());
                    Timestamp timestampStartDataOfBook = new java.sql.Timestamp(startDataOfBook.getTime());

                    if (Objects.requireNonNull(timestampEndDataOfBook).before(currentReservation.getStartOfBook())
                            || Objects.requireNonNull(timestampStartDataOfBook).after(currentReservation.getEndOfBook())) {
                    } else isAviable = false;

                    //availableCars.add(currentCar);
                    //availableCarsSet.add(currentCar);

                    // Caso in cui la prenotazione che vogliamo fare è dopo della prenotazione già fatta
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
}
