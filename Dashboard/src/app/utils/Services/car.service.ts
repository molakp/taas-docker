import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { map } from "rxjs/operators";
import { Observable } from 'rxjs';
import { User } from '../Models/user.model';
import { Car } from '../Models/car.model';
// questo è l'url di default del server locale 'http://localhost:8080'
import { environment } from '../../../environments/environment';
import { simplifiedCar } from '../Models/simplifiedCar.model';

@Injectable({
  providedIn: 'root'
})
export class CarService {
  private apiServerUrl = environment.apiBaseUrl;
  // Qua diciamo che è un client HTTP
  constructor(private http: HttpClient){}

  public getCars(): Observable<Car[]> {
    return this.http.get<Car[]>(`${this.apiServerUrl}/car/all`);

  }
  public addCar(car: Car): Observable<string> {
    return this.http.post<string>(`${this.apiServerUrl}/car/addCar`, car).pipe(
      map((resp) => {
        return resp;
      })
    );
  }

  public updateCar(car: Car) {
    //console.log("ecco la macchina modificata "+JSON.stringify(car))
    return this.http.put<string>(`${this.apiServerUrl}/car/updateCar`, car).pipe(
      map((resp) => {
        //console.log("Ecco la risposta della delete " + resp);
        return resp;
      })
    );
  }

  public deleteCar(carId: string): Observable<void> {
    //console.log(`metodo chiamato da delete ${carId}`);
  //  console.log(`${this.apiServerUrl}/car/${carId}`);
    return this.http.delete<void>(`${this.apiServerUrl}/car/${carId}`);
  }

  public getCarsWithLowBattery(userId: string): Observable<Car[]> {
    console.log(`metodo chiamato da delete ${userId}`);
    return this.http.get<Car[]>(`${this.apiServerUrl}/car/${userId}`);
  }

}
