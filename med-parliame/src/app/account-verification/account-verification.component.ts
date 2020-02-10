import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-account-verification',
  templateUrl: './account-verification.component.html',
  styleUrls: ['./account-verification.component.css']
})
export class AccountVerificationComponent implements OnInit {
	message: any;
	userId: any;
	constructor(public userService: UserServiceService,
				public route: ActivatedRoute) { }

	ngOnInit() {
		this.route.queryParams.subscribe(params => {
			this.userId = params['userId'];
			let data = {
				'userId': this.userId
			}
			this.userService.dataPostApi(data,AppSettings.AccountVerification).then(resp=>{
				console.log(resp)
				if(resp['status'] == 'true'){
					this.message = resp['message']
				}
			})
		})

		
	}

}
