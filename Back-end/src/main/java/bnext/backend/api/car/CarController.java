package bnext.backend.api.car;

import bnext.backend.api.user.UserService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class CarController {


    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    //restituisce tutte le macchine che abbiamo in lista "cars"
    @GetMapping("/cars")
    public @NotNull List<Car> getAllCar() {
        return carService.getAllCars();
    }

    //restituisce una determinata macchina del cliente loggato
    @GetMapping("/cars/{carID}")
    public Car getCarById(@PathVariable @NotNull UUID carID) {
        return carService.getCarByID(carID);
    }

    //aggiungo una machina alla lista senza modificare quelle gia presenti
    // position sarà default vuota
    @RequestMapping(method = RequestMethod.POST, value = "/cars")
    public @NotNull ResponseEntity<Car> addCar(@RequestBody @NotNull Car car) {
        return carService.addCar(car);
    }

    //@RequestMapping(method = RequestMethod.PUT, value = "/cars")
    @PutMapping("/cars")
    public @NotNull ResponseEntity<String> updateCar(@RequestBody @NotNull Car car) {
        return carService.updateCar(car);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/cars/{carId}")
    public @NotNull String deleteCar(@PathVariable @NotNull UUID carId) {
        System.out.println("La macchina da conacellare è " + carId);
        return carService.deleteCar(carId);
    }

    /*
    @RequestMapping(method = RequestMethod.POST, value="cars/destination")
    public Reservation checkDestination(@RequestParam String carID){
                System.out.println("carID before is "+ carID);
                return carService.checkDestination(carID);
    }


    //Metodo che resituisce tutte le macchine messe in affitto dall'utente con Id specificato
    */
    @GetMapping("/cars/user={ownerId}")
    public @Nullable List<Car> getOwenedCars(@PathVariable String ownerId) {
        return carService.getOwenedCars(ownerId);
    }

    @GetMapping("/cars/battery/user={userId}")
    public @Nullable List<Car> getCarsWithLowBattery(@PathVariable @NotNull UUID userId) {
        return carService.getCarsWithLowBattery(userId);
    }


}
