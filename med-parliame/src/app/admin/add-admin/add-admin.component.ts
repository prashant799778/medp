import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.css']
})
export class AddAdminComponent implements OnInit {
	recentAdmin= [];
	addAddminForm: FormGroup
	constructor(public userService: UserServiceService,
				public fb: FormBuilder) {
					this.createTable()
				 }

    ngOnInit() {
		this.userService.getApiData(AppSettings.TotalSubAdmins).then(resp=>{
			console.log(resp)
			this.recentAdmin = resp['result']
			
		})
	}
	createTable(){
		this.addAddminForm = this.fb.group({
			userName: [''],
			email: [''],
			adminType: [''],
			password: [''],
			confirmPass: ['']	
		})
	}
	resetData(){
		this.addAddminForm.reset()
	}

}
