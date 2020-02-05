import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';
import { AppSettings } from 'src/app/utils/constant';
import { id_ID } from 'ng-zorro-antd';
declare var jQuery: any

@Component({
  selector: 'app-student',
  templateUrl: './student.component.html',
  styleUrls: ['./student.component.css']
})
export class StudentComponent implements OnInit {
  studentlist = []
  postStatus = [];
  activatedds: boolean;
  userTypeId: any;
  id: any;
  constructor(public userService: UserServiceService,
    public router: Router) { 
      this.activatedds =false;
    }

  ngOnInit() {
    let data =  {
        'userTypeId': 4
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
// policyDetail(id, userTypeId){
//   this.router.navigate(['/profile'],{queryParams: {id: id,userTypeId: userTypeId,admins: 'Admin/Student'}})
// }
EditDetails(id, userTypeId){
  this.router.navigate(['Admin/addAdmin'],{queryParams: {id: id,userTypeId: userTypeId,admins: '0'}})
}
deleteAdmin(){
  let data ={
    'userId': this.id,
    'userTypeId': this.userTypeId
  }
  console.log(data)
  this.userService.dataPostApi(data, AppSettings.DeleteSubAdmin).then(resp =>{
    console.log(resp)
    if(resp['status'] == 'true'){
      this.activatedds = true;
      let data =  {
        'userTypeId': 4
    }
    this.userService.dataPostApi(data,AppSettings.AllSubAdmins ).then(resp=>{
      this.studentlist = resp['result']
      this.studentlist.forEach(resp =>{
        this.getStatus(resp.status)
      })
      
      setTimeout(()=>{
        jQuery('#addAdmin-delete').modal('hide')
      },2000)
      
    
  })
    }
  })
}
deleteAdmins(id, userTypeId){
  this.id = id;
  this.userTypeId = userTypeId
  jQuery('#addAdmin-delete').modal('show')
}
clsoeModal(){
  jQuery('#addAdmin-delete').modal('hide')
}

}
