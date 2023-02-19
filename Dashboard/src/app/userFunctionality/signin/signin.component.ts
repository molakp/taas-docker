import { Component, OnInit,ViewChild, ElementRef,NgZone, OnChanges, SimpleChanges } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { AuthService } from '../../utils/Services/auth.service';
import {Request} from '../../utils/Models/request.model';
// queste due righe sono utili a gestire l'UUID
//import { v5 as uuidv5 } from 'hash-uuid';
//import { v4 as uuid } from 'uuid';
import * as uuid from 'uuid-by-string';
//import * as uuid from 'uuid';
//import { createHash } from 'crypto';
//import * as crypto from 'crypto';

import { ImageService } from '../google-signin/ImageService';

@Component({
selector: 'app-signin',
templateUrl: './signin.component.html',
styleUrls: ['./signin.component.css'],
providers: [ImageService]
})
export class SigninComponent implements OnInit {
	
	name: string = '';
	surname: string = '';
	username: string = '';
	password : string = '';
	isSignedin = false;
	loginGoogleClicked = false;
	error: string = '';

	auth2: any;
  	@ViewChild('loginRef', {static: true }) loginElement!: ElementRef;

	constructor(private route: ActivatedRoute, private router: Router, private authService: AuthService, private ngZone:NgZone,private imageService: ImageService) {}

	ngOnInit() {
		console.log("ngOnInit")
		this.isSignedin = this.authService.isUserSignedin();

		if(this.isSignedin) {
			this.router.navigateByUrl('welcomeHome');
		}
		// google Login
		this.googleAuthSDK();
	}


	

	doSignin() {
		if(!this.loginGoogleClicked){
			if(this.username !== '' && this.username !== null && this.password !== '' && this.password !== null) {
				this.signin()
			} else {
				this.error = 'Invalid Credentials';
			}
		}
	}
	loginGoogle(){
		this.loginGoogleClicked=true	
	}

	simpleLogin(){
		this.loginGoogleClicked=false
	}


	signin(){
		const request: Request = { username: this.username, password: this.password};
				//console.log(request)
				// per stamapre l'oggetto con tutti i campi
				this.authService.signin(request).subscribe((result)=> {
					this.router.navigateByUrl('welcomeHome');
					//console.log("Ecco result "+JSON.stringify(result))
					localStorage.setItem('token', 'Bearer ' + result.token);
					localStorage.setItem("user", JSON.stringify(result.user))
					//console.log("Ecco local storage "+JSON.stringify(localStorage.getItem("user")))
				}, (error:any) => {
					console.log(error)
					this.error = 'Either invalid credentials or something went wrong';
				});
	}

	signUp_or_signIn_Google(){
		
		this.authService.getUserByUsername(this.username).subscribe((result)=> {
			// se non termino con errore, l'utente esiste, quindi posso procedere con il login
			if(result != null){
				console.log("HO TROVATO L'UTENTE")
				//Faccio il login, in questo modo altrimenti da errore
				this.ngZone.run(() => {
					this.signin()
				  });
				
				
			}else{
				// la richiesta ha restituito null, l'utente non è stato trovato, quindi bisogna registrarlo
				const request: Request = {name: this.name, surname:this.surname, username: this.username, password: this.password, roles: "USER"};
				console.log("REGISTRO L'UTENTE\n"+JSON.stringify(request))
				this.authService.signup(request).subscribe((result)=> {

					console.log(this.username+": "+result)
					//Faccio il login, in questo modo altrimenti da errore
					this.ngZone.run(() => {
						this.signin()
					  });

				}, (error:any) => {
					console.log(error)
					this.error = 'Failed to signUp user '+ this.username;
				});
			}
				
		}, (error:any) => {
			console.log(error)
			this.error = 'Failed to find user '+ this.username;
		});

	}


	// ***************          Codice per il  login con GOOGLE          *********************** 
	


	callLoginButton() {
		console.log("Esecuzione callLoginButton")
		this.auth2.attachClickHandler(this.loginElement.nativeElement, {},
	
		  (googleAuthUser:any) => {
			
			console.log("Esecuzione googleAuthUser")
			let profile = googleAuthUser.getBasicProfile();
	
			//console.log('Token || ' + googleAuthUser.getAuthResponse().id_token);
	
			console.log('ID: ' + profile.getId());
			localStorage.setItem('google-user-id',JSON.stringify(profile.getId()));
	
			console.log('Name: ' + profile.getName());
			localStorage.setItem('google-user-name',JSON.stringify(profile.getName()));
	
			//console.log('Image URL: ' + profile.getImageUrl());
	
			console.log('Email: ' + profile.getEmail());
			localStorage.setItem('google-user-email',JSON.stringify( profile.getEmail()));
			// NON funziona per errore CORS, non vale la pena tentare di sistemarlo per ora (non è importante)
			//this.imageService.downloadImage(profile.getImageUrl(), 'foto-profilo.png');
	
	
			//this.ngZone.run(()=>this.navigateTo('/welcomeHome'));
			//const getUuid = require('uuid-by-string');
			// genero un numero casuale tra 1 e 1000
			//let randomNumber =  Math.floor(Math.random() * (1000 - 1 + 1)) + 1;
			//setto opportunamente l'username del profilo google togliendo lo spazio vuoto dal nome e cognome
			//this.username = (profile.getName()).replace(" ","_")+ randomNumber;
			this.username = profile.getEmail() 
			this.name = profile.getName().split(" ")[0]
			this.surname = profile.getName().split(" ")[1]
			this.password = profile.getId();
			
			
			//console.log("Ecco UUID: "+uuid(profile.getId()))
			//this.signUp_or_signIn_Google(uuid(profile.getId()))
			this.signUp_or_signIn_Google()

		   /* Write Your Code Here */
	
		  }, (error:any) => {
			//console.log("EROREEEEEEEEEEEEEEEEEEEEEEEE")
			console.log(error)
			//alert(JSON.stringify(error, undefined, 2));
	
		  });
	
	  }
	  navigateTo(url: string){
	
		this.router.navigate([url]);
	
	  }
	
	  /**
	
	   * Write code on Method
	
	   *
	
	   * @return response()
	
	   */
	
	  googleAuthSDK() {
	
		(<any>window)['googleSDKLoaded'] = () => {
	
		  (<any>window)['gapi'].load('auth2', () => {
	
			this.auth2 = (<any>window)['gapi'].auth2.init({
	
			  client_id: '1084541111327-fuhvkor571fsm9hapkuts90dd3oubu5i.apps.googleusercontent.com',
	
			  plugin_name: "chat",
	
			  cookiepolicy: 'single_host_origin',
	
			  scope: 'profile email'
	
			});
	
			this.callLoginButton();
			console.log("Ha finito il metodo callLoginButton")
		  });
	
		}
	
		(function(d, s, id){
			console.log("Ecco la funzione")
		  var js, fjs = d.getElementsByTagName(s)[0];
	
		  if (d.getElementById(id)) {return;}
	
		  js = d.createElement('script');
	
		  js.id = id;
	
		  js.src = "https://apis.google.com/js/platform.js?onload=googleSDKLoaded";
	
		  fjs?.parentNode?.insertBefore(js, fjs);
	
		}(document, 'script', 'google-jssdk'));
	
	  }


	// ***************          Fine codice per il  login con GOOGLE          *********************** 
}
