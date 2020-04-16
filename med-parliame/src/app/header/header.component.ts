import { Component, OnInit } from '@angular/core';
import { AuthsService } from '../services/auths.service';
import { LocalStorageService } from 'angular-web-storage';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';
import { UserTypeId } from '../pipes.pipe';
declare var jQuery: any

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName: any;
  activatedds: boolean;
  notification: boolean;
  totalNotification: any;
  userTypeId: any;
  constructor(public authsService: AuthsService,
              public userService: UserServiceService,
             public local: LocalStorageService) {
                this.activatedds = false
              }

  ngOnInit() {
    console.log(this.local.get('userData1')[0])
    this.userName = this.local.get('userData1')[0].userName
    this.userTypeId = this.local.get('userData1')[0].userTypeId
    // let data = {
    //   'userTypeId':
    // }
    setInterval(()=>{
      this.getDatA()
    },2000)
    
    
  }

  getDatA(){
    if(this.userTypeId == 1){
      this.userService.dataPostApi(null,AppSettings.superAdminNotificationCount).then(resp=>{
        console.log(resp)
        if(resp && resp['totalcount'] > 0){
          this.notification = true;
          this.totalNotification = resp['totalcount']
        }
      })
    }else{
      let data ={
        "userTypeId": this.userTypeId
      }
      this.userService.dataPostApi(data,AppSettings.superAdminNotificationCount).then(resp=>{
        console.log(resp)
        if(resp && resp['totalcount'] > 0){
          this.notification = true;
          this.totalNotification = resp['totalcount']
        }
      })
    }
  }
  logout(){
   
    this.authsService.logout()
  }
  logouts(){
    jQuery('#logout-pop').modal('show')
  }
  gotOPage(){
    console.log("clsose freined")
    this.notification = false;
  }

}
