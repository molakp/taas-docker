import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';


import { LandingPage } from './landingPage.routing';
import { CommonModule } from '@angular/common';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { UserSettingComponent } from './userSetting/userSetting.component';



@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(LandingPage),
    FormsModule,
    ReactiveFormsModule,
    NgbModule
  ],
  declarations: [
    UserSettingComponent
  ]
})
export class LandingModule { }
