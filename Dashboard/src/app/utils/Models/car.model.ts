import { Feedback } from './feedback.model';
import { User } from './../Models/user.model';
import { Reservation } from './../Models/reservation';

export class Car {
    carId?: string;
    name?: string;
    carModel?: string;
    plateNumber?: string;
    battery?: number;
    priceHour?: number;
    priceKm?: number;
    availabilityPresent?: boolean;
    user: User;
    reservation: Reservation[];
    position: Object[];
    feedbacks?: Feedback[];
}