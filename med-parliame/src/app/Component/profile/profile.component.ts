import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { LocalStorageService } from 'angular-web-storage';
declare var jQuery: any;

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent implements OnInit {
	id: any;
	userTypeId: any;
	userProfile: any;
	userPost = [];
	enterpueresss: boolean;
	modalDescription: any;
	AdminsDetails: any;
	adminlist1: any;
	adminlist2: any;
	superLogin: boolean;
	activatedds: boolean;
	constructor(public route: ActivatedRoute,
		public local: LocalStorageService,
		public router: Router,
				public userService: UserServiceService) {
					this.activatedds = false
				 }

	ngOnInit() {
		if(this.local.get('userData2') && this.local.get('userData2').superLogin == 'yes'){
			this.superLogin = true;
		}else{
			this.superLogin = false;
		}
		this.route.queryParams.subscribe(params => {
				this.id = params['id'];
				this.userTypeId = params['userTypeId']
				this.AdminsDetails = params['admins']
				console.log(this.AdminsDetails)
				if(this.AdminsDetails == '0'){
					console.log(this.AdminsDetails)
					this.adminlist1 = 'Admin /'
					this.adminlist2 = 'Student /'
				}else if(this.AdminsDetails == '1'){
					console.log(this.AdminsDetails)
					this.adminlist1 = 'Admin /'
					this.adminlist2 = 'Entrepreneur /'
				}else if(this.AdminsDetails == '2'){
					console.log(this.AdminsDetails)
					this.adminlist1 = 'Admin /'
					this.adminlist2 = 'Policy /'
				}else if(this.AdminsDetails == '4'){
					this.adminlist1 = 'User /'
					this.adminlist2 = 'Student /'
				}else if(this.AdminsDetails == '5'){
					this.adminlist1 = 'User /'
					this.adminlist2 = 'Policy Maker/'
					console.log(this.adminlist1)
				}else if(this.AdminsDetails == '6'){
					this.adminlist1 = 'User /'
					this.adminlist2 = 'Entrepreneur /'
				}

				this.getData()

				// if(this.userTypeId == '2'){
				// 	let data = {
				// 		'userId': this.id,
				// 		'userTypeId': 2
				// 	}	
				// 	this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				// 		console.log(resp)
				// 		this.userProfile = resp['result']['userProfile'][0]
				// 		this.userPost = resp['result']['userPost']
				// 	})
				// }else if(this.userTypeId == '3'){
				// 	this.enterpueresss = true;
				// 	let data = {
				// 		'userId': this.id,
				// 		'userTypeId': 6
				// 	}	
				// 	this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				// 		console.log(resp)
				// 		this.userProfile = resp['result']['userProfile'][0]
				// 		this.userPost = resp['result']['userPost']
				// 	})
				// }else if(this.userTypeId == '5'){
				// 	console.log("hello check 1")
				// 	this.enterpueresss = true;
				// 	let data = {
				// 		'userId': this.id,
				// 		'userTypeId': 5
				// 	}	
				// 	this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				// 		console.log(resp)
				// 		this.userProfile = resp['result']['userProfile'][0]
				// 		this.userPost = resp['result']['userPost']
				// 	})
				// }else if(this.userTypeId == '6'){
				// 	this.enterpueresss = true;
				// 	let data = {
				// 		'userId': this.id,
				// 		'userTypeId': 6
				// 	}	
				// 	this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				// 		console.log(resp)
				// 		this.userProfile = resp['result']['userProfile'][0]
				// 		this.userPost = resp['result']['userPost']
				// 	})
				// }else if(this.userTypeId == '7'){
				// 	this.enterpueresss = true;
				// 	let data = {
				// 		'userId': this.id,
				// 		'userTypeId': 7
				// 	}	
				// 	this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				// 		console.log(resp)
				// 		this.userProfile = resp['result']['userProfile'][0]
				// 		this.userPost = resp['result']['userPost']
				// 	})
				// }
				
			})
		
	}

	ActiveStudent(){
		let data ={
			'userTypeId': this.userProfile.userTypeId,
			'userId':  this.userProfile.userId,
			'email': this.userProfile.email,
		}
		this.userService.dataPostApi(data,AppSettings.UpdateStatus).then(resp=>{
			if(resp['status'] == 'true'){
				
				if(this.userProfile.status == '0'){
					console.log(this.userTypeId)
					// this.userProfile.status = '1'
					this.getData()
					this.modalDescription = 'Deactivated'
					this.activatedds = true;
					setTimeout(()=>{
						jQuery('#students-pop').modal("hide")
						setTimeout(()=>{
							this.activatedds = false;
					},1000)
						
					},2000)
					
				}else{
					console.log(this.userTypeId)
					this.getData()
					// jQuery('#students-pop').modal('show')
					this.modalDescription = 'activated'
					this.activatedds = true;
					// this.userProfile.status = '0'
					setTimeout(()=>{
						jQuery('#students-pop').modal("hide")
						setTimeout(()=>{
							this.activatedds = false;
					},1000)
					},2000)
				}

			}
		})
	}
	activatedStduent(){
		jQuery('#students-pop').modal('show')
	}
	closeMOdal(){
		console.log(this.AdminsDetails, this.userTypeId)
		if(this.AdminsDetails == 6){
			this.router.navigateByUrl('/User/enterpenure')
		}else if(this.AdminsDetails == 5){
			this.router.navigateByUrl('/User/policy')

		}else if(this.AdminsDetails == 4){
			this.router.navigateByUrl('/User/student')

		}else if(this.userTypeId == '7'){
			this.router.navigateByUrl('/studentsList')
		}else if(this.userTypeId == '6'){
			this.router.navigateByUrl('/enterpenure')
		}
	}
	getData(){
		if(this.userTypeId == '2'){
			let data = {
				'userId': this.id,
				'userTypeId': 2
			}	
			this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				console.log(resp)
				this.userProfile = resp['result']['userProfile'][0]
				this.userPost = resp['result']['userPost']
			})
		}else if(this.userTypeId == '3'){
			this.enterpueresss = true;
			let data = {
				'userId': this.id,
				'userTypeId': 6
			}	
			this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				console.log(resp)
				this.userProfile = resp['result']['userProfile'][0]
				this.userPost = resp['result']['userPost']
			})
		}else if(this.userTypeId == '5'){
			console.log("hello check 1")
			this.enterpueresss = true;
			let data = {
				'userId': this.id,
				'userTypeId': 5
			}	
			this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				console.log(resp)
				this.userProfile = resp['result']['userProfile'][0]
				this.userPost = resp['result']['userPost']
			})
		}else if(this.userTypeId == '6'){
			this.enterpueresss = true;
			let data = {
				'userId': this.id,
				'userTypeId': 6
			}	
			this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				console.log(resp)
				this.userProfile = resp['result']['userProfile'][0]
				this.userPost = resp['result']['userPost']
			})
		}else if(this.userTypeId == '7'){
			this.enterpueresss = true;
			let data = {
				'userId': this.id,
				'userTypeId': 7
			}	
			this.userService.dataPostApi(data, AppSettings.UserProfile).then(resp=>{
				console.log(resp)
				this.userProfile = resp['result']['userProfile'][0]
				this.userPost = resp['result']['userPost']
			})
		}
	}

}
