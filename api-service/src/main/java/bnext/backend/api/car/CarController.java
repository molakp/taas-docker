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
@RequestMapping("car")
public class CarController {


    @Autowired
    private CarService carService;

    @Autowired
    private UserService userService;

    //restituisce tutte le macchine che abbiamo in lista "cars"
    @GetMapping("/all")
    public @NotNull List<Car> getAllCar() {
        return carService.getAllCars();
    }

    //restituisce una determinata macchina del cliente loggato
    @GetMapping("/{carID}")
    public Car getCarById(@PathVariable @NotNull UUID carID) {
        return carService.getCarByID(carID);
    }

    //aggiungo una machina alla lista senza modificare quelle gia presenti
    // position sarà default vuota
    @PostMapping("/addCar")
    public @NotNull ResponseEntity<Car> addCar(@RequestBody @NotNull Car car) {
        return carService.addCar(car);
    }

    //@RequestMapping(method = RequestMethod.PUT, value = "/cars")
    @PutMapping("/updateCar")
    public @NotNull ResponseEntity<String> updateCar(@RequestBody @NotNull Car car) {
        return carService.updateCar(car);
    }


    @DeleteMapping("/{carId}")
    public @NotNull String deleteCar(@PathVariable @NotNull UUID carId) {
        System.out.println("La macchina da cancellare è " + carId);
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
    @GetMapping("/user={ownerId}")
    public @Nullable List<Car> getOwenedCars(@PathVariable String ownerId) {
        return carService.getOwenedCars(ownerId);
    }

    @GetMapping("/battery/user={userId}")
    public @Nullable List<Car> getCarsWithLowBattery(@PathVariable @NotNull UUID userId) {
        return carService.getCarsWithLowBattery(userId);
    }


}
