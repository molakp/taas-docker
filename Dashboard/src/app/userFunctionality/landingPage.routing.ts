import { Routes } from '@angular/router';
import { FullComponent } from '../layouts/full/full.component';
import { SigninComponent } from './signin/signin.component';
import { SignupComponent } from './signup/signup.component';
import { GoogleSigninComponent } from './google-signin/google-signin.component';
import { 
	AuthGuardService as AuthGuard 
  } from '../utils/Services/Guards/auth-guard.service';
import { DashboardComponent } from '../dashboard/dashboard.component';
import { UserSettingComponent } from './userSetting/userSetting.component';


export const LandingPage: Routes = [
	{	
		path: '',
		// In app-routing.module non mettendo niente nell'URL, vedo come comportarmi nel codice scritto qui, il che ci dice
		// che se anche qui non mettiamo niente nell'URL, istanziamo il componente SigninComponent e quindi ci carica il
		// codice HTML che abbiamo specificato in quel componente (file ==> signin.component.html).
		//component: SigninComponent,
		pathMatch: "full",
		redirectTo: 'signin',
		//children: []
	},
	{ 
		path: 'signup',
		component: SignupComponent
	},
	{
        canActivate: [AuthGuard], 
		path: 'welcomeHome',
		component: FullComponent,
		// cosi apre come pagina iniziale dashboard
		redirectTo: "/dashboard"
		//pathMatch: "full"
	},
	{	 
		path: 'signin',
		component: SigninComponent
	},
	//INIZIO per il login google
	/*{	
        path: 'login',
        //component: GoogleSigninComponent,
		children:[
			{
			  path: 'oauth2',
			  //component: GoogleSigninComponent,
			  children: [
				{
					path: ':other',
					component: GoogleSigninComponent
				}
				
			  ]
			}
		  ]
		  
      }*/
      //FINE per il login google
];
