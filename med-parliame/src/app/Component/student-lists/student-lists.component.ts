import { Component, OnInit } from '@angular/core';
import { AppSettings } from 'src/app/utils/constant';
import { UserServiceService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-student-lists',
  templateUrl: './student-lists.component.html',
  styleUrls: ['./student-lists.component.css']
})
export class StudentListsComponent implements OnInit {
  studentlist= []
  postStatus = []
  constructor(public userService: UserServiceService,
    public router: Router) { }

  ngOnInit() {
		
    this.userService.getApiData(AppSettings.AllStudentsList).then(resp=>{
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

policyDetail(id){
this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: 7}})
}

}
