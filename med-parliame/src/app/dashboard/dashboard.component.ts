import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';
import { Router } from '@angular/router';

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
	PostList = [];
	postStatus= [];
	totalRecords: any;
	pageSize: any;
	constructor(public userService: UserServiceService,
				public router: Router) {
		// this.postStatus = 0;
		this.totalRecords= 50;
		this.pageSize = 10
	 }

	ngOnInit() {
		this.userService.getApiData(AppSettings.AdminPannel).then(resp=>{
			
			
			this.totalEnterprenuer = resp['result']['enterprenuerMasterCount'].count
			this.totalPolicy = resp['result']['policyMakerMasterCount'].count
			this.totalStudent = resp['result']['studentMasterCount'].count
			this.totalAdmins = resp['result']['totalAdmins'].count
			
		})
		console.log(this.totalPolicy)
		this.userService.dataPostApi(null,AppSettings.AllPosts).then(resp=>{
			console.log(resp)
			this.PostList = resp['result']
			this.PostList.forEach(resp =>{
				this.getStatus(resp.status)
				// this.postStatus.push(0)
			})
			
			
		})
		
	}
	getUserType(type){
		if(type == '7'){
			return 'Student'
		}else if(type == '6'){
			return 'Professional'
		}else if(type == '5'){
			return 'Policy Maker'
		}
	}
	getStatus(status){

		if(status == '0'){
			this.postStatus.push(0)
		}else if(status == '1'){
			this.postStatus.push(1);
		}else{
			this.postStatus.push(2);
		}
	}
	policyDetail(id){
		this.router.navigate(['/policyDetails'],{queryParams: {id: id}})
	}

}
