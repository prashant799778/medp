import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
declare var jQuery: any;

@Component({
  selector: 'app-reply-event-post',
  templateUrl: './reply-event-post.component.html',
  styleUrls: ['./reply-event-post.component.css']
})
export class ReplyEventPostComponent implements OnInit {
  apprejId: any;
  id: any;
	userTypeId: any;
	postDetails: any;
	modalDescription: any;
	onlyView: boolean
	superLogin: boolean;
	locations: any;
	dashboardssCheck: any;
	beforeApproved: boolean;
	approvedModal: any;
	activatedds: boolean;
	frmPost: FormGroup;
	userType: any;
  comment = [];
  studentAdmin: boolean;

	constructor(public fb: FormBuilder,private route: ActivatedRoute, private router: Router,
				public local: LocalStorageService,
				public userService: UserServiceService) {
					this.beforeApproved =false;
          this.activatedds = false
          this.studentAdmin = true;
				 }

	ngOnInit() {
		this.route.queryParams.subscribe(params => {
			this.id = params['id'];
			this.dashboardssCheck = params['dashboard']
		})
		this.frmPost = this.fb.group({
			commentDescription: ['']
		  });
		//   this.frmPost.get('commentDescription').valueChanges.subscribe(value =>{
		// 	  console.log(0
		//   })
		// this.locations = window.location.href
		// 	console.log(this.locations)
		// // this.locations = this.locations.split('/dashboard')
		console.log(this.locations)
		// console.log(this.local.get('userData2').superLogin)
		if(this.local.get('userData2') && this.local.get('userData2').superLogin == 'yes'){
			this.superLogin = true;
		}else{
			this.superLogin = false;
		}
		this.route.queryParams.subscribe(params => {
			this.id = params['id'];
			this.userTypeId = params['userTypeId']
		})
		let data = {
			'Id': this.id,
			// 'userTypeId': this.userTypeId,
			'commentDescription': this.frmPost.get('commentDescription').value 
    }
    if(this.local.get('userData1') && this.local.get('userData1').userType == 4){
      this.studentAdmin = true;
    }else{
      this.studentAdmin = false;
    }
		this.userService.dataPostApi(data, AppSettings.getEventReply).then(resp =>{
			this.postDetails = resp['result']['1']['0']
			this.comment = resp['result']['0']
			if(resp['result']['1'].status == 0 ){
				console.log("only view")
				this.onlyView = true;
			}
		})
		

	}
	keyCodeCheck(event){
		console.log(event.keyCode)
		if(event.keyCode == 13){
			this.replyPost()
		}
  }
  
  reloadPost(){
    let data = {
      'Id': this.id
    }
    this.userService.dataPostApi(data, AppSettings.getEventReply).then(resp =>{
      this.postDetails = resp['result']['1']['0']
      this.comment = resp['result']['0']
      if(resp['result']['1'].status == 0 ){
        console.log("onluy view")
        this.onlyView = true;
      }
    })
  }

	replyPost(){
		let data = {
			'Id': this.id,
			'userTypeId': this.local.get('userData1')[0].userTypeId,
			'approvedUserId': this.local.get('userData1')[0].userId,
			'commentDescription': this.frmPost.get('commentDescription').value 
		}
		this.userService.dataPostApi(data, AppSettings.VerifyPost1).then(resp =>{
			// this.postDetails = resp['result']
			if(resp['status'] == 'true'){
				this.frmPost.get('commentDescription').reset()
				let data = {
					'Id': this.id,
					// 'userTypeId': this.userTypeId,
					'commentDescription': this.frmPost.get('commentDescription').value 
				}
				this.userService.dataPostApi(data, AppSettings.getEventReply).then(resp =>{
					this.postDetails = resp['result']['1']['0']
					this.comment = resp['result']['0']
					if(resp['result']['1'].status == 0 ){
						console.log("onluy view")
						this.onlyView = true;
					}
				})
			}
		})
	}
	Approve(id){
		if(id == '1'){
			console.log(this.local.get('userData1')[0].userId)
			let data = {
				'userTypeId': this.local.get('userData1')[0].userTypeId,
				'approvedUserId': this.local.get('userData1')[0].userId,
				'id': this.apprejId
	
			}
			this.userService.dataPostApi(data, AppSettings.approveEventPostComment).then(resp =>{
				if(resp['status']== 'true'){
					
					// if(id == '1'){
					// 	this.modalDescription = ' Post Approved Successfully'
					// }else{
					// 	this.modalDescription = ' Post Rejected Successfully'
					// }
					// this.beforeApproved = true;
					this.onlyView = false;
					this.activatedds = true;
					setTimeout(()=>{
            jQuery('#approv-pop').modal("hide")
            setTimeout(()=>{
							this.approvedModal = ""
							this.apprejId = ""
							this.activatedds = false;
						},1000)
          },2000)
          this.reloadPost()
				}
			})
    }
    if(id == '2'){
			console.log(this.local.get('userData1')[0].userId)
			let data = {
				'userTypeId': this.local.get('userData1')[0].userTypeId,
				'approvedUserId': this.local.get('userData1')[0].userId,
				'id': this.apprejId
	
			}
			this.userService.dataPostApi(data, AppSettings.rejectEventPostComment).then(resp =>{
				if(resp['status']== 'true'){
					
					// if(id == '1'){
					// 	this.modalDescription = ' Post Approved Successfully'
					// }else{
					// 	this.modalDescription = ' Post Rejected Successfully'
					// }
					// this.beforeApproved = true;
					this.onlyView = false;
					this.activatedds = true;
					setTimeout(()=>{
            jQuery('#approv-pop').modal("hide")
            setTimeout(()=>{
							this.approvedModal = ""
							this.apprejId = ""
							this.activatedds = false;
						},1000)
          },2000)
          this.reloadPost()
				}
			})
    }
		
		
	}
	closeMOdal(){
		jQuery('#approv-pop').modal('hide')
		// if(window.location.href == ){

		// }
		// console.log(this.dashboardssCheck)
		// if(this.dashboardssCheck == 'dashboard'){
		// 	this.router.navigateByUrl('/dashboard')
		// }else{
		// 	this.router.navigateByUrl('/allPosts')
		// }
		
	}
	goto(id){
		this.router.navigateByUrl(id)
	}
	Approved(id,oc){
		jQuery('#approv-pop').modal('show')
		this.approvedModal = oc
		this.apprejId = id;
	}
		
	

}

