import { Component, OnInit } from "@angular/core";
import { simplifiedCar } from "../../utils/Models/simplifiedCar.model";
import { Car } from "../../utils/Models/car.model";
import { CarService } from "../../utils/Services/car.service";
import { User } from "../../utils/Models/user.model";
import { Router } from "@angular/router";

@Component({
  selector: "app-add-car",
  templateUrl: "./add-car.component.html",
  styleUrls: ["./add-car.component.scss"],
})


export class AddCarComponent implements OnInit {
  public localStorageUser: User;
  error: string = "";
  
  listOfCars: Car[]
  carToAdd:Car  //= this.localStorageUser.ownedCars[0]
  
  constructor(private carService: CarService, private router: Router) {} 

  ngOnInit(): void {
    this.carToAdd = new Car()
    this.localStorageUser = JSON.parse(localStorage.getItem("user") ?? "")
    this.carToAdd.user = this.localStorageUser
    //if(this.localStorageUser.ownedCars === undefined)
      //this.localStorageUser.ownedCars= []

    //this.listOfCars = this.localStorageUser.ownedCars!
  }

  addCar() {
    
    this.error = ''
    if ( this.carToAdd.availabilityPresent && this.carToAdd.plateNumber ) {
      // variabile temporanea utile a validare l'input dell'utente per la disponibilità della macchina
      let temp = JSON.stringify(this.carToAdd.availabilityPresent)

      if( temp.indexOf("true") != - 1 || temp.indexOf("false") != - 1 ){
        this.carService.addCar(this.carToAdd).subscribe(
          (resp) => {
            // se non faccio questo da errore
            let temp = JSON.stringify(resp)
            this.localStorageUser.ownedCars?.push(JSON.parse(temp))
            localStorage.setItem("user",JSON.stringify(this.localStorageUser))
            console.log("Car succesfully added");
				    this.router.navigateByUrl('dashboard');
            
          },
          (error) => {
            console.log("Errore :"+JSON.stringify(error))
            this.error = "Something went wrong during adding car";
          }
        );
      } else this.error = "Only true or false value accepted for avaiability field";

    } else this.error = "Fields plate number and avaiability are mandatory";
  }
}
