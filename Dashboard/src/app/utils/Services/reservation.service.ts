import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Reservation } from '../Models/reservation';
import { environment } from '../../../environments/environment';
// questo è l'url di default del server locale 'http://localhost:8080'

@Injectable({
  providedIn: 'root'
})
export class ReservationService {
  private apiServerUrl = environment.apiBaseUrl;
  // Qua diciamo che è un client HTTP
  constructor(private http: HttpClient){}

  public getReservations(): Observable<Reservation[]> {
    //console.log(this.http.get<Reservation[]>(`${this.apiServerUrl}/reservations`), { responseType:'text' as 'json'});
    return this.http.get<Reservation[]>(`${this.apiServerUrl}/reservations`);
    
  }

  public getReservationsByCarOwnerID(carOwnerID?: string): Observable<Reservation[]> {
    //console.log(this.http.get<Reservation[]>(`${this.apiServerUrl}/reservations/car-owner-id=`), { responseType:'text' as 'json'});
    return this.http.get<Reservation[]>(`${this.apiServerUrl}/reservations/car-owner-id=${carOwnerID}`);
  }

  public getReservationsByID(reservationID?: string): Observable<Reservation[]> {
    //console.log(this.http.get<Reservation[]>(`${this.apiServerUrl}/reservations/get=`), { responseType:'text' as 'json'});
    return this.http.get<Reservation[]>(`${this.apiServerUrl}/reservations/get=${reservationID}`);
  }


  public addReservation(reservation: Reservation): Observable<string> {
    return this.http.post<string>(`${this.apiServerUrl}/reservations/add`, reservation) ;
  }

  public updateReservation(reservation: Reservation): Observable<Reservation> {
    return this.http.put<Reservation>(`${this.apiServerUrl}/reservations/update`, reservation);
  }

  public deleteReservation(reservationId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/reservations/delete=${reservationId}`);
  }


}