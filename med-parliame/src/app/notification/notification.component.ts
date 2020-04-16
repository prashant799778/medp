import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'angular-web-storage';
import { UserServiceService } from '../services/user-service.service';
import { AppSettings } from '../utils/constant';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
declare var jQuery: any;

@Component({
  selector: 'app-notification',
  templateUrl: './notification.component.html',
  styleUrls: ['./notification.component.css']
})
export class NotificationComponent implements OnInit {

  allnews = [];
  httpError: boolean;
  errorMsg: any;
  pager = {};
  pageOfItems = [];
  userTypeDetails = [];
  oneTimeCategoryName : any;
  CategoryId: number;
  CategoryName = [];
  tabsIndex: any;
  pageSize: number = 10;
  totalRecords: number;
  paginationDisplay: boolean;
  frmShowNews: FormGroup;
  newsId: any;
  activatedds: boolean;
  noData: boolean;
  userTypeId: any;
  constructor(public fb: FormBuilder,public local: LocalStorageService, private apiService: UserServiceService, private route: ActivatedRoute, private router: Router) { 
        this.activatedds = false;
        this.noData = false;
        this.tabsIndex = 0;
        this.frmShowNews = this.fb.group({
          startlimit: [''],
          endlimit: [''],
         
          UserType: [''],
          
        });  
      }
    
  isCollapsed: boolean = false;
  iconCollapse: string = 'icon-arrow-up';

  ngOnInit() {
    this.getNews();
  }

  
  

  

  pageChanged(event){
    console.log(event)
    // this.pageSize = event;
    // let totalpagess = (this.totalRecords / 2)
    let endlimit = this.pageSize;
    let startlimit = (this.pageSize * event) - this.pageSize;
    if(endlimit > this.totalRecords){
    endlimit = this.totalRecords;
    console
    this.frmShowNews.get('endlimit').setValue(endlimit)
    }else{
    this.frmShowNews.get('endlimit').setValue(this.pageSize)
    }
    
    this.frmShowNews.get('startlimit').setValue(startlimit)
    // this.frmShowNews.get('CategoryId').setValue(this.CategoryId)
    let data = this.frmShowNews.getRawValue();
    
    this.apiService.dataPostApi(data,AppSettings.SHOW_ADMIN_NEWS).then((data: any[]) => {
      this.totalRecords = data['totalCount']
      console.log(this.totalRecords)
      if(this.totalRecords > this.pageSize){
        console.log("inside if",this.totalRecords)
        this.paginationDisplay = true;
        }else{
          console.log("inside else",this.totalRecords)
        this.paginationDisplay = false;
        }
      this.allnews = data['result'];
      
    });
    }

  getNews() {
    const userData = this.local.get('userData1');
    const userType = userData[0]['userTypeId'];
    const userId = userData[0]['userId'];
    if(userType == 4){
      this.userTypeId = 7
    }else if(userType == 3){
      this.userTypeId = 6
    }else if(userType == 2){
      this.userTypeId = 5
    }else if(userType == 10){
      this.userTypeId = 8
    }else if(userType == 11){
      this.userTypeId = 9
    }
    if (userType === 1) {
      const params = { 
        userTypeId: this.userTypeId,
      // CategoryId : this.frmShowNews.get('CategoryId').value,
      startlimit: 0,
      endlimit: this.pageSize,
      // UserCreate: AppSettings.getLoggedInUser()                
    };
      this.apiService.dataPostApi(params,AppSettings.superAdminNotification).then((data: any[]) => {
        this.totalRecords = data['totalcount']

        if(this.totalRecords > this.pageSize){
          console.log("inside if",this.totalRecords)
          this.paginationDisplay = true;
          }else{
            console.log("inside else",this.totalRecords)
          this.paginationDisplay = false;
          }
          console.log(this.totalRecords)
          if(this.totalRecords == 0){
            this.noData = true
          }
        this.allnews = data['result'];
        console.log(this.allnews)
      });
    } else {
      const params = { 
      // CategoryId : this.frmShowNews.get('CategoryId').value,
      userTypeId: this.userTypeId,
      startlimit: 0,
      endlimit: this.pageSize
    };    
      this.apiService.dataPostApi(params,AppSettings.adminNotification).then((data: any[]) => {
        this.totalRecords = data['totalcount']

        if(this.totalRecords > this.pageSize){
          console.log("inside if",this.totalRecords)
          this.paginationDisplay = true;
          }else{
            console.log("inside else",this.totalRecords)
          this.paginationDisplay = false;
          }
          console.log(this.totalRecords)
          if(this.totalRecords == 0){
            this.noData = true
          }
        this.allnews = data['result'];
        // console.log(this.allnews)
      });
    }
  }

  editNews(NewsId) {
    console.log(NewsId);
    this.router.navigate(['/news/createNews'], { queryParams: {NewsId: NewsId}});
  }

  deleteNews(id){
    this.newsId = id
    jQuery('#addAdmin-news').modal('show')
    console.log("Id of News",id);
    
  }
  deletedNewss(){
    let data ={
      'id': this.newsId
    }
    this.apiService.dataPostApi(data, AppSettings.deleteMarketingInsights).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
        this.activatedds = true;
        setTimeout(()=>{
          jQuery('#addAdmin-news').modal('hide')
          setTimeout(()=>{
            this.activatedds = false;
          },1000)
        },1000)
      }
      this.getNews();
      this.newsId = '';
    });
  }
  closeModal(){
    jQuery('#addAdmin-news').modal('hide')
  }

  checkStatus(data) {
    if (data['status'] === 'true') {
      if (data['result'] !== '') {
      this.allnews = data['result'];
      } else {
        this.httpError = true;
        this.errorMsg = data['message'];
      }
    } else {
      this.httpError = true;
      this.errorMsg = data['message'];
    }
  }
  
  changeTabs(i,CategoryId){
    this.tabsIndex = i;
    this.CategoryId = CategoryId;
    this.frmShowNews.get('CategoryId').setValue(this.CategoryId);
    this.getNews()
  }

  policyDetail(id,userTypeId){
		this.router.navigate(['/policyDetails'],{queryParams: {id: id,userTypeId: userTypeId,dashboard: 'dashboard'}})
	}

  

}


