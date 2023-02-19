import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from "@angular/common/http";
import { Observable } from 'rxjs';
import { User } from '../Models/user.model';
import { map } from "rxjs/operators";
// questo è l'url di default del server locale 'http://localhost:8080'
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: "root",
})
export class UserService {
  private apiServerUrl = environment.apiBaseUrl;
  // Qua diciamo che è un client HTTP
  constructor(private http: HttpClient) {}

  public getUsers(): Observable<User[]> {
    //console.log(this.http.get<User[]>(`${this.apiServerUrl}/user/allUsers`), { responseType:'text' as 'json'});
    return this.http.get<User[]>(`${this.apiServerUrl}/user/allUsers`);
  }
  public addUser(user: User): Observable<string> {
    return this.http.post<string>(`${this.apiServerUrl}/registration`, user);
  }

  
  public updateUser(user: User): Observable<User> {
    return this.http.put<User>(`${this.apiServerUrl}/user/update`, user);
  }

  public deleteUser(userId: string): Observable<void> {
    return this.http.delete<void>(`${this.apiServerUrl}/user/del=${userId}`);
  }

  public getUserByID(userId: string): Observable<string> {
    return this.http
      .get<string>(`${this.apiServerUrl}/user/id=${userId}`, {
        headers: new HttpHeaders({ "Content-Type": "application/json" }),
        responseType: "text" as "json",
      });
  }
}
