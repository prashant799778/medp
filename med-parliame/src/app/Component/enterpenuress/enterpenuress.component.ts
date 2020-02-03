import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { Router } from '@angular/router';

@Component({
  selector: 'app-enterpenuress',
  templateUrl: './enterpenuress.component.html',
  styleUrls: ['./enterpenuress.component.css']
})
export class EnterpenuressComponent implements OnInit {
	enterlist = [];
	postStatus = [];
	constructor(public userService: UserServiceService,
				public router: Router) { }

	ngOnInit() {
		
		  this.userService.getApiData(AppSettings.AllEnterprenuers).then(resp=>{
			this.enterlist = resp['result']
			this.enterlist.forEach(resp =>{
				this.getStatus(resp.status)
			})
			
			
			
		  })

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
	this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: 5}})
	}
}
