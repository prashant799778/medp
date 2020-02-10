import { Component, OnInit } from '@angular/core';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';

@Component({
  selector: 'app-account-verification',
  templateUrl: './account-verification.component.html',
  styleUrls: ['./account-verification.component.css']
})
export class AccountVerificationComponent implements OnInit {

	constructor(public userService: UserServiceService) { }

	ngOnInit() {
		this.userService.dataPostApi(null,AppSettings)
	}

}
