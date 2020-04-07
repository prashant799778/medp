import { Component, OnInit } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UserServiceService } from 'src/app/services/user-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { AppSettings } from 'src/app/utils/constant';
declare var jQuery: any
@Component({
  selector: 'app-create-about',
  templateUrl: './create-about.component.html',
  styleUrls: ['./create-about.component.css']
})
export class CreateAboutComponent implements OnInit {
  data: [];
  httpError: boolean;
  userTypeDetails= []
  errorMsg: any;
  newsDetails : any;
  totalnews : number;
  categoryList: [];
  htmlContent = '';
  newsId: number;
  errorMessage: any;
  file: any;
  imageShow: any= '';
  showBanner: number;
  updateCheck: boolean;
  messageShow: any;
  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter News Description...',
    translate: 'no',
    defaultFontName: 'Arial',
    customClasses: [
      {
        name: 'quote',
        class: 'quote',
      },
      {
        name: 'redText',
        class: 'redText'
      },
      {
        name: 'titleText',
        class: 'titleText',
        tag: 'h1',
      },
    ]
  };

  frmNews: FormGroup;
  constructor(public fb: FormBuilder, private apiService: UserServiceService, private route: ActivatedRoute,public local:LocalStorageService, private router: Router) {
    console.log('constructor');
    this.initializeForm();
    this.updateCheck= false;
   }

   ngOnInit() {
    console.log("step 1")
    this.route.queryParams.subscribe(params => {
      console.log(params);
      this.newsId = params['NewsId'];
      console.log(this.newsId);
      if(this.newsId){
        this.updateCheck= true;
      }
    });
    if (this.newsId !== undefined) {
      // this.getCategoryList();
      this.getNewsData(this.newsId);
     
    } else {
      // this.getCategoryList();
    }
    this.getUsertype()
  }
  collapsed(event: any): void {
  }

  expanded(event: any): void {
  }

  initializeForm() {
    // Question,Answer,UserId
    this.frmNews = this.fb.group({
      newsType: [''],
      newsTitle: [''],
      banner: [''],
      summary: [''],
      newsDesc: [''],
      userCreate: [''],
      userTypeId: [''],
      id: [''],
      
    });
  }

  onFileSelect(event) {
    console.log(event)
    if(event.type === "change"){
      if (event.target.files.length > 0) {
        this.file = event.target.files[0];
        // console.log(file);
        var reader = new FileReader();
        reader.readAsDataURL(event.target.files[0]);
        reader.onload = (event) => {
          this.imageShow = (<FileReader>event.target).result;
          this.frmNews.get('banner').setValue(this.file);
          this.showBanner = 0;
        }
      }
      
    }
    
    
 
  }
  UpdateNews(){
    if(!this.frmNews.get('newsDesc').valid){
      this.errorMessage = "Please Upload Video Link or Image"
      jQuery("#errorModal").modal('show')
      return;
    }
    console.log(this.frmNews.value)
    this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
    const newsData = {
      // newsType : this.frmNews.get('newsType').value,
      // newsTitle: this.frmNews.get('newsTitle').value,
      // summary: this.frmNews.get('summary').value,
      description: this.frmNews.get('newsDesc').value,
      // UserId : this.frmNews.get('userCreate').value,
      // userTypeId: this.frmNews.get('userTypeId').value,
      flag: 'u',
      id: this.frmNews.get('id').value,
      // status: 1
    };

    const formData = new FormData();
    // formData.append('NewsBanner', this.frmNews.get('banner').value ? this.frmNews.get('banner').value : 'NULL');
    formData.append('data', JSON.stringify(newsData));

    console.log(formData);
    this.apiService.dataPostApi(newsData, AppSettings.aboutUs).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
       
        this.frmNews.reset();
        jQuery('#addAdmin-news2').modal('show')
        this.messageShow = 'Updated'
        setTimeout(()=>{
          jQuery('#addAdmin-news2').modal('hide')
        },2000)
        this.imageShow = '';
        console.log(this.file)
        this.file = '';
        var elemsss = (<HTMLInputElement>document.getElementById('file12'))
        
        elemsss.value = '';

        // document.getElementById('filesss').val('');


      }
    });
  }

  // submitNews() {
  //   this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
  //   const newsData = {
  //     // newsType : this.frmNews.get('newsType').value,
  //     newsTitle: this.frmNews.get('newsTitle').value,
  //     summary: this.frmNews.get('summary').value,
  //     newsDesc: this.frmNews.get('newsDesc').value,
  //     UserId : this.frmNews.get('userCreate').value,
  //     userTypeId: this.frmNews.get('userTypeId').value,
  //     flag: 'i'
  //   };

  //   const formData = new FormData();
  //   formData.append('NewsBanner', this.frmNews.get('banner').value ? this.frmNews.get('banner').value : 'NULL');
  //   formData.append('news', JSON.stringify(newsData));

  //   console.log(formData);
  //   this.apiService.dataPostApi(formData, AppSettings.CREATE_ADMIN_NEWS).then((data: any[]) => {
  //     console.log(data);
  //     if(data['status'] == 'true'){
  //       this.frmNews.reset();
  //       this.imageShow = '';
  //       console.log(this.file)
  //       this.file = '';
  //       var elemsss = (<HTMLInputElement>document.getElementById('file12'))
        
  //       elemsss.value = '';

  //       // document.getElementById('filesss').val('');


  //     }
  //   });

  // }

  // getCategoryList(){
  //   this.apiService.dataPostApi(null,AppSettings.GET_NEWS_CATEGORY).then((data: any[]) => {
  //     this.categoryList = data['result'];
  //     console.log(this.categoryList);
  //   });
  // }

  getNewsData(newsId){
    const params = { id: newsId};
      this.apiService.dataPostApi(params,AppSettings.allaboutUs).then((data: any[]) => {
        console.log(data['result']);
        if (data['status'] === 'true') {
          this.newsDetails = data['result'];
          this.totalnews = data['totalnewsdata'];
          this.setNewsDetails();
        } else {
          this.httpError = true;
          this.errorMsg = data['message'];
        }
      });
  }

  setNewsDetails(){
    let NewsCount = 0;
    console.log("news",this.newsDetails)
    if(this.newsDetails[0]['imagePath'] !== " "){
      this.showBanner = 1;
// this.imageClick = true;
// this.isCropDone = true;
      this.frmNews.get('banner').setValue(this.newsDetails[0]['imagePath']);
    }
    // this.frmNews.get('newsType').setValue(this.newsDetails[0]['newsType']);
    // this.frmNews.get('newsTitle').setValue(this.newsDetails[0]['newsTitle']);
    // this.frmNews.get('summary').setValue(this.newsDetails[0]['summary']);
    this.frmNews.get('newsDesc').setValue(this.newsDetails[0]['description']);
    this.frmNews.get('id').setValue(this.newsDetails[0]['id']);
    // this.frmNews.get('status').setValue(this.newsDetails[0]['newsDesc']);
    // if(this.userTypeDetails){
    //   this.userTypeDetails.forEach(resp=>{
    //     if(resp.id == this.newsDetails[0]['userTypeId'])
    //   })
    // }
    // this.frmNews.get('userTypeId').setValue(this.newsDetails[0]['userTypeId'])
  }
  getUsertype(){
    this.apiService.dataPostApi(null,AppSettings.userDropDown).then(resp=>{
      console.log(resp)
      if(resp['status'] == 'true'){
        this.userTypeDetails = []
this.userTypeDetails = resp['result']
      }
    })
  }
}
