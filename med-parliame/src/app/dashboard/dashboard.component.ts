import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';
import { Router } from '@angular/router';
import { AuthsService } from '../services/auths.service';
import { LocalStorageService } from 'angular-web-storage';

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
	superLogin: boolean;
	userId: any;
	PageDisplay: boolean;


	totalApprovedCount: any;
	totalRejectCount: any;
	totalPostCount: any;
	totalUsers: any;

	subDashboardAdmin = [];
	loaderStatus: boolean;
	constructor(public userService: UserServiceService,
				public authsService: AuthsService,
				public local: LocalStorageService,
				public router: Router) {
		// this.postStatus = 0;
		this.totalRecords= 50;
		this.pageSize = 10;
		this.superLogin = false;
		this.loaderStatus = false;
	 }

	ngOnInit() {
		setTimeout(()=>{
			console.log(this.superLogin)
			if(this.local.get('userData2')){
				console.log(this.superLogin)
				if(this.local.get('userData2').superLogin == 'yes'){
					this.superLogin = true;
					if(this.superLogin == true){
						this.userService.getApiData(AppSettings.AdminPannel).then(resp=>{
						
						
							this.totalEnterprenuer = resp['result']['enterprenuerMasterCount'].count
							this.totalPolicy = resp['result']['policyMakerMasterCount'].count
							this.totalStudent = resp['result']['studentMasterCount'].count
							this.totalAdmins = resp['result']['totalAdmins'].count
							
						})
						console.log(this.totalPolicy)
						this.userService.dataPostApi(null,AppSettings.AllUserPosts).then(resp=>{
							console.log(resp)
							this.PostList = resp['result']
							// if()
							this.PostList.forEach(resp =>{
								this.getStatus(resp.status)
								// this.postStatus.push(0)
							})
							
							this.loaderStatus = true;
						})
					}else{
						// console.log(this.userId)
						
					}
				
				}else{
					
					if(this.local.get('userData1')[0].userTypeId == '2'){


						this.userId = this.local.get('userData1')[0].userId
						console.log(this.userId)
						this.superLogin = false
						let data = {
							'userId':this.userId 
						}
						this.userService.getApiDatawithData(AppSettings.PolicyMasterPannel,data).then(resp=>{
						
						
							this.totalApprovedCount = resp['result'][0]['approvedPost'].count
							this.totalRejectCount = resp['result'][0]['rejectedPost'].count
							this.totalUsers = resp['result'][0]['totalUsers'].count
							this.totalPostCount = resp['result'][0]['totalpostCounts'].count
							
						})

						let datas = {
							'userTypeId': 5
						}
						this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
							this.subDashboardAdmin = resp['result']['1']
							console.log(this.subDashboardAdmin)
							this.subDashboardAdmin.forEach(resp=>{
								this.getStatus(resp.status)
							})
							
							
							this.loaderStatus = true;
						})	
					}else if(this.local.get('userData1')[0].userTypeId == '3'){

						this.userId = this.local.get('userData1')[0].userId
					console.log(this.userId)
					this.superLogin = false
					let data = {
						'userId':this.userId 
					}
					this.userService.getApiDatawithData(AppSettings.EnterprenuerMasterPannel,data).then(resp=>{
					
					
						this.totalApprovedCount = resp['result'][0]['approvedPost'].count
						this.totalRejectCount = resp['result'][0]['rejectedPost'].count
						this.totalUsers = resp['result'][0]['totalUsers'].count
						this.totalPostCount = resp['result'][0]['totalpostCounts'].count
						
					})

						let datas = {
							'userTypeId': 6
						}
						this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
							this.subDashboardAdmin = resp['result']['1']
							console.log(this.subDashboardAdmin)
							this.subDashboardAdmin.forEach(resp=>{
								this.getStatus(resp.status)
							})
							
							
							
						})
						this.loaderStatus = true;
					}else{

						this.userId = this.local.get('userData1')[0].userId
					console.log(this.userId)
					this.superLogin = false
					let data = {
						'userId':this.userId 
					}
					this.userService.getApiDatawithData(AppSettings.StudentMasterPannel,data).then(resp=>{
					
					
						this.totalApprovedCount = resp['result'][0]['approvedPost'].count
						this.totalRejectCount = resp['result'][0]['rejectedPost'].count
						this.totalUsers = resp['result'][0]['totalUsers'].count
						this.totalPostCount = resp['result'][0]['totalpostCounts'].count
						
					})

						let datas = {
							'userTypeId': 7
						}
						this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
							this.subDashboardAdmin = resp['result']['1']
							console.log(this.subDashboardAdmin)
							this.subDashboardAdmin.forEach(resp=>{
								this.getStatus(resp.status)
							})
							
							
							
						})

						this.loaderStatus = true;
					}
					console.log(this.local.get('userData1')[0].userId)
					

					
					

				}
			}else{
				if(this.local.get('userData1')[0].userTypeId == '2'){


					this.userId = this.local.get('userData1')[0].userId
					console.log(this.userId)
					this.superLogin = false
					let data = {
						'userId':this.userId 
					}
					this.userService.getApiDatawithData(AppSettings.PolicyMasterPannel,data).then(resp=>{
					
					
						this.totalApprovedCount = resp['result'][0]['approvedPost'].count
						this.totalRejectCount = resp['result'][0]['rejectedPost'].count
						this.totalUsers = resp['result'][0]['totalUsers'].count
						this.totalPostCount = resp['result'][0]['totalpostCounts'].count
						
					})

					let datas = {
						'userTypeId': 5
					}
					this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
						this.subDashboardAdmin = resp['result']['1']
						console.log(this.subDashboardAdmin)
						this.subDashboardAdmin.forEach(resp=>{
							this.getStatus(resp.status)
						})
						
						this.loaderStatus = true;	
						
					})	
				}else if(this.local.get('userData1')[0].userTypeId == '3'){

					this.userId = this.local.get('userData1')[0].userId
				console.log(this.userId)
				this.superLogin = false
				let data = {
					'userId':this.userId 
				}
				this.userService.getApiDatawithData(AppSettings.EnterprenuerMasterPannel,data).then(resp=>{
				
				
					this.totalApprovedCount = resp['result'][0]['approvedPost'].count
					this.totalRejectCount = resp['result'][0]['rejectedPost'].count
					this.totalUsers = resp['result'][0]['totalUsers'].count
					this.totalPostCount = resp['result'][0]['totalpostCounts'].count
					
				})

					let datas = {
						'userTypeId': 6
					}
					this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
						this.subDashboardAdmin = resp['result']['1']
						console.log(this.subDashboardAdmin)
						this.subDashboardAdmin.forEach(resp=>{
							this.getStatus(resp.status)
						})
						
						this.loaderStatus = true;	
						
					})

				}else{

					this.userId = this.local.get('userData1')[0].userId
				console.log(this.userId)
				this.superLogin = false
				let data = {
					'userId':this.userId 
				}
				this.userService.getApiDatawithData(AppSettings.StudentMasterPannel,data).then(resp=>{
				
				
					this.totalApprovedCount = resp['result'][0]['approvedPost'].count
					this.totalRejectCount = resp['result'][0]['rejectedPost'].count
					this.totalUsers = resp['result'][0]['totalUsers'].count
					this.totalPostCount = resp['result'][0]['totalpostCounts'].count
					
				})

					let datas = {
						'userTypeId': 7
					}
					this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
						this.subDashboardAdmin = resp['result']['1']
						console.log(this.subDashboardAdmin)
						this.subDashboardAdmin.forEach(resp=>{
							this.getStatus(resp.status)
						})
						
						this.loaderStatus = true;
						
					})

					
				}

			}
		},1000)
		
		
		
		
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
	policyDetail(id,userTypeId){
		this.router.navigate(['/policyDetails'],{queryParams: {id: id,userTypeId: userTypeId,dashboard: 'dashboard'}})
	}

}
