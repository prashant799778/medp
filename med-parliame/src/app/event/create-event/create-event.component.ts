import { Component, OnInit } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { FormGroup, FormBuilder } from '@angular/forms';
import { UserServiceService } from 'src/app/services/user-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { AppSettings } from 'src/app/utils/constant';
import {IMyDpOptions} from 'mydatepicker';

@Component({
  selector: 'app-create-event',
  templateUrl: './create-event.component.html',
  styleUrls: ['./create-event.component.css']
})
export class CreateEventComponent implements OnInit {
  data: [];
  httpError: boolean;
  userTypeDetails= []
  errorMsg: any;
  newsDetails : any;
  totalnews : number;
  categoryList: [];
  htmlContent = '';
  newsId: number;
  file: any;
  imageShow: any= '';
  showBanner: number;
  public myDatePickerOptions: IMyDpOptions = {
    // other options...
    dateFormat: 'dd.mm.yyyy',
};
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
   }

   ngOnInit() {
    console.log("step 1")
    this.route.queryParams.subscribe(params => {
      console.log(params);
      this.newsId = params['NewsId'];
      console.log(this.newsId);
    });
    if (this.newsId !== undefined) {
      // this.getCategoryList();
      this.getNewsData(this.newsId);
    } else {
      // this.getCategoryList();
    }
    this.
    getUsertype()
  }
  collapsed(event: any): void {
  }

  expanded(event: any): void {
  }

  initializeForm() {
    // Question,Answer,UserId
    this.frmNews = this.fb.group({
      eventDate: [''],
      newsTitle: [''],
      banner: [''],
      summary: [''],
      userTypeId: [''],
      // eventLocation: [''],
      userCreate: [''],
      eventLocation: ['']
    });
    this.frmNews.get('eventDate').valueChanges.subscribe(resp=>{
      if(resp && resp.formatted)
      this.getDate(resp.formatted)
    })
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

  submitNews() {
    console.log(this.frmNews.get('eventDate').value.formatted)
    this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
    const newsData = {
      eventLocation : this.frmNews.get('eventLocation').value,
      eventTitle: this.frmNews.get('newsTitle').value,
      eventSummary: this.frmNews.get('summary').value,
      eventDate: this.getDate(this.frmNews.get('eventDate').value.formatted),
      UserId : this.frmNews.get('userCreate').value,
      userTypeId: this.frmNews.get('userTypeId').value
    };

    const formData = new FormData();
    formData.append('postImage', this.frmNews.get('banner').value);
    formData.append('data', JSON.stringify(newsData));

    console.log(formData);
    this.apiService.dataPostApi(formData, AppSettings.parliamentEvent).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
        this.frmNews.reset();
        this.imageShow = '';
        console.log(this.file)
        this.file = '';
        var elemsss = (<HTMLInputElement>document.getElementById('file12'))
        
        elemsss.value = '';

        // document.getElementById('filesss').val('');


      }
    });

  }

  getDate(date){
    let currenttime = new Date();
    console.log(currenttime.getHours())

    

    if(date){
      let data = date.split('.')
      let newDate = [];
      let counter = 1;
      console.log(data.length)
      for(let i = data.length; i>0; i--){
        console.log(i)
        if(counter < 3){
          newDate.push(data[i-1]+'-') 
          counter ++;
        }else{
          newDate.push(data[i-1]) 
        }

      }
     
      console.log(newDate)
      let afterDate = (newDate.join(''))
      console.log(afterDate+" "+currenttime.getHours()+':'+currenttime.getMinutes()+':'+currenttime.getSeconds() )
      return afterDate+" "+currenttime.getHours()+':'+currenttime.getMinutes()+':'+currenttime.getSeconds()
    }
  }

  // getCategoryList(){
  //   this.apiService.dataPostApi(null,AppSettings.GET_NEWS_CATEGORY).then((data: any[]) => {
  //     this.categoryList = data['result'];
  //     console.log(this.categoryList);
  //   });
  // }

  getNewsData(newsId){
    const params = { NewsId: newsId};
      this.apiService.dataPostApi(params,AppSettings.SHOW_ADMIN_NEWS).then((data: any[]) => {
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
      this.frmNews.get('banner').setValue(this.newsDetails[0]['imagePath']);
    }
    this.frmNews.get('newsType').setValue(this.newsDetails[0]['newsType']);
    this.frmNews.get('newsTitle').setValue(this.newsDetails[0]['newsTitle']);
    this.frmNews.get('summary').setValue(this.newsDetails[0]['summary']);
    this.frmNews.get('newsDesc').setValue(this.newsDetails[0]['newsDesc']);
  }
  getUsertype(){
    this.apiService.dataPostApi(null,AppSettings.userDropDown).then(resp=>{
      console.log(resp)
      if(resp['status'] == 'true'){
        this.userTypeDetails = resp['result']
      }
    })
  }
}
