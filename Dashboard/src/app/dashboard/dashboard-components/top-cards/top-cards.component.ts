import { CarService } from './../../../utils/Services/car.service';
import { Component, OnInit } from '@angular/core';
import {topcard,topcards} from './top-cards-data';
import { User } from './../../../../app/utils/Models/user.model';
import { Car } from './../../../../app/utils/Models/car.model';
import { Feedback } from '../../../utils/Models/feedback.model';
import {Reservation} from '../../../utils/Models/reservation';
import {ReservationService} from '../../../utils/Services/reservation.service';
import { HttpErrorResponse } from '@angular/common/http';
import { noop } from 'rxjs';
import { TOUCH_BUFFER_MS } from '@angular/cdk/a11y/input-modality/input-modality-detector';
import { Router } from '@angular/router';


@Component({
  selector: 'app-top-cards',
  templateUrl: './top-cards.component.html'
})
export class TopCardsComponent implements OnInit {
  public localUser: User;
  public localUserId: string;
  public localFeedback: Feedback [];
  public count=0;
  public countWithLowBattery=0;


  topcards:topcard[];
  public  localStorageUser: User;

  reservations : Reservation[];
  reservationsByCarOwnerID : Reservation[];
  constructor(private CarService: CarService,private reservationService: ReservationService,private router: Router) {
    //this.topcards=topcards;
  }


  ngOnInit(): void {
    this.reservations = JSON.parse(
      localStorage.getItem("reservations") || "{}"
    );
    this.localStorageUser = JSON.parse(localStorage.getItem("user")!);

    if (this.localStorageUser == null || this.localStorageUser == undefined) {
      console.log("PROBLEMS WITH LOCALStorageUSER");
    }
    //processo i feedbacks

    this.localUser = JSON.parse(localStorage.getItem("user") || "{}");
    this.localUserId = this.localUser.userId || "{}";
    this.localFeedback = this.localUser.feedbacks || [];

   /* this.reservationsByCarOwnerID = JSON.parse(
      localStorage.getItem("reservationsByCarOwnerID") || "{}"
    );
    let totalReservations = 0;
    this.localUser.ownedCars?.forEach(car => {
        totalReservations+=car.reservation.length
      }
    )*/
    // processo le reservations
    let reservationTopCard = {
      bgcolor: "warning",
      icon: "bi bi-check2-all",
      // se this.reservations!.length.toString() è null o undefined viene assegnato la stringa VUOTA
      title:this.getReservationsByCarOwnerID().toString(),
      subtitle: "Reservations",
    };



    let feedbackTopCard = {
      bgcolor: "info",
      icon: "bi bi-emoji-smile",
      // se this.reservations!.length.toString() è null o undefined viene assegnato la stringa VUOTA
      title: this.localFeedback!.length.toString() ?? "",
      subtitle: "Feedbacks leaved",
    };



    //processo avaiable cars
    let avaiableCarsCount = 0;
    this.localStorageUser.ownedCars?.forEach((car) => {
      if (car.availabilityPresent) {
        avaiableCarsCount++;
      }
    });

    let avaiableCarsCard = {
      bgcolor: "success",
      icon: "bi bi-grid-fill",
      title: avaiableCarsCount.toString(),
      subtitle: "Avaiable Cars",
    };

    this.countWithLowBattery = 0;
    this.localStorageUser.ownedCars?.forEach((car) => {
      if (car.battery! < 30) {
        this.countWithLowBattery++;
      }
    });

    //Processo cars with low battery
    let carsWithLowBattery = {
      bgcolor: "danger",
      icon: "bi bi-lightning-charge-fill",
      title: this.countWithLowBattery.toString(),
      subtitle: "Cars with low battery",
    };

    // aggiungo la topcard appena creata all'elenco delle topcards
    this.topcards = [];

    //this.topcards.indexOf(reservationTopCard) === -1 ? this.topcards.push(reservationTopCard) : noop();
    //this.topcards.indexOf(feedbackTopCard) === -1 ? this.topcards.push(feedbackTopCard) : noop();

    this.topcards.push(
      avaiableCarsCard,
      carsWithLowBattery,
      feedbackTopCard,
      reservationTopCard
    );
    //this.topcards.push(reservationTopCard);
  }



  public getReservationsByCarOwnerID(): number {

    /*this.reservationService.getReservationsByCarOwnerID(this.localStorageUser.userId).subscribe(
      (response: Reservation[]) => {
        this.reservations = response;
        localStorage.setItem("reservationsByCarOwnerID", JSON.stringify(response));
      },
      (error: HttpErrorResponse) => {
        alert(error.message);
      }
    );*/
    let totalReservations = 0;
    this.localUser!.ownedCars?.forEach(car => {
        console.log("Car of User" + car)
        totalReservations += car.reservation.length
      }
    )
    return totalReservations
  }


  public getFeedbacksByCarOwnerID(): number {

      /*this.reservationService.getReservationsByCarOwnerID(this.localStorageUser.userId).subscribe(
        (response: Reservation[]) => {
          this.reservations = response;
          localStorage.setItem("reservationsByCarOwnerID", JSON.stringify(response));
        },
        (error: HttpErrorResponse) => {
          alert(error.message);
        }
      );*/
      let totalFeedbacks = 0;
      this.localUser!.ownedCars?.forEach(car => {
          console.log("Car of User"+car)
         // totalFeedbacks+=car.feedbacks?.length
        }
      )
      return totalFeedbacks


    //console.log("Ecco user in NAV COMPONENT, questi sono i feedbacks "+JSON.stringify(this.localFeedback))
  }

  public getCarsWithLowBattery(localUserId: string){

    console.log(JSON.stringify("sono entrato nel metodo getCarsWithLowBattery"))
    alert("entrato");

    this.CarService.getCarsWithLowBattery(localUserId).subscribe(
			(error) =>{
				console.log(JSON.stringify(error))
			},
			() => {
        console.log("queste solo le auto scariche");
//				this.router.navigateByUrl('dashboard');
      });
  }



}
