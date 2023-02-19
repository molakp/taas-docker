import { Component, OnInit } from "@angular/core";
import { Car } from "../../utils/Models/car.model";
import { User } from "../../utils/Models/user.model";
import { CarService } from "../../utils/Services/car.service";
import { debounceTime } from "rxjs/operators";
import { Subject } from "rxjs";
import { Router } from "@angular/router";
import { UserService } from "../../utils/Services/user.service";

@Component({
  selector: "app-manage-car",
  templateUrl: "./manage-car.component.html",
  styleUrls: ["./manage-car.component.scss"],
})
export class ManageCarComponent implements OnInit {
  // End the Closeable Alert
  // This is for the self closing alert
  private _success = new Subject<string>();
  staticAlertClosed = false;
  successMessage: string | null = null;

  // copia dell'utente corrente
  localStorageUser!: User;
  // macchina modificata
  modifiedCar: Car;
  // targa della macchina
  carToManagePlateNumber: string;
  // posizione nella lista della macchina modificata
  positionCar: number



  constructor(private carService: CarService, private router: Router, private userService: UserService) {}

  ngOnInit(): void {
    this.modifiedCar = new Car
    // recupero la targa della macchina da modificare
    this.carToManagePlateNumber = sessionStorage.getItem("carToManagePlateNumber")?? ''
    // recupero l'utente
    this.localStorageUser = JSON.parse(localStorage.getItem("user") ?? "")
  }


  updateCar(): void {
    //verifico di aver inserito almento un dato (aver fatto almeno una modifcia)
    if(this.modifiedCar != undefined){
      this.modifiedCar.plateNumber= this.carToManagePlateNumber
      if(this.localStorageUser.ownedCars){

        for(let i=0; i < this.localStorageUser.ownedCars.length; i++){

          // in questo caso ho trovato la macchina, quindi la aggiorno
          if(this.modifiedCar.plateNumber=== this.localStorageUser.ownedCars[i].plateNumber){
            this.localStorageUser.ownedCars[i].name = this.modifiedCar.name ?? this.localStorageUser.ownedCars[i].name
            this.localStorageUser.ownedCars[i].carModel = this.modifiedCar.carModel ?? this.localStorageUser.ownedCars[i].carModel
            this.localStorageUser.ownedCars[i].priceHour = this.modifiedCar.priceHour ?? this.localStorageUser.ownedCars[i].priceHour
            this.localStorageUser.ownedCars[i].priceKm = this.modifiedCar.priceKm ?? this.localStorageUser.ownedCars[i].priceKm
            this.localStorageUser.ownedCars[i].availabilityPresent = this.modifiedCar.availabilityPresent ?? this.localStorageUser.ownedCars[i].availabilityPresent
            this.localStorageUser.ownedCars[i].battery = this.modifiedCar.battery ?? this.localStorageUser.ownedCars[i].priceKm
            // e salvo l'indice
            this.positionCar=i
          }
        }

        // aggiorno la macchina nel local storage
        localStorage.setItem("user", JSON.stringify(this.localStorageUser))

        // quindi faccio la chiamata al server per la modifica delle macchine
        this.carService.updateCar(this.localStorageUser.ownedCars[this.positionCar]).subscribe(
          (error) => {
            console.log(JSON.stringify(error));
          },
          () => {
            console.log("Car succesfully updated");
            this.router.navigateByUrl("dashboard");
          }
        );
        sessionStorage.removeItem("carToManagePlateNumber");

      }else alert("local storage is null")
    } else alert("Not car found")


  }
}
