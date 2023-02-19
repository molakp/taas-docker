import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../utils/Services/auth.service';
import { User } from '../../utils/Models/user.model';
import { Router } from '@angular/router';
import { modifiedUser } from '../../utils/Models/modifiedUser.model';


@Component({
selector: 'app-userSetting',
templateUrl: './userSetting.component.html',
styleUrls: ['./userSetting.component.css']
})
export class UserSettingComponent {

	user: User
	error: string = '';
	fieldModifiedUser: modifiedUser = {}

	constructor(private authService: AuthService, private router: Router) {
		// prendo l'utente corrente
		this.user= JSON.parse(localStorage.getItem("user")|| "{}")
		// elimino i minuti e le ore, trattandosi di data di nascita infatti non servono, quindi aggiorno la data
		this.user.birthDate = this.user.birthDate?.slice(0,10) || '';
		// memorizzo l'utente aggiornato in local storage
		localStorage.setItem("user",JSON.stringify(this.user))

		this.fieldModifiedUser.userId= this.user.userId
		this.fieldModifiedUser.username = this.user.username?? undefined
		this.fieldModifiedUser.birthDate= this.user.birthDate?? undefined
		this.fieldModifiedUser.name= this.user.name?? undefined
		this.fieldModifiedUser.surname= this.user.surname?? undefined


	}

	editUser(click:string){

		//console.log("Ecco modified data "+JSON.stringify(this.fieldModifiedUser))
		/*console.log("Ecco user(prima) "+JSON.stringify(this.user))
		this.user.username= this.modifiedData["username"]
		this.user.birthDate= this.modifiedData["birth"]
		this.user.name= this.modifiedData["name"]
		this.user.surname= this.modifiedData["surname"]
		console.log("Ecco user(dopo) "+JSON.stringify(this.user))*/

		if(click === 'delete'){
			this.authService.delteUser(this.user.userId??'').subscribe(
			(error) =>{
				console.log(JSON.stringify(error))
			},
			() => {
				// in questo caso non ci sono errori quindi avendo eliminato l'utente ritorno al signin
				alert("User succesfully deleted")
				this.router.navigateByUrl('signin');
				localStorage.clear();
			});
			console.log("User succesfully deleted");
		}else{
			//console.log("Ecco l'utente modificato "+JSON.stringify(this.fieldModifiedUser))
			// se non stato cambiato l'username
			/*if(this.usernameChanged){
				this.usernameChanged = false
			}*/

			this.authService.updateUser(this.fieldModifiedUser).subscribe(
				(error) =>{
					console.log(JSON.stringify(error))
				},
				() => {
					//aggiorno l'utente
					this.user.username= this.fieldModifiedUser.username
					this.user.birthDate= this.fieldModifiedUser.birthDate
					if(this.fieldModifiedUser.name != '')this.user.name= this.fieldModifiedUser.name
					else this.user.name= undefined
					this.user.surname= this.fieldModifiedUser.surname
					//console.log("prova data "+new Date(this.fieldModifiedUser.birth||''))
					localStorage.setItem("user",JSON.stringify(this.user));
					//window.location.reload()
    				this.router.navigateByUrl("/dashboard");
					console.log("User succesfully updated");
				});
		}
	}
}
