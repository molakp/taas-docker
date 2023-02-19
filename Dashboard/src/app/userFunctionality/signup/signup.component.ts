import { Component, OnInit } from '@angular/core';
import { AuthService } from '../..//utils/Services/auth.service';
import {Request} from '../../utils/Models/request.model';


@Component({
selector: 'app-signup',
templateUrl: './signup.component.html',
styleUrls: ['./signup.component.css']
})
export class SignupComponent implements OnInit {

	constructor(private authService: AuthService) { }

	username: string = '';
	password: string = '';

	user_roles: any = [
		{name:'User', value:'USER', selected: false},
		{name:'Admin', value:'ADMIN', selected: false},
		{name:'Anonymous', value:'ANONYMOUS', selected: false},
	]

	//selectedRoles: string[] = [];
	selectedRoles:string="";

	error: string = '';
	success: string = '';

	ngOnInit(): void {
	}

	onChangeCategory(event: any, role: any) {
		// aggiungo solo se è vero e non è gia presente nella stringa
		/*if(role.selected == true && this.selectedRoles.indexOf(role.value) === -1)
			this.selectedRoles += role.value+","
		
		// Questo gestisce il caso in cui un utente cambia idea e toglie un permesso, quindi devo toglierlo dalla stringa
		if(role.selected == false && this.selectedRoles.indexOf(role.value) != -1)
			this.selectedRoles = this.selectedRoles.replace(role.value+",", "")	*/
		this.selectedRoles= role.value;
	}

	doSignup() {
		if(this.username !== '' && this.username !== null && this.password !== '' && this.password !== null && this.selectedRoles.length > 0) {
			// tolgo la virgola finale
			/*if(this.selectedRoles.charAt(this.selectedRoles.length-1) === ",")
				this.selectedRoles= this.selectedRoles.slice(0, -1)*/
			const request: Request = { username: this.username, password: this.password, roles: this.selectedRoles};

			this.authService.signup(request).subscribe((result)=> {
				//console.log(result);
				//this.success = 'Signup successful';
				this.success = result;
			}, (err) => {
				//console.log(err);
				this.error = 'Something went wrong during signup';
			});
		} else {
			this.error = 'All fields are mandatory';
		}
	}

}
