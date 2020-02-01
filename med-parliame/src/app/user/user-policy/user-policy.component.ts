import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';

@Component({
  selector: 'app-user-policy',
  templateUrl: './user-policy.component.html',
  styleUrls: ['./user-policy.component.css']
})
export class UserPolicyComponent implements OnInit {
	UserPolicyList=[];
	constructor(public userService: UserServiceService) { }

	ngOnInit() {
		this.userService.getApiData(AppSettings.AllPolicyMaker).then(resp=>{
			console.log(resp)
			this.UserPolicyList = resp['result']
		
		})
	}

}
