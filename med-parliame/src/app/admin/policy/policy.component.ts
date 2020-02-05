import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { Router } from '@angular/router';

@Component({
  selector: 'app-policy',
  templateUrl: './policy.component.html',
  styleUrls: ['./policy.component.css']
})
export class PolicyComponent implements OnInit {
	PolicyList = []
	postStatus = []
	  constructor(public userService: UserServiceService,
					public router: Router) { }

	ngOnInit() {
		let data =  {
			'userTypeId': 2
		}
		this.userService.dataPostApi(data,AppSettings.AllSubAdmins ).then(resp=>{
		  this.PolicyList = resp['result']
		  this.PolicyList.forEach(resp =>{
			this.getStatus(resp.status)
		  })
		  
		
	  })
	}
	getGender(value){
		if(value == '0'){
			return 'Female'
		}else if(value == '1'){
			return 'Male'
		}else{
			return 'Other'
		}
	}
	getStatus(status){

		if(status == '0'){
		  this.postStatus.push(0)
		}else {
		  this.postStatus.push(1);
		}
	  }

	  policyDetail(id, userTypeId){
		this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: userTypeId,admins: 'AdminPolicy'}})
	}
	EditDetails(id, userTypeId){
		this.router.navigate(['Admin/addAdmin'],{queryParams: {id: id,userTypeId: userTypeId,admins: '2'}})
	}
	

	deleteAdmin(id, userTypeId){
		let data ={
		  'userId': id,
		  'userTypeId': userTypeId
		}
		console.log(data)
		this.userService.dataPostApi(data, AppSettings.DeleteSubAdmin).then(resp =>{
		  if(resp['status'] == 'true'){
			let data =  {
				'userTypeId': 2
			}
			this.userService.dataPostApi(data,AppSettings.AllSubAdmins ).then(resp=>{
			  this.PolicyList = resp['result']
			  this.PolicyList.forEach(resp =>{
				this.getStatus(resp.status)
			  })
			  
			
		  }) 
		  }
		})
	  }

}
