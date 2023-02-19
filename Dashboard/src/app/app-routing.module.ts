import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { FullComponent } from './layouts/full/full.component';
import { Page404Component } from './page404/page404.component';
import { UserSettingComponent } from './userFunctionality/userSetting/userSetting.component';
import {ManageCarComponent} from './dashboard/manage-car/manage-car.component'
import { 
  AuthGuardService as AuthGuard 
} from './utils/Services/Guards/auth-guard.service';
import { SignupComponent } from './userFunctionality/signup/signup.component';
import { AppComponent } from './app.component';

export const Approutes: Routes = [
  {
    path: '',
    children: [
      {
        canActivate: [AuthGuard], 
        path: 'dashboard',
        loadChildren: () => import('./dashboard/dashboard.module').then(m => m.DashboardModule),
        component: FullComponent
      },
      {
        canActivate: [AuthGuard], 
        path: 'about',
        loadChildren: () => import('./about/about.module').then(m => m.AboutModule),
        component: FullComponent
      },
      {
        canActivate: [AuthGuard], 
        path: 'component',
        loadChildren: () => import('./component/component.module').then(m => m.ComponentsModule),
        component: FullComponent
      },
      {	 
        canActivate: [AuthGuard], 
        path: 'userSetting',
        component: FullComponent,
        children:[
          {
            path: '',
            component: UserSettingComponent
          }
        ]
      },
      {
        // Se non metto nessun URL ne sopra ne qui vado a vedere nel figlio LandingModule dove andare
        path: '',
        loadChildren: () => import('./userFunctionality/landing.module').then(m => m.LandingModule),
      }
    ]
  },
  { path: '**',pathMatch: "full", component: Page404Component},
  
];
