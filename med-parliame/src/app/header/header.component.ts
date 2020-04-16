import { Component, OnInit } from '@angular/core';
import { AuthsService } from '../services/auths.service';
import { LocalStorageService } from 'angular-web-storage';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';
declare var jQuery: any

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName: any;
  activatedds: boolean;
  constructor(public authsService: AuthsService,
              public userService: UserServiceService,
             public local: LocalStorageService) {
                this.activatedds = false
              }

  ngOnInit() {
    console.log(this.local.get('userData1')[0])
    this.userName = this.local.get('userData1')[0].userName
    // let data = {
    //   'userTypeId':
    // }
    this.userService.dataPostApi(null,AppSettings.superAdminNotificationCount).then(resp=>{
      console.log(resp)
    })
  }
  logout(){
   
    this.authsService.logout()
  }
  logouts(){
    jQuery('#logout-pop').modal('show')
  }

}
