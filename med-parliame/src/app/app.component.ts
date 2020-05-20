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
  forgetPasswords: boolean;
  otpshow: boolean;
  updatePassword: boolean;
  errorMessage: any;
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
			forgetPass: this.fb.group({
				email: ['',[Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/)]],
			})  ,
			otpForm : this.fb.group({
				otp1: [''],
				otp2: [''],
				otp3: [''],
				otp4: [''],
			}),
			update: this.fb.group({
				pass: [''],
				pass1:['']
			})
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
					if(resp['result'][0].userTypeId != 5  && resp['result'][0].userTypeId != 6 && resp['result'][0].userTypeId != 7 && resp['result'][0].userTypeId != 8 && resp['result'][0].userTypeId != 9 && resp['result'][0].userTypeId != 13 && (resp['result'][0].userTypeId <= 11 || resp['result'][0].userTypeId == 12) ){
						this.loginSuccess= true;
						this.getSaveCustomer(resp['result'])
						this.errors = '';

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

	getForget(){
		
		// if(this.loginForm.get('forgetpass').get('terms').value == false){
		// 	this.errors = 'Please select the terms and conditions'
		// }else{
			// let userData = this.loginForm.get('forgetPass').value;
			let data = {
				'email': this.loginForm.get('forgetPass').get('email').value
			}
			this.userService.dataPostApi(data,AppSettings.generateOtp).then(resp=>{
				if(resp && resp['status']=='true'){

					this.loginForm.get('otpForm').get('otp1').patchValue(resp['result'][0].otp[0]);
					this.loginForm.get('otpForm').get('otp2').patchValue(resp['result'][0].otp[1]);
					this.loginForm.get('otpForm').get('otp3').patchValue(resp['result'][0].otp[2]);
					this.loginForm.get('otpForm').get('otp4').patchValue(resp['result'][0].otp[3]);
					console.log(this.loginForm)
					this.otpshow= true;
				}
				
			})
			// this.authsService.login(userData).subscribe(resp =>{

			// 	if(resp['status'] == 'true'){
			// 		if(resp['result'][0].userTypeId != 5  && resp['result'][0].userTypeId != 6 && resp['result'][0].userTypeId != 7 && resp['result'][0].userTypeId != 8 && resp['result'][0].userTypeId != 9 && resp['result'][0].userTypeId != 13 && (resp['result'][0].userTypeId <= 11 || resp['result'][0].userTypeId == 12) ){
			// 			this.loginSuccess= true;
			// 			this.getSaveCustomer(resp['result'])
			// 			this.errors = '';

			// 		}else{
			// 			this.errors = 'you are not authorized Admin'
			// 		}
					
			// 	}else{
			// 		this.loginSuccess = false;
			// 		this.errors = resp['message']
			// 	}
			// })
		// }

		
			
	}

	veryfyOTP(){
		let inputData: string = '';
		let otp1 = this.loginForm.get('otpForm').get('otp1').value;
		let otp2 = this.loginForm.get('otpForm').get('otp2').value;
		let otp3 = this.loginForm.get('otpForm').get('otp3').value;
		let otp4 = this.loginForm.get('otpForm').get('otp4').value;
		
		 	inputData = otp1 + otp2 + otp3 + otp4
		
		let data = {
			'email': this.loginForm.get('forgetPass').get('email').value,
			'otp': inputData
		}
		this.userService.dataPostApi(data,AppSettings.verifyOtp).then(resp=>{
			if(resp && resp['status']=='true'){
				this.forgetPasswords=false;
				this.otpshow = false;
				this.updatePassword=true
			}
			
		})
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

	updatePasswords(){
		if(this.loginForm.get('update').get('pass').value != this.loginForm.get('update').get('pass1').value){
			this.errorMessage = 'Enter correct password'
			jQuery("#messagePopuupss").modal('show')
			setTimeout(()=>{
				// jQuery("#messagePopuupss").modal('hide')
			},2000)
		}else{
			let data ={
				email: this.loginForm.get('login').get('email').value,
				password: this.loginForm.get('update').get('pass').value
			}	
			this.userService.dataPostApi(data,AppSettings.updatePassword).then(resp=>{
				if(resp && resp['status']=='true'){
					this.errorMessage = 'Password update successfully'
					jQuery("#messagePopuupss").modal('show')
					setTimeout(()=>{
						jQuery("#messagePopuupss").modal('hide')
					},2000)

				}
			})
		}
	}

	forgetPass(boll){
		this.forgetPasswords=boll
	}
	
}
