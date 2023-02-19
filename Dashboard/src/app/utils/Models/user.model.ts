import { Car } from "./car.model";

export interface User {
    userId?: string;
    name?: string;
    surname?: string;
    birthDate?: string;
    username?: string;
    password?: string;
    active?: string;
    roles?: string;
    permissions?: string;
    ownedCars?: Car[];
    reservation?: Object[];
    feedbacks?:Object[];
    permissionList?:Object[];
    roleList?: Object[];
}