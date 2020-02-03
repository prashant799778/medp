import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';
import { AppSettings } from 'src/app/utils/constant';

@Component({
  selector: 'app-enterpenure',
  templateUrl: './enterpenure.component.html',
  styleUrls: ['./enterpenure.component.css']
})
export class EnterpenureComponent implements OnInit {
  studentlist = []
  postStatus = [];
  constructor(public userService: UserServiceService,
    public router: Router) { }

  ngOnInit() {
    let data =  {
			'userTypeId': 3
		}
		this.userService.dataPostApi(data,AppSettings.AllSubAdmins ).then(resp=>{
		  this.studentlist = resp['result']
		  this.studentlist.forEach(resp =>{
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
policyDetail(id, userTypeId){
  this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: userTypeId,admins: 'Admin/Enterpenure'}})
}
EditDetails(id, userTypeId){
  this.router.navigate(['Admin/addAdmin'],{queryParams: {id: id,userTypeId: userTypeId,admins: '1'}})
}

}


