import { Component } from '@angular/core';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { AuthsService } from './services/auths.service';
import { LocalStorageService } from 'angular-web-storage';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'med-parliame';
  loginForm: FormGroup
  loginSuccess: boolean;
  showPasswords: boolean;
  loginMedPar: any;
  constructor(public fb: FormBuilder,
				public local: LocalStorageService,
				public authsService: AuthsService){
				this.createTable()
					if(this.local.get('userData1')){
						this.loginSuccess = true;
					}
						
  }
  createTable(){
		this.loginForm = this.fb.group({
			login: this.fb.group({
				email: ['',[Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/)]],
				password: ['',Validators.required],
				
      }),
    })
  }
	showPassword(id){
		if(this.showPasswords == true){
			this.showPasswords = false;
		}else{
			this.showPasswords = true;
		}
		
		
		// let passwor = this.loginForm.get('data').get('Password')
		// console.log(passwor)
		var x = document.getElementById(id);
		
		if (x['type'] === "password") {
			x['type'] = "text";
		} else {
			x['type'] = "password";
		}
	}
	getLogin(){
		let userData = this.loginForm.get('login').value;
			this.authsService.login(userData).subscribe(resp =>{

				if(resp['status'] == 'true'){
					this.loginSuccess= true;
					this.getSaveCustomer(resp['result'])
				}else{
					this.loginSuccess = false;
				}
			})
			
	}
	getSaveCustomer(data){
		console.log("data value",data)
		// if(data != null){
		 
		  // this.session.set(this.KEY, data);
		  this.local.set('userData1',(data))
	}
}
