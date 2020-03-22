import { Component } from '@angular/core';
import { FormGroup, FormBuilder,Validators } from '@angular/forms';
import { AuthsService } from './services/auths.service';
import { LocalStorageService } from 'angular-web-storage';
import { ActivatedRoute } from '@angular/router';
import { UserServiceService } from './services/user-service.service';
import { AppSettings } from './utils/constant';
declare var jQuery: any;

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
  AccountVerification: boolean;
  userId: any;
  message: any;
  secondplit: any;
  emailVerified: boolean;
  constructor(public fb: FormBuilder,
				public userService: UserServiceService,
				public route: ActivatedRoute,
				public local: LocalStorageService,
				public authsService: AuthsService){

					console.log(window.location.href)
					this.AccountVerification = false;
					this.emailVerified =  true;
      let location = window.location.href
      location = location = location.substring(location.lastIndexOf("/") + 1, location.length );
      let secondLocation = location.substring(0, location.lastIndexOf("/") + 1)
      secondLocation = secondLocation.substring(secondLocation.lastIndexOf("/") + 1, secondLocation.length - 5 );
      console.log(location)
      let splitLocation = location.split('?')
      console.log(splitLocation[1])
      if( splitLocation[0] == 'AccountVerification'){
        // this.router.navigateByUrl('/AccountVerification')
		this.AccountVerification = true

		// this.route.queryParams.subscribe(params => {
		// 	this.userId = params['userId'];
			
			let secondSPlit = splitLocation[1].split('=')
			this.secondplit = secondSPlit[1]
			this.emailVerify()
			
			
			
		// })

	  }else{
		this.AccountVerification = false
		this.createTable()
		if(this.local.get('userData1')){
			this.loginSuccess = true;
		}

		this.authsService.logoutEvent.subscribe(resp=>{
			this.loginSuccess = false;
		})

	  }
				
					// jQuery('input[type="checkbox"]'). click(function(){
					// 	if(jQuery(this). prop("checked") == true){
					// 	alert("Checkbox is checked." );
					// 	}
					// 	else if(jQuery(this). prop("checked") == false){
					// 	alert("Checkbox is unchecked." );
					// 	}
					// })	
					
						
  }
  createTable(){
		this.loginForm = this.fb.group({
			login: this.fb.group({
				email: ['',[Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/)]],
				password: ['',Validators.required],
				terms: ['']
				
      }),
	})
	this.loginForm.get('login').get('terms').valueChanges.subscribe(value =>{
		console.log(value)
		if(value == true){
			this.errors = ''
		}
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
		
		if(this.loginForm.get('login').get('terms').value == false){
			this.errors = 'Please select the terms and conditions'
		}else{
			let userData = this.loginForm.get('login').value;
			this.authsService.login(userData).subscribe(resp =>{

				if(resp['status'] == 'true'){
					if(resp['result'][0].userTypeId != 5  && (resp['result'][0].userTypeId <= 11 || resp['result'][0].userTypeId == 12) ){
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

		
			
	}
	getSaveCustomer(data){
		console.log("data value",data)
		// if(data != null){
		 
		  // this.session.set(this.KEY, data);
		  this.local.set('userData1',(data))
		  console.log(this.loginForm)
		  if(data[0].userTypeId != 5){
			if(data[0].userTypeId == 1){
				let datas = {
					'superLogin': 'yes'
				}  
				this.local.set('userData2',(datas))
			  }
		  }
		  
	}
	logout(){
		this.authsService.logout()
	}
	closeModal(){
		jQuery("#logout-pop").modal('hide')
	}
	emailVerify(){
		let data = {
			'userId': this.secondplit
		}
		this.userService.getApiDataacountVerfication(AppSettings.AccountVerification,data).then(resp=>{
			console.log(resp)
			if(resp['status'] == 'true'){
				this.message = resp['content']
			}else{
				this.message = resp['content']
			}
			this.emailVerified = true;
		})
		
	}
	
}
