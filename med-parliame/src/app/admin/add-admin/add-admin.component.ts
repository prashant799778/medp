import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
declare var jQuery: any;
@Component({
  selector: 'app-add-admin',
  templateUrl: './add-admin.component.html',
  styleUrls: ['./add-admin.component.css']
})
export class AddAdminComponent implements OnInit {
	recentAdmin= [];
	addAddminForm: FormGroup
	adminDropDown = [];
	id: any;
	userTypeId: any;
	AdminsDetails: any;
	modalDescription: any;
	constructor(public userService: UserServiceService,
		public route: ActivatedRoute,
				public fb: FormBuilder) {
					this.createTable()
				 }

    ngOnInit() {
		this.route.queryParams.subscribe(params => {
			this.id = params['id'];
			this.userTypeId = params['userTypeId']
			let data = {
				userId: this.id,
				userTypeId: this.userTypeId

			}
			this.userService.dataPostApi(data,AppSettings.AllSubAdmins).then(resp=>{
				console.log(resp['result'][0])
				this.addAddminForm.get('adminName').setValue(resp['result'][0].userName)
				this.addAddminForm.get('emailId').setValue(resp['result'][0].email)
				this.addAddminForm.get('userTypeId').setValue(resp['result'][0].userTypeId)
				this.addAddminForm.get('password').setValue(resp['result'][0].password)
				this.addAddminForm.get('confirmPass').setValue(resp['result'][0].password)

			})
		})

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
			if(resp['status'] == 'true'){
				jQuery('#addAdmin-pop').modal('show')
				this.modalDescription = "Student Admin editedd "
			}

		})
	}
	setAdminType(id){
		console.log(id)
		this.addAddminForm.get('userTypeId').setValue(id)
	}

}
