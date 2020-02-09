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
  errors: any;
  constructor(public fb: FormBuilder,
				public local: LocalStorageService,
				public authsService: AuthsService){
				this.createTable()
					if(this.local.get('userData1')){
						this.loginSuccess = true;
					}

					this.authsService.logoutEvent.subscribe(resp=>{
						this.loginSuccess = false;
					})
						
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
					if(resp['result'][0].userTypeId <= 11 || resp['result'][0].userTypeId == 12){
						this.loginSuccess= true;
						this.getSaveCustomer(resp['result'])

					}else{
						this.errors = 'you are not authorized Admin'
					}
					
				}else{
					this.loginSuccess = false;
					this.errors = resp['message']
				}
			})
			
	}
	getSaveCustomer(data){
		console.log("data value",data)
		// if(data != null){
		 
		  // this.session.set(this.KEY, data);
		  this.local.set('userData1',(data))
		  console.log(this.loginForm)
		  if(data[0].userTypeId == 1){
			let datas = {
				'superLogin': 'yes'
			}  
			this.local.set('userData2',(datas))
		  }
	}
	
}
