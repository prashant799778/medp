import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AppSettings } from 'src/app/utils/constant';
import { UserServiceService } from 'src/app/services/user-service.service';
import { LocalStorageService } from 'angular-web-storage';
declare var jQuery: any

@Component({
  selector: 'app-edit-news',
  templateUrl: './edit-news.component.html',
  styleUrls: ['./edit-news.component.css']
})
export class EditNewsComponent implements OnInit {

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
  constructor(public fb: FormBuilder,public local: LocalStorageService, private apiService: UserServiceService, private route: ActivatedRoute, private router: Router) { 
        this.activatedds = false;
        this.tabsIndex = 0;
        this.frmShowNews = this.fb.group({
          startlimit: [''],
          endlimit: [''],
          CategoryId: ['1'],
          UserType: [''],
          filter: ['']
        });  
      }
    
  isCollapsed: boolean = false;
  iconCollapse: string = 'icon-arrow-up';

  ngOnInit() {
    // this.route.queryParams.subscribe(x => this.loadPage(x.page || 1));
    //       console.log("Page")
    this.getUsertype();
    this.getNews();
  }

  getUsertype(){
    this.apiService.dataPostApi(null,AppSettings.userDropDown).then(resp=>{
      console.log(resp)
      if(resp['status'] == 'true'){
        this.userTypeDetails = resp['result']
      }
    })
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
      this.totalRecords = data['totalnewscategorywise']
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
    if (userType === 2) {
      const params = { filters: this.frmShowNews.get('filter').value,
      // CategoryId : this.frmShowNews.get('CategoryId').value,
      startlimit: 0,
      endlimit: this.pageSize,
      // UserCreate: AppSettings.getLoggedInUser()                
    };
      this.apiService.dataPostApi(params,AppSettings.SHOW_ADMIN_NEWS).then((data: any[]) => {
        this.totalRecords = data['totalnewscategorywise']

        if(this.totalRecords > this.pageSize){
          console.log("inside if",this.totalRecords)
          this.paginationDisplay = true;
          }else{
            console.log("inside else",this.totalRecords)
          this.paginationDisplay = false;
          }
        this.allnews = data['result'];
        console.log(this.allnews)
      });
    } else {
      const params = { filters: this.frmShowNews.get('filter').value,
      // CategoryId : this.frmShowNews.get('CategoryId').value,
      startlimit: 0,
      endlimit: this.pageSize
    };    
      this.apiService.dataPostApi(params,AppSettings.SHOW_ADMIN_NEWS).then((data: any[]) => {
        this.totalRecords = data['totalnewscategorywise']

        if(this.totalRecords > this.pageSize){
          console.log("inside if",this.totalRecords)
          this.paginationDisplay = true;
          }else{
            console.log("inside else",this.totalRecords)
          this.paginationDisplay = false;
          }
        this.allnews = data['result'];
        console.log(this.allnews)
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
    this.apiService.dataPostApi(data, AppSettings.DELETE_ADMIN_NEWS).then((data: any[]) => {
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

  

}


