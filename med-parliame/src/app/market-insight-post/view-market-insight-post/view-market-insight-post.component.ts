import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-web-storage';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { AuthsService } from 'src/app/services/auths.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-view-market-insight-post',
  templateUrl: './view-market-insight-post.component.html',
  styleUrls: ['./view-market-insight-post.component.css']
})
export class ViewMarketInsightPostComponent implements OnInit {

	postList=[];
	postStatus = [];
	id: any;
	userTypeId: any;
	dashboardssCheck: any;
	titleHeading: any;
	constructor(public userService: UserServiceService,
	  public authsService: AuthsService,
	  public local: LocalStorageService,
	  public route: ActivatedRoute,
	  public router: Router) { }
  
	ngOnInit() {
	  this.route.queryParams.subscribe(params => {
		this.id = params['id'];
		this.dashboardssCheck = params['dashboard']
		if(this.id){
		  let datas = {
			'userTypeId': this.id
		  }
		  this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
			this.postList = resp['result']
			console.log(this.postList)
			this.postList.forEach(resp=>{
			  this.getStatus(resp.status)
			})
		  })
		  if(this.id == 5){
			this.titleHeading = 'Policy Maker'
			// let datas = {
			//   'userTypeId': 5
			// }
			// this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
			//   this.postList = resp['result']
			//   console.log(this.postList)
			//   this.postList.forEach(resp=>{
			//     this.getStatus(resp.status)
			//   })
			// })
		  }else if(this.id == 6){
			this.titleHeading = 'Entrepreneur'
			// let datas = {
			//   'userTypeId': 6
			// }
			// this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
			//   this.postList = resp['result']
			//   console.log(this.postList)
			//   this.postList.forEach(resp=>{
			//     this.getStatus(resp.status)
			//   })
			// })
		  }else if(this.id == 7){
			this.titleHeading = 'Student'
			// let datas = {
			//   'userTypeId': 7
			// }
			// this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
			//   this.postList = resp['result']
			//   console.log(this.postList)
			//   this.postList.forEach(resp=>{
			//     this.getStatus(resp.status)
			//   })
			// })
		  }else if(this.id == 8){
			this.titleHeading = 'Doctor'
			// let datas = {
			//   'userTypeId': 8
			// }
			// this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
			//   this.postList = resp['result']
			//   console.log(this.postList)
			//   this.postList.forEach(resp=>{
			//     this.getStatus(resp.status)
			//   })
			// })
		  }else if(this.id == 9){
			this.titleHeading = 'Professional'
			// let datas = {
			//   'userTypeId': 9
			// }
			// this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
			//   this.postList = resp['result']
			//   console.log(this.postList)
			//   this.postList.forEach(resp=>{
			//     this.getStatus(resp.status)
			//   })
			// })
		  }else if(this.id == 13){
			this.titleHeading = 'Decision Maker'
			// let datas = {
			//   'userTypeId': 9
			// }
			// this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
			//   this.postList = resp['result']
			//   console.log(this.postList)
			//   this.postList.forEach(resp=>{
			//     this.getStatus(resp.status)
			//   })
			// })
		  }
		}
		
	   
	  })
	  if(this.local.get('userData1')[0].userTypeId == '3'){
		let datas = {
		  'userTypeId': 6
		}
		this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
		  this.postList = resp['result']
		  console.log(this.postList)
		  this.postList.forEach(resp=>{
			this.getStatus(resp.status)
		  })
		})
	  }else  if(this.local.get('userData1')[0].userTypeId == '2'){
		let datas = {
		  'userTypeId': 5
		}
		this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
		  this.postList = resp['result']
		  console.log(this.postList)
		  this.postList.forEach(resp=>{
			this.getStatus(resp.status)
		  })
		})
		  
	  }else if(this.local.get('userData1')[0].userTypeId == '4'){
		this.titleHeading = 'Student'
		let datas = {
		  'userTypeId': 7
		}
		this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
		  this.postList = resp['result']
		  console.log(this.postList)
		  this.postList.forEach(resp=>{
			this.getStatus(resp.status)
		  })
		  console.log(this.postList)
		})
	  }else if(this.local.get('userData1')[0].userTypeId == '10'){
		this.titleHeading = 'Doctor'
		let datas = {
		  'userTypeId': 8
		}
		this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
		  this.postList = resp['result']
		  console.log(this.postList)
		  this.postList.forEach(resp=>{
			this.getStatus(resp.status)
		  })
		})
	  }
	  else if(this.local.get('userData1')[0].userTypeId == '11'){
		this.titleHeading = 'Professional'
		let datas = {
		  'userTypeId': 9
		}
		this.userService.dataPostApi(datas, AppSettings.getMarketingInsights ).then(resp=>{
		  this.postList = resp['result']
		  console.log(this.postList)
		  this.postList.forEach(resp=>{
			this.getStatus(resp.status)
		  })
		})
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
	  
		  this.router.navigate(['/replyPostMarketing'],{queryParams: {id: id,userTypeId: userTypeId}})
	  }
  
  }
  