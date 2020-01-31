import { Component, OnInit, Inject } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
// import { AuthsService } from '../../services/auths.service';
// import { UserService } from '../../services/user.service';
// import { ICountry } from '../../services/interfaces';
// import { CustomValidator } from '../../validator/custom-validator';
import { DeviceDetectorService } from 'ngx-device-detector';
// import { LOCAL_STORAGE, WebStorageService } from 'ngx-webstorage-service';
// import { AuthService, FacebookLoginProvider, SocialUser, GoogleLoginProvider } from 'angularx-social-login';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from '../services/user-service.service';
import { AuthsService } from '../services/auths.service';

declare var jQuery:any;

@Component({
  selector: 'app-user-signin',
  templateUrl: './user-signin.component.html',
  styleUrls: ['./user-signin.component.css']
})
export class UserSigninComponent implements OnInit {
	signIn: number;
	loginForm: FormGroup;
	userData: any;
	// country: ICountry[];
	forumsDetails = [];
	deviceInfo = null;
	deviceType: any;
	public data:any;
	forgetPassword: number;
	otpMessage: number;
	verifyotp: number;
	mobileNumber: any;
	userId: any;
	emailFirst: any;
	emailLast: any;
	mobileFirst: any;
	mobilelast: any;
	// user: SocialUser;
	errorMessagesShow: number;
	errorMessage: any;
	refferid: any;
	showPasswords: boolean;
	showConfPasswords: boolean;
	loginMedPar: any;


	
	constructor(private fb: FormBuilder,
				public userService: UserServiceService,
				private route: ActivatedRoute, private router: Router,
				private authsService: AuthsService,
				// private authService: AuthService,
				// @Inject(LOCAL_STORAGE) private storage: WebStorageService,
				private deviceService: DeviceDetectorService,
				// public 	customValidation: CustomValidator
				) { 
					this.signIn = 0;
					this.forgetPassword = 0;
					this.otpMessage = 0;
					this.verifyotp = 0;
					this.emailFirst= '0';
					this.emailLast= '0';
					this.mobileFirst= '0';
					this.mobilelast= '0';
					this.errorMessagesShow = 0;
					this.showPasswords = true;
					this.showConfPasswords = true;
					this.epicFunction();
					this.createTable();
	}

	ngOnInit() {
		this.route.queryParams.subscribe(params => {
			this.refferid = params['refferid'];
			if(this.refferid){
				this.loginForm.get('data').get('ReferralCode').setValue(this.refferid)
			}
			
		})	
console.log(this.refferid)

		
		// this.userService.getCountry().then(resp =>{
		// 	if(resp){
		// 		let response;
		// 		response = resp;
		// 		if(response && response.status ){
		// 			this.country = response.result
		// 		}
	  	// 	}
		// });

		

		  this.loginForm.get('data').get('refferid').setValue(this.refferid)
		  console.log(this.loginForm.get('data').value)
		
	}

