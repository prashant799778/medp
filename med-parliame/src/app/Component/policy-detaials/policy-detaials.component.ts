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
	constructor(private route: ActivatedRoute, private router: Router,
				public local: LocalStorageService,
				public userService: UserServiceService) { }

	ngOnInit() {
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
			if(resp['result']['0'].status == 0){
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
		this.router.navigateByUrl('/dashboard')
	}

}
