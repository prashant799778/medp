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
	statuss: any;
	activatedds: boolean;
	errorMessage: any;
	errors: boolean;
	flags: any;
	constructor(public userService: UserServiceService,
		public route: ActivatedRoute,
		public router: Router,
				public fb: FormBuilder) {
					this.createTable()
					this.activatedds= false;
					this.errors = false;
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
			
				if(resp && resp['result'] && resp['result'][0].status == 0){
					this.acitvated = '0'

				}else if(resp && resp['result'] && resp['result'][0].status == 1){
					this.acitvated = '1'
				}
				if(resp && resp['result']){
					this.addAddminForm.get('adminName').setValue(resp['result'][0].userName)
					this.addAddminForm.get('emailId').setValue(resp['result'][0].email)
					this.addAddminForm.get('userTypeId').setValue(resp['result'][0].userTypeId)
					this.addAddminForm.get('password').setValue(resp['result'][0].password)
					this.addAddminForm.get('confirmPass').setValue(resp['result'][0].password)
					this.addAddminForm.get('flag').setValue('u')
					this.flags = 'u';
					this.addAddminForm.get('actionType').setValue(resp['result'][0].status)
					this.UPdateUserss = true;
					this.statuss = resp['result'][0].status
				}
				
				

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
			flag: ['i']	,
			actionType: ['']
		})
		this.addAddminForm.get('actionType').valueChanges.subscribe(value => {
			console.log('name has changed:', value,this.statuss)
				if(value != null && value != this.statuss && this.statuss != undefined){
					console.log('name has detactive:', value,this.statuss)
					// this.deactivate()
					this.openModal()

				}
				
			
			
			
	  });
	}
	resetData(){
		this.addAddminForm.reset()
		this.errors = false;
		this.errorMessage = ''
	}
	saveData(){

		let data = this.addAddminForm.getRawValue();
		this.userService.dataPostApi(data,AppSettings.AddSubAdmins).then(resp =>{
			console.log(resp)
			if(resp['status'] == 'true'){
				jQuery('#addAdmin-popSave').modal('show')
				if(this.addAddminForm.get('flag').value == 'i'){
					this.modalDescription = "Inserted Successfully "
				}else{
					this.modalDescription = "Updated Successfully "
				}
				setTimeout(()=>{
					jQuery('#addAdmin-popSave').modal('hide')
				},2000)
				this.addAddminForm.reset();
				if(this.flags == 'u'){
					this.addAddminForm.get('flag').setValue('u')
				}else{
					this.addAddminForm.get('flag').setValue('i')
				}
				// if(this.tabName == 0){
				// 	this.router.navigateByUrl('Admin/student')
				// }else if(this.tabName == 1){
				// 	this.router.navigateByUrl('Admin/enterpenure')
		
				// }else{
				// 	this.router.navigateByUrl('Admin/policy')
				// }
				
			}else{
				this.errors = true;
				this.errorMessage = resp['message']
			}

		})
	}
	setAdminType(id){
		console.log(id)
		this.addAddminForm.get('userTypeId').setValue(id)
	}
	
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
				this.activatedds = true;
				setTimeout(()=>{
					jQuery('#addAdmin-pop').modal("hide")
				},2000)
				
			}
		})
	}
	closeModal(){
		jQuery('#addAdmin-pop').modal('hide')
	}
	openModal(){
		jQuery('#addAdmin-pop').modal('show')
	}
	activatedType(event){
		console.log("hello",event.target.value)
		// if(this.acitvated == '0'){
		// 	this.deactivate()
		// }else if(this.acitvated  == '1'){
		// 	this.deactivate()
		// }
	}


}
