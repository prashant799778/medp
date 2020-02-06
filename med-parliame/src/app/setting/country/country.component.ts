import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.css']
})
export class CountryComponent implements OnInit {

	constructor(public userService: UserServiceService) { }

	ngOnInit() {
		this.userService.dataPostApi(null,AppSettings).then(resp=>{

		})
		this.userService.dataPostApi(null,AppSettings).then(resp=>{
			
		})
		this.userService.dataPostApi(null,AppSettings).then(resp=>{
			
		})
	}

}