  	createTable(){
		this.loginForm = this.fb.group({
			login: this.fb.group({
				email: ['',[Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/)]],
				password: ['',Validators.required],
				refferid: [this.refferid]
			}),
			data: this.fb.group({
				userName: ['',Validators.required],
				mobileNo: ['',[Validators.required, Validators.pattern(/^[0-9]{10,10}$/)]],
				email: ['',[Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/)]],
				password: ['',Validators.required],
				ConfirmPassword: ['',[Validators.required]],
				gender: ['0'],
				country: ['0'],
				city: ['0'],
				ReferralCode: [''],
				Key: ['V'],
				LoginType: [''],
				DeviceId:[''],
				deviceType:[this.deviceType],
				os: [this.deviceInfo.os],
				OsVersion: [this.deviceInfo.os_version],
				Token: [''],
				refferid: [this.refferid],
				pointCategoryId: [''],
				pointSubCategoryId: [''],
				pointActivityId: ['']
			}, {validator: this.checkPassword }),
			otpForm : this.fb.group({
				otp1: [''],
				otp2: [''],
				otp3: [''],
				otp4: [''],
				otp5: [''],
				otp6: [''],
			}),
			forgetPassword: this.fb.group({
				Email: ['',[Validators.required, Validators.pattern(/^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,4}$/)]	]
			}),
			phoneVerification : this.fb.group({
     
				MobileNo: [''],
				UserId: [''],
				Key: ['V']
				
			  })
			
			
		});
		
		this.loginForm.get('login').enable();
		this.loginForm.get('data').disable();
		this.loginForm.get('otpForm').disable();
		this.loginForm.get('forgetPassword').disable(); 
		
		
	}

	getLogin($event){
		this.errorMessagesShow = 0;
		this.loginForm.get('data').disable();
		this.loginForm.get('otpForm').disable();
		this.loginForm.get('forgetPassword').disable(); 
		
		if(this.loginForm.valid){
			console.log(this.loginForm)
			this.userData = this.loginForm.get('login').value;
			this.loginMedPar = this.authsService.login(this.userData)
			if(this.loginMedPar == 'true'){
				console.log(this.loginMedPar)
				this.router.navigateByUrl('/dashboard')
				this.saveInLocal(1,this.userData);
			}
			
		}else{
		  const controls = this.loginForm.controls;
		
						Object.keys(controls).forEach(controlName => {
							console.log(controls)
							controls[controlName].markAllAsTouched();
						});
						console.log(this.loginForm)
						return false;
		}
	} 

	saveInLocal(key, resp) {
      
      console.log("save in local ",key , resp)
      
		// this.storage.set(key, resp);
		
		// this.data= this.storage.get(key);
		
		// this.userService.getSaveCustomer(this.data);
	   }

	signUpModal(){
		this.showPasswords = true;
		this.showConfPasswords = true;
		this.errorMessagesShow = 0;
		this.signIn = 1;
		this.loginForm.reset();
		this.loginForm.get('data').get('Country').setValue(0);
      	this.loginForm.get('data').get('Gender').setValue(0);
		this.loginForm.get('otpForm').enable();
		this.loginForm.get('data').enable();
		this.loginForm.get('login').disable();
		this.loginForm.get('forgetPassword').disable();
		if(this.refferid){
			this.loginForm.get('data').get('ReferralCode').setValue(this.refferid)
			this.loginForm.get('data').get('ReferralCode').disable();
		}	
		
	}

	signInModal(){
		this.showPasswords = true;
		this.showConfPasswords = true;
		this.errorMessagesShow = 0;
		this.signIn = 0;
		this.loginForm.reset();
		this.loginForm.get('login').enable();
		this.loginForm.get('data').disable();
		this.loginForm.get('otpForm').disable();
		this.loginForm.get('forgetPassword').disable();
	}

	epicFunction() {
		this.deviceInfo = this.deviceService.getDeviceInfo();
		const isMobile = this.deviceService.isMobile();
		const isTablet = this.deviceService.isTablet();
		const isDesktopDevice = this.deviceService.isDesktop();
		this.deviceType = isMobile ? 'isMobile' : isTablet ? 'isTablet' :  isDesktopDevice ? 'isDesktopDevice' : "Not a Device";
		console.log(this.deviceInfo)
	}

	checkPassword(group: FormGroup){
		// this.errorMessagesShow = 0;
		if(group){
			if(group.get('ConfirmPassword').value){
				let pass = group.get('Password').value;
				let confirmPass = group.get('ConfirmPassword').value;
				return pass === confirmPass ? null : { notSame: true }
			}
		}
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

	showPasswordss(id){
		if(this.showConfPasswords == true){
			this.showConfPasswords = false;
		}else{
			this.showConfPasswords = true;
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

	signUp(){
		this.errorMessagesShow = 0;
		this.loginForm.get('otpForm').enable();
		this.loginForm.get('data').enable();
		this.loginForm.get('forgetPassword').disable();
		this.loginForm.get('login').disable();
		if(this.refferid){
			this.loginForm.get('data').get('ReferralCode').setValue(this.refferid)
			this.loginForm.get('data').get('pointCategoryId').setValue(2)
			this.loginForm.get('data').get('pointSubCategoryId').setValue(4)
			this.loginForm.get('data').get('pointActivityId').setValue(4)

		}else{
			if(this.loginForm.get('data').get('ReferralCode').value){
				this.loginForm.get('data').get('pointCategoryId').setValue(2)
				this.loginForm.get('data').get('pointSubCategoryId').setValue(4)
				this.loginForm.get('data').get('pointActivityId').setValue(4)	
			}else{
				this.loginForm.get('data').get('ReferralCode').setValue('')
				this.loginForm.get('data').get('pointCategoryId').setValue('')
				this.loginForm.get('data').get('pointSubCategoryId').setValue('')
				this.loginForm.get('data').get('pointActivityId').setValue('')
			}
			
		}
		
		if(this.loginForm.valid){
			const data = new FormData();
			data.append('ProfilePic', '');
			data.append('data', JSON.stringify(this.loginForm.get('data').value));
			console.log(this.loginForm.get('data').value)
			// this.userService.getSignUp(data).then(resp => {
			// 	if(resp){
			// 		let response;
			// 		response = resp;
			// 		if(response.status == 'true'){
			// 			this.userData =  this.loginForm.get('data').value
			// 			this.authsService.login(this.userData).subscribe(resp => {

			// 				if(resp){
			// 					let response
			// 					response = resp;
			// 					if(response.status =='true'){
			// 						response.result.forEach(element => {
			// 							this.saveInLocal(1,element);
			// 						});
			// 					}
			// 				}

			// 			})
					
			// 		this.verifyotp = 2;
					
			// 		this.mobileNumber = this.loginForm.get('data').get('MobileNo').value;
					
			// 		response.result.forEach(resp =>{
			// 			this.userId = resp.UserId
			// 		})
			// 		console.log(this.mobileNumber[0], this.mobileNumber[1])
			// 		this.mobileFirst = this.mobileNumber[0];
			// 		this.mobilelast = this.mobileNumber[7] + this.mobileNumber[8] + this.mobileNumber[9]
			// 		console.log(this.mobilelast)
			// 		this.sms();
			// 		this.loginForm.reset();
					
			// 		}else{
			// 			this.errorMessagesShow = 1;
			// 			this.errorMessage = response.message
			// 		// this.verifyotp = 3;
			// 		// this.phoneVerify.emit(this.verifyotp);
			
			// 		}
			
			// 		// console.log(resp)
			// 	}
			// })
	
		}else{
			const controls = this.loginForm.controls;
			Object.keys(controls).forEach(controlName => {
				console.log(controls)
				controls[controlName].markAllAsTouched();
			});
			console.log(this.loginForm)
			return false;
		}
	} 

	backtoSignIn(){
		// this.errorMessagesShow = 0;
		this.loginForm.get('forgetPassword').disable();
		this.loginForm.get('otpForm').disable();
		this.forgetPassword = 0;
		this.errorMessagesShow = 0;
	}

	forgetPass(){
		this.errorMessagesShow = 0;
		this.loginForm.get('forgetPassword').enable();
		this.loginForm.get('forgetPassword').reset();
		this.loginForm.get('forgetPassword').enable();
		this.loginForm.get('otpForm').enable();
		this.forgetPassword = 1;
	}

	verifyMessage(){
		this.errorMessagesShow = 0;
		if(this.loginForm.get('forgetPassword').valid){
			let email = this.loginForm.get('forgetPassword').value;
			let emaillst = email.Email.split('@')
			console.log(email.Email[0], email.Email[1])
			this.emailFirst = email.Email[0];
			this.emailLast = emaillst[1];
			// this.userService.getForgetPassword(email).then(resp =>{
			//   let Response;
			//   Response = resp;
			//   console.log('Response trueeee 111')
			//   if(Response && Response.status){
			// 	console.log('Response trueeee 2222')
			// 	if(Response.status == 'true'){
			// 		console.log('Response trueeee 33333')
			// 		setTimeout(()=>{
			// 			if(this.userService.otp){
			// 				let otp = this.userService.otp
			// 				console.log(otp)
			// 				this.loginForm.get('otpForm').get('otp1').patchValue(otp[0]);
			// 				this.loginForm.get('otpForm').get('otp2').patchValue(otp[1]);
			// 				this.loginForm.get('otpForm').get('otp3').patchValue(otp[2]);
			// 				this.loginForm.get('otpForm').get('otp4').patchValue(otp[3]);
			// 				this.loginForm.get('otpForm').get('otp5').patchValue(otp[4]);
			// 				this.loginForm.get('otpForm').get('otp6').patchValue(otp[5]);
			// 			}
			// 		},2000)
			// 		this.otpMessage = 1;
				  
			// 	}else{
			// 	  	// this.userService.add(Response)
			// 		// jQuery('#sign-modal-up').modal('hide')
			// 		// jQuery('#unsuccess-modal').modal('show')
			// 		this.errorMessagesShow = 1;
			// 			this.errorMessage = Response.message
			// 		// this.forgetPassword = 0;
			// 		this.loginForm.get('forgetPassword').get('Email').reset();
			// 	}
		
			//   }
			// })
		}else{
			this.loginForm.get('forgetPassword').get('Email').markAsTouched();
			
		}
		
		
	}

	sendOtp(){

		this.errorMessagesShow = 0;
		
		 let inputData: string = '';
		 const controls = this.loginForm.controls;
		
		
		let otp1 = this.loginForm.get('otpForm').get('otp1').value;
		let otp2 = this.loginForm.get('otpForm').get('otp2').value;
		let otp3 = this.loginForm.get('otpForm').get('otp3').value;
		let otp4 = this.loginForm.get('otpForm').get('otp4').value;
		let otp5 = this.loginForm.get('otpForm').get('otp5').value;
		let otp6 = this.loginForm.get('otpForm').get('otp6').value; 
		
		 	inputData = otp1 + otp2 + otp3 + otp4 + otp5 + otp6;
		
		
		 
		//  this.userService.sendOtp(inputData).then( resp => {
		//    let response;
		//    response = resp;
		//    this.userService.add(response);
		//    if(response.status == 'true'){
			 
		// 	 this.verifyotp = 1;
			
	 
		//    }
		//    jQuery('#sign-modal-up').modal('hide')
		// //    jQuery('#unsuccess-modal').modal('show')
		//    this.verifyotp = 0;
		//    this.forgetPassword = 0;
		// 	this.loginForm.get('forgetPassword').get('Email').reset();
		//  });
	 
		
	   }

	sms(){
		this.errorMessagesShow = 0;
    console.log(this.mobileNumber)
		this.loginForm.get('phoneVerification').get('MobileNo').setValue(this.mobileNumber);
		this.loginForm.get('phoneVerification').get('UserId').setValue(this.userId);
		this.loginForm.get('phoneVerification').get('Key').setValue('V');
		let data = this.loginForm.get('phoneVerification').value
		
	   console.log(data)
		// this.userService.getVerifyPhone(data).then(resp => {
		//   let response;
		//   response = resp;
		//   if(response.status == 'true'){
		// 	  console.log("if true")
		// 	  setTimeout(()=>{
		// 		if(this.userService.otp){
		// 			let otp = this.userService.otp
		// 			console.log(otp)
		// 			this.loginForm.get('otpForm').get('otp1').patchValue(otp[0]);
		// 			this.loginForm.get('otpForm').get('otp2').patchValue(otp[1]);
		// 			this.loginForm.get('otpForm').get('otp3').patchValue(otp[2]);
		// 			this.loginForm.get('otpForm').get('otp4').patchValue(otp[3]);
		// 			this.loginForm.get('otpForm').get('otp5').patchValue(otp[4]);
		// 			this.loginForm.get('otpForm').get('otp6').patchValue(otp[5]);
		// 		}
		// 	},2000)
			
		
		//   }else{
		// 	console.log("else true")
		// 	// this.verifyotp =3;
			
		// 	// this.userService.add(response)
		//   }
		// });
		
	  } 
	verifyMObile(){

	}  
	
	closeModal(){
		this.loginForm.reset();
		this.forgetPassword = 0;
		this.verifyotp = 0;
		this.signIn = 0;
		this.loginForm.get('login').enable();
		this.errorMessagesShow = 0;
	}

	facebookLogin(): void {
		// this.errorMessagesShow = 0;
		console.log("hello login gacebook")
		// this.authService.signIn(FacebookLoginProvider.PROVIDER_ID);
	} 

	googleLogin(): void {
		// this.errorMessagesShow = 0;
		console.log("hello login googlebook")
		// this.authService.signIn(GoogleLoginProvider.PROVIDER_ID);
	}


	  valuesChangesOTP(){
		this.errorMessagesShow = 0;
		this.loginForm.valueChanges.subscribe(resp => {
			let OTP = this.loginForm.get("otpForm");
			console.log(OTP.get('otp1').value)
			if(OTP.get('otp5').value && OTP.get('otp4').value && OTP.get('otp3').value && OTP.get('otp2').value && OTP.get('otp1').value){
			  jQuery("#otp6").focus()
			}else if(OTP.get('otp4').value && OTP.get('otp3').value && OTP.get('otp2').value && OTP.get('otp1').value){
			  jQuery("#otp5").focus()
			}else if(OTP.get('otp3').value && OTP.get('otp2').value && OTP.get('otp1').value){
			  jQuery("#otp4").focus()
			}else if(OTP.get('otp2').value && OTP.get('otp1').value){
			  jQuery("#otp3").focus()
			}else if(OTP.get('otp1').value){
			  jQuery("#otp2").focus()
			}
		  })
	  }

	enterLogin(event){
		console.log(event.target.id)
		jQuery('#signInLogin').click();
	}  

	policyLink(){
		jQuery('#sign-modal-up').modal('hide')
	}

}
