import { Component, OnInit } from '@angular/core';

import { User } from './utils/Models/user.model';

import { AuthService } from './utils/Services/auth.service';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
 
  constructor(public authService:AuthService){}
  
}
