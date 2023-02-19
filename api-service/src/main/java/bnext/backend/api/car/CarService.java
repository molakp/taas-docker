package bnext.backend.api.car;

import bnext.backend.api.reservation.ReservationRepository;
import bnext.backend.api.user.User;
import bnext.backend.api.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ReservationRepository reservationRepository;


    public @NotNull List<Car> getAllCars() {
        List<Car> cars = new ArrayList<>();
        carRepository.findAll().forEach(cars::add);
        if (cars.isEmpty())
            System.out.println("There's no car to show, the list is empty");
        return cars;
    }

    /* ******************************************************************************************************************************
         Da rivedere questo metodo quando abbiamo implementato la funzionalità di chi è loggato in una determinata sessione
    ****************************************************************************************************************************** */
    public Car getCarByID(@NotNull UUID id) {
        return carRepository.findById(id).orElse(null);
    }

    public @NotNull ResponseEntity<Car> addCar(@NotNull Car car) {
        // Se almeno una delle due espressioni è vera, infatti, se non ci sono macchine posso salvare direttamente la macchina,
        // non possono esserci doppioni.
        if (getAllCars().isEmpty() || carRepository.findByPlateNumber(car.getPlateNumber()) == null) {
            //System.out.println("Ecco la macchina "+car);
            carRepository.save(car);
            return new ResponseEntity<Car>(car, HttpStatus.OK);
        } else return new ResponseEntity<Car>((Car) null, HttpStatus.BAD_REQUEST);
    }


    public @NotNull ResponseEntity<String> updateCar(@NotNull Car car) {
        // verifico che esista la macchina con l'id indicato
        if (carRepository.findById(car.getCarId()).isEmpty())
            return new ResponseEntity<String>("No car with this id in the database", HttpStatus.OK);
            //return "No car with this id in the database";
        else {


            Car storedCar = carRepository.findById(car.getCarId()).get();
            ReflectionUtils.doWithFields(car.getClass(), field -> {
                        field.setAccessible(true);
                        // il controllo sulla lista evita di assegnare una nuova lista se quella passata è vuota
                        // Le liste sono gestite dopo
                        if (field.get(car) != null && !field.getName().equals("id") && !(field.get(car) instanceof List)) {


                            // Se il field non è null allora accedo ai field della macchina salvata
                            ReflectionUtils.doWithFields(storedCar.getClass(),
                                    old_field -> {
                                        old_field.setAccessible(true);
                                        // se è lo stesso campo allora lo aggiorno
                                        if (old_field.getName().equals(field.getName())) {
                                            //System.out.println("Updating field " + old_field.getName() + ": " + field.get(car));
                                            old_field.set(storedCar, field.get(car));

                                        }


                                    });

                        }

                        // gestione liste politica ADD ALL
                        /*
                        storedCar.getFeedback().addAll(car.getFeedback());
                        storedCar.getReservation().addAll(car.getReservation());
                        */

                    }
            );

            //System.out.println("Ecco la macchina alla fine " + storedCar);
            //questo metodo basta gia ad aggiornare la macchina
            carRepository.save(storedCar);
            //return "CAR SUCCESSFULLY UPDATED";
            return new ResponseEntity<String>("CAR SUCCESSFULLY UPDATED", HttpStatus.OK);

        }
    }

    public @NotNull String deleteCar(@NotNull UUID carId) {
        System.out.println("DELETE car SERVICE");
        // verifico che esista la macchina con l'id indicato
        if (carRepository.findById(carId).isEmpty())
            return "No car with this id in the database";

        carRepository.deleteById(carId);
        return " CAR SUCCESSFULLY DELETED";
    }
    /* ******************************************************************************************************************************
                                 Da rivedere questo metodo, non so cosa dovrebbe fare
    ****************************************************************************************************************************** */
    /*public Reservation checkDestination(String carID) {

        List<Reservation> reservations = new ArrayList<>();
        //selects only registration for that car
        reservationRepository.findAll().forEach(t -> {
            System.out.println("carID id "+ carID);
            System.out.println("t carID is "+ t.getCarId());
            System.out.println(t.getCarId().equals(carID));
            if (t.getCarId().equals(carID)) {
                reservations.add(t);
            }
        });
        System.out.println(reservations.toString());
        // ordina la lista in base alla data di prenotazione
        reservations.sort((d1,d2) -> d1.getReservationTime().compareTo(d2.getReservationTime()));
        if(!reservations.isEmpty())
            return reservations.get(0);
        else
            return new Reservation();

    }*/

    //Restituisco tutte le macchine messe in affitto dall'utente con Id specificato dal parametro del metodo
    public @Nullable ArrayList<Car> getOwenedCars(String ownerId) {
        User user = userService.getUserById(ownerId);
        // verifico che esista il cliente con l'id indicato
        if (user != null)
            return (ArrayList<Car>) new ArrayList(carRepository.findByUser(user));
        else {
            System.out.println("The user with id = " + ownerId + " does not exist");
            return null;
        }
    }

    public List<Car> getCarsWithLowBattery(UUID userId) {

        List<Car> carsToBeFiltered = new ArrayList<>();
        carRepository.findAll().forEach(carsToBeFiltered::add);

        //prendo tutte le auto di questo utente con il livello di batteria basso
        List<Car> carsWithLowBattery = new ArrayList<>();
        for (Car c : carsToBeFiltered) {
            if (c.getBattery() < 50 && c.getUser().getUserId().equals(userId)) {
                carsWithLowBattery.add(c);
            }
        }
        return carsWithLowBattery;
    }
}










/*
    public @NotNull String addCarByName(String name, String carModel,
                                        String extraData, Position position,
                                        Integer battery, Integer priceHour, Integer priceKm,
                                        Boolean availabilityPresent) {

        Car newCar = new Car();
        newCar.setName(name);
        newCar.setCarModel(carModel);
        newCar.setPlateNumber(extraData);
        newCar.setPosition(position);
        newCar.setBattery(battery);
        newCar.setPriceHour(priceHour);
        newCar.setPriceKm(priceKm);
        newCar.setAvailabilityPresent(availabilityPresent);


        carRepository.save(newCar);
        return "SUCCESFULLY ADDED";


    }


 */