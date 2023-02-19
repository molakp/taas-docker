import { User } from "./user.model";


export interface simplifiedCar {
    name?: string;
    carModel?: string;
    plateNumber?: string;
    priceHour?: number;
    priceKm?: number;
    availabilityPresent?: boolean;
    user?: User
}