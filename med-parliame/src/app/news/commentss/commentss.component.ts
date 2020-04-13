import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { LocalStorageService } from 'angular-web-storage';
declare var jQuery: any;

@Component({
  selector: 'app-commentss',
  templateUrl: './commentss.component.html',
  styleUrls: ['./commentss.component.css']
})
export class CommentssComponent implements OnInit {
  newsId: any;
  allnews=[];

  beforeApproved: boolean;
	approvedModal: any;
  activatedds: boolean;
  onlyView: boolean;
  ids: any;


  constructor(public route: ActivatedRoute,
    public local: LocalStorageService,
     private apiService: UserServiceService,) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      console.log(params);
      this.newsId = params['NewsId'];
      console.log(this.newsId);
      let data ={
        'Id': this.newsId
      }
      this.apiService.dataPostApi(data, AppSettings.allMarketingInsightThread).then(resp=>{
        console.log(resp)
        if(resp && resp['status']=='true'){
          this.allnews = resp['result']['0']
        }
      })
    });
  }

  Approve(id){
		if(id == '1'){
			console.log(this.local.get('userData1')[0].userId)
			let data = {
				// 'id': this.id,
				'userTypeId': this.local.get('userData1')[0].userTypeId,
				'approvedUserId': this.local.get('userData1')[0].userId,
				'id': this.ids
	
			}
			this.apiService.dataPostApi(data, AppSettings.approveMarketingComment).then(resp =>{
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
							// this.newsId = ""
							this.activatedds = false;
						},500)
					},2000)
					this.reloadPost()
				}
			})
		}
		if(id == '2'){
			console.log(this.local.get('userData1')[0].userId)
			let data = {
				// 'id': this.id,
				'userTypeId': this.local.get('userData1')[0].userTypeId,
				'approvedUserId': this.local.get('userData1')[0].userId,
				'id': this.ids
	
			}
			this.apiService.dataPostApi(data, AppSettings.rejectMarketingComment).then(resp =>{
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
							// this.newsId = ""
							this.activatedds = false;
						},500)
					},2000)
					this.reloadPost()
				}
			})
		}
		
		
  }
  
  reloadPost(){
		let data = {
		  'Id': this.newsId
		}
		this.apiService.dataPostApi(data, AppSettings.allMarketingInsightThread).then(resp =>{
		  // this.postDetails = resp['result']['1']['0']
		  this.allnews = resp['result']['0']
		  
		})
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
	
	Approved(id,oc){
    console.log(oc,"vijay")
		jQuery('#approv-pop').modal('show')
		this.approvedModal = oc
		this.ids = id;
	}
		

}
