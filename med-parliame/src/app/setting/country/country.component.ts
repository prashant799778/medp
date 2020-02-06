import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { FormGroup, FormBuilder } from '@angular/forms';
declare var jQuery: any;

@Component({
  selector: 'app-country',
  templateUrl: './country.component.html',
  styleUrls: ['./country.component.css']
})
export class CountryComponent implements OnInit {

	settingForm: FormGroup;
	error: number;
	errorMessage: any;
	constructor(public userService: UserServiceService,
				public fb: FormBuilder) { 
		this.createTable()
		this.error = 0;
	}

	ngOnInit() {
		// this.userService.dataPostApi(null,AppSettings).then(resp=>{

		// })
		// this.userService.dataPostApi(null,AppSettings).then(resp=>{
			
		// })
		// this.userService.dataPostApi(null,AppSettings).then(resp=>{
			
		// })
	}
	createTable(){
		this.settingForm = this.fb.group({
			country: [''],
			university: [''],
			qualification: ['']
		})
		this.settingForm.get('country').valueChanges.subscribe(value=>{
			this.error = 0;
			this.errorMessage = ''
		})
		this.settingForm.get('university').valueChanges.subscribe(value=>{
			this.error = 0;
			this.errorMessage = ''
		})
		this.settingForm.get('qualification').valueChanges.subscribe(value=>{
			this.error = 0;
			this.errorMessage = ''
		})

	}
	addCountry(){
		if(this.settingForm.get('country').value){
			let data = {
				'countryName' : this.settingForm.get('country').value,
				'flag': 'i'
			}
			this.userService.dataPostApi(data,AppSettings.AddCountry).then(resp=>{
				console.log(resp)
				if(resp['status']=='true'){
					jQuery("#addCountrySucc").modal('show')
					setTimeout(()=>{
						jQuery("#addCountrySucc").modal('hide')
					},2000)
					this.settingForm.reset()
				}else{
					this.error = 1;
					this.errorMessage = resp['message']
				}
			})
		}
	}	

	addUniversity(){
		if(this.settingForm.get('university').value){
			let data = {
				'universityName' : this.settingForm.get('university').value,
				'flag': 'i'
			}
			this.userService.dataPostApi(data,AppSettings.AddUniversity).then(resp=>{
				console.log(resp)
				if(resp['status']=='true'){
					jQuery("#addCountrySucc").modal('show')
					setTimeout(()=>{
						jQuery("#addCountrySucc").modal('hide')
					},2000)
					this.settingForm.reset()
				}else{
					this.error = 2;
					this.errorMessage = resp['message']
				}
			})
		}
	}

	addQualification(){
		if(this.settingForm.get('qualification').value){
			let data = {
				'qualificationName' : this.settingForm.get('qualification').value,
				'flag': 'i'
			}
			this.userService.dataPostApi(data,AppSettings.AddQualification).then(resp=>{
				console.log(resp)
				if(resp['status']=='true'){
					jQuery("#addCountrySucc").modal('show')
					setTimeout(()=>{
						jQuery("#addCountrySucc").modal('hide')
					},2000)
					this.settingForm.reset()
				}else{
					this.error = 3;
					this.errorMessage = resp['message']
				}
			})
		}
	}

}
