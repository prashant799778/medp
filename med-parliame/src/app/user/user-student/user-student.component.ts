import { Component, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/utils/constant';
import { UserServiceService } from 'src/app/services/user-service.service';

@Component({
  selector: 'app-user-student',
  templateUrl: './user-student.component.html',
  styleUrls: ['./user-student.component.css']
})
export class UserStudentComponent implements OnInit {
	UserStudentList= [];
  	constructor(public userService: UserServiceService) { }

	ngOnInit() {
		this.userService.getApiData(AppSettings.AllStudentsList).then(resp=>{
			console.log(resp)
			this.UserStudentList = resp['result']
		
		})
	}

}
