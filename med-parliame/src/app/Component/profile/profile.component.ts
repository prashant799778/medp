import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';

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
	enterpueresss: boolean
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

}
