import { Component, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/utils/constant';
import { UserServiceService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-student',
  templateUrl: './user-student.component.html',
  styleUrls: ['./user-student.component.css']
})
export class UserStudentComponent implements OnInit {
	UserStudentList= [];
	postStatus = [];
	  constructor(public userService: UserServiceService,
					public router: Router) { }

	ngOnInit() {
		this.userService.getApiData(AppSettings.AllStudentsList).then(resp=>{
			console.log(resp)
			this.UserStudentList = resp['result']
			this.UserStudentList.forEach(element => {
				this.getStatus(element.status)
			});
		
		})
	}
	policyDetail(id){
		this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: 7,admins: '4'}})
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
