import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { Router } from '@angular/router';
import { AppSettings } from 'src/app/utils/constant';
declare var jQuery: any;

@Component({
  selector: 'app-enterpenure',
  templateUrl: './enterpenure.component.html',
  styleUrls: ['./enterpenure.component.css']
})
export class EnterpenureComponent implements OnInit {
  studentlist = []
  postStatus = [];
  id: any;
  userTypeId: any;
  activatedds: boolean;
  constructor(public userService: UserServiceService,
    public router: Router) { 
      this.activatedds = false;
    }

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
// deleteAdmin(id, userTypeId){
//   let data ={
//     'userId': id,
//     'userTypeId': userTypeId
//   }
//   console.log(data)
//   this.userService.dataPostApi(data, AppSettings.DeleteSubAdmin).then(resp =>{
//     console.log(resp)
//     if(resp['status']== 'true'){
//       let data =  {
//         'userTypeId': 3
//       }
//       this.userService.dataPostApi(data,AppSettings.AllSubAdmins ).then(resp=>{
//         this.studentlist = resp['result']
//         this.studentlist.forEach(resp =>{
//         this.getStatus(resp.status)
//         })
        
      
//       })
//     }
    
//   })
// }
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
        jQuery('#enterpAdmin-delete').modal('hide')
      },2000)
      
    
  })
    }
  })
}
deleteAdmins(id, userTypeId){
  this.id = id;
  this.userTypeId = userTypeId
  jQuery('#enterpAdmin-delete').modal('show')
}
clsoeModal(){
  jQuery('#enterpAdmin-delete').modal('hide')
}

}


