import { Component, OnInit } from '@angular/core';
import { AuthsService } from '../services/auths.service';
import { LocalStorageService } from 'angular-web-storage';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {
  userName: any;
  constructor(public authsService: AuthsService,
             public local: LocalStorageService) { }

  ngOnInit() {
    console.log(this.local.get('userData1')[0].userName)
    this.userName = this.local.get('userData1')[0].userName
  }
  logout(){
    this.authsService.logout()
  }

}
