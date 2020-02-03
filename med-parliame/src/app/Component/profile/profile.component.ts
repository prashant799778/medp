import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
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
	constructor(public route: ActivatedRoute,
				public userService: UserServiceService) { }

	ngOnInit() {
		this.route.queryParams.subscribe(params => {
				this.id = params['id'];
				this.userTypeId = params['userTypeId']
				if(this.userTypeId == '2'){
					let data = {
						'userId': this.id,
						'userTypeId': 5
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
				}else{
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
					this.userProfile.status = '1'
					this.modalDescription = 'activate'
					jQuery('#students-pop').modal('show')
				}else{
					jQuery('#students-pop').modal('show')
					this.modalDescription = 'deactivate'
					this.userProfile.status = '0'
				}

			}
		})
	}

}
