import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-policy',
  templateUrl: './user-policy.component.html',
  styleUrls: ['./user-policy.component.css']
})
export class UserPolicyComponent implements OnInit {
	UserPolicyList=[];
	postStatus = [];
	constructor(public userService: UserServiceService,
				public router: Router) { }

	ngOnInit() {
		this.userService.getApiData(AppSettings.AllPolicyMaker).then(resp=>{
			console.log(resp)
			this.UserPolicyList = resp['result']
			this.UserPolicyList.forEach(resp =>{
				this.getStatus(resp.status)
			})
		
		})
	}
	getStatus(status){

		if(status == '0'){
			this.postStatus.push(0)
		}else if(status == '1'){
			this.postStatus.push(1);
		}
	}
	policyDetail(id){
		this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: 5,admins: '5'}})
	}

}
