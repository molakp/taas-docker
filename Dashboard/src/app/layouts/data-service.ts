import { Injectable } from "@angular/core";
import { BehaviorSubject } from "rxjs/BehaviorSubject";

import {User} from '../utils/Models/user.model';

@Injectable({providedIn: 'root'})
export class DataService{
    private messageSource = new BehaviorSubject<string> ("message");
    currentMessage = this.messageSource.asObservable();

    private user = new BehaviorSubject<User>(null!);
    currentUSer = this.user.asObservable();
    constructor(){}

    changeMessage( message : string) {
        this.messageSource.next(message);
    }

    setUser( user : User) {
        this.user.next(user);
    }
}