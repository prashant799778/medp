import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';

@Component({
  selector: 'app-policy',
  templateUrl: './policy.component.html',
  styleUrls: ['./policy.component.css']
})
export class PolicyComponent implements OnInit {
	PolicyList = []
  	constructor(public userService: UserServiceService) { }

	ngOnInit() {
		this.userService.getApiData(AppSettings.AllPolicyMaker).then(resp=>{
				console.log(resp)
				this.PolicyList = resp['result']
				
			})
	}

}
