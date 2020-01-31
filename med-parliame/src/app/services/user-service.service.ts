import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { SessionStorageService, LocalStorageService } from 'angular-web-storage';

@Injectable({
	providedIn: 'root'
})
export class UserServiceService {
	baseUrl= 'http://134.209.153.34:5031/';
	user: any;
	KEY = 'value';

	constructor(private http: HttpClient,
				public local: LocalStorageService,
				public session: SessionStorageService) { }

	dataPostApi(data, endpoint){
		return  new Promise((resolve, reject) => {
				let url = this.baseUrl+endpoint;
				this.http.post(url,data).toPromise().then(res =>{
					if(res ){
						resolve(res);
					}else{
						reject('error')
					}
				});
			})
	}
	getApiData( endpoint){
		return  new Promise((resolve, reject) => {
			console.log(this.baseUrl, endpoint)
				let url = this.baseUrl+''+endpoint;
				console.log(url)
				this.http.get(url).toPromise().then(res =>{
					if(res ){
						resolve(res);
					}else{
						reject('error')
					}
				});
			})
	}

	getSaveCustomer(data){
		console.log("data value",data)
		
		  this.user = data;
		  // this.session.set(this.KEY, data);
		  this.local.set('userData1',(data))
		  this.session.set(this.KEY,this.local.get('userData1'))
		
	  }
}
