import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
	totalEnterprenuer: any;
	totalPolicy: any;
	totalStudent: any;
	totalAdmins: any;
	PolicyList = [];
	constructor(public userService: UserServiceService) { }

	ngOnInit() {
		this.userService.getApiData(AppSettings.AdminPannel).then(resp=>{
			
			
			this.totalEnterprenuer = resp['result']['enterprenuerMasterCount'].count
			this.totalPolicy = resp['result']['policyMakerMasterCount'].count
			this.totalStudent = resp['result']['studentMasterCount'].count
			this.totalAdmins = resp['result']['totalAdmins'].count
			
		})
		console.log(this.totalPolicy)
		this.userService.getApiData(AppSettings.AllPolicyMaker).then(resp=>{
			console.log(resp)
			this.PolicyList = resp['result']
			
		})
		
	}

}
