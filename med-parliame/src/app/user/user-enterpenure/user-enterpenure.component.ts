import { Component, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/utils/constant';
import { UserServiceService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-user-enterpenure',
  templateUrl: './user-enterpenure.component.html',
  styleUrls: ['./user-enterpenure.component.css']
})
export class UserEnterpenureComponent implements OnInit {
  UserEnterpenureList = [];
  postStatus = [];
  constructor(public userService: UserServiceService,
              public router: Router) { }

  ngOnInit() {
    
    this.userService.getApiData(AppSettings.AllEnterprenuers).then(resp=>{
			console.log(resp)
			this.UserEnterpenureList = resp['result']
			this.UserEnterpenureList.forEach(resp =>{
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
		this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: 6,admins: '6'}})
	}

}
