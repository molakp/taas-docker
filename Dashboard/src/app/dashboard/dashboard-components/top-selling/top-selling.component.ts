import { SimplifiedCar, TopSelling } from './top-selling-data';
import { Component, OnInit } from '@angular/core';
import { User } from './../../../../app/utils/Models/user.model';
import { Feedback } from '../../../utils/Models/feedback.model';
import { Reservation } from '../../../utils/Models/reservation';
import { ReservationService } from '../../../utils/Services/reservation.service';
import { HttpErrorResponse } from '@angular/common/http';
import { CarService } from '../../../utils/Services/car.service';
import { Car } from '../../../utils/Models/car.model';
import { Router } from '@angular/router';
import { UserService } from "../../../utils/Services/user.service";
@Component({
  selector: "app-top-selling",
  templateUrl: "./top-selling.component.html",
})
export class TopSellingComponent implements OnInit {

  owenedCarsCurrentUser: Car[];
  public localStorageUser: User;

  constructor(private router: Router, private carService: CarService, private userService: UserService) {}

  ngOnInit(): void {
    this.localStorageUser = JSON.parse(localStorage.getItem("user") || "{}");
    this.owenedCarsCurrentUser = this.localStorageUser!.ownedCars ?? [];
  }



  //metodo che gestisce i deleteCar
  deleteCar(carId: string): void {
    this.carService.deleteCar(carId).subscribe(
      (error) => {
        console.log(JSON.stringify(error));
      },
      () => {
        // elimino l'oggetto
        if(this.localStorageUser.ownedCars){
          var removeIndex = this.localStorageUser.ownedCars.map(item => item.carId).indexOf(carId);
          ~removeIndex && this.localStorageUser.ownedCars.splice(removeIndex, 1);
        }
        localStorage.setItem("user",JSON.stringify(this.localStorageUser))

        //this.localStorageUser.ownedCars.splice(carId, 1);

        console.log("Car succesfully deleted");

        //this.refreshUser();
      }
    );
  }



  manageCar(plateNumber: string) {
    sessionStorage.setItem("carToManagePlateNumber", plateNumber);
    this.router.navigateByUrl("/dashboard/manageCar");
  }

  addCar() {
    this.router.navigateByUrl("/dashboard/addCar");
  }
}
