import { Feedback } from './../Models/feedback.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
// questo è l'url di default del server locale 'http://localhost:8080'
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class FeedbackService {
  private apiServerUrl = environment.apiBaseUrl;
  // Qua diciamo che è un client HTTP
  constructor(private http: HttpClient){}

  public getAllFeedbacks(): Observable<Feedback[]> {
    return this.http.get<Feedback[]>(`${this.apiServerUrl}/feedbacks`);

  }
  public addFeedback(feedback: Feedback): Observable<string> {
    return this.http.post<string>(`${this.apiServerUrl}/feedbacks/addFeed`, feedback) ;
  }

  /*public updateFeedback(feedback: Feedback): Observable<Feedback> {
    return this.http.put<Feedback>(`${this.apiServerUrl}/feedbacks/update`, feedback);
  }

  public deleteUser(userId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/del=${userId}`);
  }*/


}
