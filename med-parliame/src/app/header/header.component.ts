import { Component, OnInit } from '@angular/core';
import { AuthsService } from '../services/auths.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(public authsService: AuthsService) { }

  ngOnInit() {
  }
  logout(){
    this.authsService.logout()
  }

}
