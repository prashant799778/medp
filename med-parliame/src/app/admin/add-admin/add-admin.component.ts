import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
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
	UPdateUserss: boolean;
	tabName: any;
	acitvated: any;
	acitvatedd1: boolean;
	constructor(public userService: UserServiceService,
		public route: ActivatedRoute,
		public router: Router,
				public fb: FormBuilder) {
					this.createTable()
				 }

    ngOnInit() {
		this.route.queryParams.subscribe(params => {
			this.id = params['id'];
			this.userTypeId = params['userTypeId']
			this.tabName = params['admins']
			let data = {
				userId: this.id,
				userTypeId: this.userTypeId

			}
			this.addAddminForm.get('userId').setValue(this.id)
			this.userService.dataPostApi(data,AppSettings.AllSubAdmins).then(resp=>{
				console.log(resp['result'][0])
				if(resp['result'][0].status == 0){
					this.acitvated = '0'

				}else if(resp['result'][0].status == 1){
					this.acitvated = '1'
				}
				this.addAddminForm.get('adminName').setValue(resp['result'][0].userName)
				this.addAddminForm.get('emailId').setValue(resp['result'][0].email)
				this.addAddminForm.get('userTypeId').setValue(resp['result'][0].userTypeId)
				this.addAddminForm.get('password').setValue(resp['result'][0].password)
				this.addAddminForm.get('confirmPass').setValue(resp['result'][0].password)
				this.addAddminForm.get('flag').setValue('u')
				this.UPdateUserss = true;

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
			userId: [''],
			adminType: [''],
			password: [''],
			confirmPass: [''],
			userTypeId: [''],
			flag: ['i']	
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
				if(this.addAddminForm.get('flag').value == 'i'){
					this.modalDescription = " Admin Inserted Successfully "
				}else{
					this.modalDescription = " Admin Updated Successfully "
				}
				
			}

		})
	}
	setAdminType(id){
		console.log(id)
		this.addAddminForm.get('userTypeId').setValue(id)
	}
	// updateUser(){
	// 	let datas = {
	// 		userTypeId: this.userTypeId,
	// 		userId: this.id,
	// 		email: this.emailId,
	// 		userName: this.userName,
	// 	}
	// 	let data = this.addAddminForm.getRawValue();
	// 	this.userService.dataPostApi(data,AppSettings.UpdateUser).then(resp =>{
	// 		console.log(resp)
	// 		if(resp['status'] == 'true'){
	// 			jQuery('#addAdmin-pop').modal('show')
	// 			this.modalDescription = "Student Admin editedd "
	// 		}

	// 	})	
	// }
	closeMOdal(){
		console.log(this.tabName)
		if(this.tabName == 0){
			this.router.navigateByUrl('Admin/student')
		}else if(this.tabName == 1){
			this.router.navigateByUrl('Admin/enterpenure')

		}else{
			this.router.navigateByUrl('Admin/policy')
		}
	}
	deactivate(){
		console.log(this.addAddminForm.value)
		let data ={
			'userTypeId': this.userTypeId,
			'userId':  this.id,
			'email': this.addAddminForm.get('emailId').value
		}
		this.userService.dataPostApi(data, AppSettings.UpdateStatus).then(resp=>{
			if(resp['status'] == 'true'){
			this.acitvatedd1 = true;
			this.modalDescription = "Successfull"
				
			}
		})
	}
	openModal(){
		jQuery('#addAdmin-pop').modal('show')
	}

}
