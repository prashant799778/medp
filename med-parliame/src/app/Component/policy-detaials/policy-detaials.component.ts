import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { LocalStorageService } from 'angular-web-storage';
import { $ } from 'protractor';
declare var jQuery: any;

@Component({
  selector: 'app-policy-detaials',
  templateUrl: './policy-detaials.component.html',
  styleUrls: ['./policy-detaials.component.css']
})
export class PolicyDetaialsComponent implements OnInit {
	id: any;
	userTypeId: any;
	postDetails: any;
	modalDescription: any;
	onlyView: boolean
	superLogin: boolean;
	locations: any;
	constructor(private route: ActivatedRoute, private router: Router,
				public local: LocalStorageService,
				public userService: UserServiceService) {
					
				 }

	ngOnInit() {
		this.locations = window.location.href
			console.log(this.locations)
		this.locations = this.locations.split('/dashboard')
		console.log(this.locations)
		// console.log(this.local.get('userData2').superLogin)
		if(this.local.get('userData2') && this.local.get('userData2').superLogin == 'yes'){
			this.superLogin = true;
		}else{
			this.superLogin = false;
		}
		this.route.queryParams.subscribe(params => {
			this.id = params['id'];
			this.userTypeId = params['userTypeId']
		})
		let data = {
			'postId': this.id,
			'userTypeId': this.userTypeId
		}
		this.userService.dataPostApi(data, AppSettings.AllPosts).then(resp =>{
			this.postDetails = resp['result']['0']
			if(resp['result']['0'].status == 0 || resp['result']['0'].status == 1 || resp['result']['0'].status == 2){
				// console.log("onluy view")
				this.onlyView = true;
			}
		})
	}
	Approve(id){
		if(id != '3'){
			let data = {
				'postId': this.id,
				'userTypeId': this.local.get('userData1')[0].userTypeId,
				'approvedUserId': this.local.get('userData1')[0].userId,
				'id': id
	
			}
			this.userService.dataPostApi(data, AppSettings.VerifyPost).then(resp =>{
				if(resp['status']== 'true'){
					jQuery('#approv-pop').modal('show')
					if(id == '1'){
						this.modalDescription = ' Post Approved Successfully'
					}else{
						this.modalDescription = ' Post Rejected Successfully'
					}
				}
			})
		}else{
			jQuery('#approv-pop').modal('show')
			this.modalDescription = 'Post OnHold'
		}
		
		
	}
	closeMOdal(){
		// if(window.location.href == ){

		// }
		this.router.navigateByUrl('/allPosts')
	}
	goto(id){
		this.router.navigateByUrl(id)
	}
		
	

}
