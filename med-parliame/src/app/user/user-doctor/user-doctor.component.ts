import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';
import { AppSettings } from 'src/app/utils/constant';

@Component({
  selector: 'app-user-doctor',
  templateUrl: './user-doctor.component.html',
  styleUrls: ['./user-doctor.component.css']
})
export class UserDoctorComponent implements OnInit {
  UserStudentList= [];
	postStatus = [];
	  constructor(public userService: UserServiceService,
					public router: Router) { }

	ngOnInit() {
		this.userService.dataPostApi(null,AppSettings.AllDoctorList).then(resp=>{
			console.log(resp)
			this.UserStudentList = resp['result']
			this.UserStudentList.forEach(element => {
				this.getStatus(element.status)
			});
		
		})
	}
	policyDetail(id){
		this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: 8,admins: '7'}})
	}
	getGender(value){
		if(value == '1'){
			return 'Female'
		}else if(value == '0'){
			return 'Male'
		}else{
			return 'Other'
		}
	}
	getStatus(status){

		if(status == '0'){
			this.postStatus.push(0)
		}else if(status == '1'){
			this.postStatus.push(1);
		}
	}


}
