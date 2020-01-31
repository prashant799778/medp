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
			console.log(resp)
			this.totalEnterprenuer = resp['enterprenuerMasterCount'].count
			this.totalPolicy = resp['policyMakerMasterCount'].count
			this.totalStudent = resp['studentMasterCount'].count
			this.totalAdmins = resp['totalAdmins'].count
			
		})
		this.userService.getApiData(AppSettings.AllPolicyMaker).then(resp=>{
			console.log(resp)
			this.PolicyList = resp['result']
			
		})
		
	}

}
