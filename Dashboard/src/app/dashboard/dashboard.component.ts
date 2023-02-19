import { HttpErrorResponse } from '@angular/common/http';
import { Component, AfterViewInit, Input } from '@angular/core';
import { UserService } from '../utils/Services/user.service';
import { User } from '../utils/Models/user.model';
import { DataService } from '../layouts/data-service';
import { Subscription } from 'rxjs';
import {ReservationService} from '../utils/Services/reservation.service';
import {Reservation} from '../utils/Models/reservation';

//declare var require: any;



@Component({
  templateUrl: "./dashboard.component.html",
})
export class DashboardComponent implements AfterViewInit {
  subtitle: string;
  public user: User;
  public localStorageUser: User;
  public reservations: Reservation[];
  //@Input() username: string;

  message: string;
  subscription: Subscription;

  constructor(
    private reservationService: ReservationService,
    private data: DataService,
    private userService:UserService
  ) {
    this.subtitle = "This is some text within a card block.";
  }

  ngOnInit() {
     this.localStorageUser = JSON.parse(localStorage.getItem("user") || "{}");

        this.refreshUser();


    this.subscription = this.data.currentMessage.subscribe(
      (message) => (this.message = message)
    );
    this.subscription = this.data.currentUSer.subscribe(
      (user) => (this.user = user)
    );

   }

  ngAfterViewInit() {
    this.refreshUser();
    //console.log("Username is  " + this.username);
  }

  private refreshUser() {
    //updating user in localstorage
    this.userService
      .getUserByID(this.localStorageUser.userId!)
      .subscribe((result) => {
        this.localStorageUser = JSON.parse(result);
        //this.safeUserAssign(result,this.localStorageUser);
        localStorage.setItem("user", JSON.stringify(this.localStorageUser));

        // this.localStorageUser = JSON.parse(localStorage.getItem("user")!);

        //this.router.navigateByUrl("dashboard");
        // window.location.reload();
      });
  }
}
