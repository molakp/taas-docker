import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../utils/Services/auth.service';
import { NgZone } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { ImageService } from './ImageService';
// Inizio login social

//import { SocialAuthService } from "angularx-social-login";
//import { FacebookLoginProvider, GoogleLoginProvider } from "angularx-social-login";
import { ViewChild, ElementRef  } from '@angular/core';
import {Router} from "@angular/router";

// Fine login social

@Component({
  selector: 'app-google-signin',
  templateUrl: './google-signin.component.html',
  styleUrls: ['./google-signin.component.scss'],
  providers: [ImageService]
})
export class GoogleSigninComponent implements OnInit {
  title = 'loginGoogle';

  auth2: any;


  @ViewChild('loginRef', {static: true }) loginElement!: ElementRef;
  constructor(public authService:AuthService, private router: Router, private ngZone:NgZone,private imageService: ImageService){}



   /*------------------------------------------

  --------------------------------------------

  About

  --------------------------------------------

  --------------------------------------------*/

  ngOnInit() {

    this.googleAuthSDK();

  }

  /**

   * Write code on Method

   *

   * @return response()

   */

  callLoginButton() {

    this.auth2.attachClickHandler(this.loginElement.nativeElement, {},

      (googleAuthUser:any) => {

        let profile = googleAuthUser.getBasicProfile();

        console.log('Token || ' + googleAuthUser.getAuthResponse().id_token);

        console.log('ID: ' + profile.getId());
        localStorage.setItem('google-user-id',JSON.stringify(profile.getId()));

        console.log('Name: ' + profile.getName());
        localStorage.setItem('google-user-name',JSON.stringify(profile.getName()));

        console.log('Image URL: ' + profile.getImageUrl());

        console.log('Email: ' + profile.getEmail());
        localStorage.setItem('google-user-email',JSON.stringify( profile.getEmail()));
        // NON funziona per errore CORS, non vale la pena tentare di sistemarlo per ora (non è importante)
        this.imageService.downloadImage(profile.getImageUrl(), 'foto-profilo.png');


        this.ngZone.run(()=>this.navigateTo('/welcomeHome'));
       /* Write Your Code Here */

      }, (error:any) => {
        console.log("EROREEEEEEEEEEEEEEEEEEEEEEEE")
        console.log(error)
        alert(JSON.stringify(error, undefined, 2));

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

      var js, fjs = d.getElementsByTagName(s)[0];

      if (d.getElementById(id)) {return;}

      js = d.createElement('script');

      js.id = id;

      js.src = "https://apis.google.com/js/platform.js?onload=googleSDKLoaded";

      fjs?.parentNode?.insertBefore(js, fjs);

    }(document, 'script', 'google-jssdk'));

  }


}
