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
	adminDropDown = [];
	constructor(public userService: UserServiceService,
				public fb: FormBuilder) {
					this.createTable()
				 }

    ngOnInit() {
		this.userService.getApiData(AppSettings.TotalSubAdmins).then(resp=>{
			console.log(resp)
			this.recentAdmin = resp['result']
			
		})
		this.userService.getApiData(AppSettings.AdminDropDown).then(resp=>{
			console.log(resp)
			this.adminDropDown = resp['result']
			
		})
	}
	createTable(){
		this.addAddminForm = this.fb.group({
			adminName: [''],
			emailId: [''],
			adminType: [''],
			password: [''],
			confirmPass: [''],
			userTypeId: ['']	
		})
	}
	resetData(){
		this.addAddminForm.reset()
	}
	saveData(){

		let data = this.addAddminForm.getRawValue();
		this.userService.dataPostApi(data,AppSettings.AddSubAdmins).then(resp =>{
			console.log(resp)
		})
	}
	setAdminType(id){
		console.log(id)
		this.addAddminForm.get('userTypeId').setValue(id)
	}

}
