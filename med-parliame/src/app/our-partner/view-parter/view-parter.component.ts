import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { FormGroup, FormBuilder, FormArray } from '@angular/forms';
import { UserServiceService } from 'src/app/services/user-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { AppSettings } from 'src/app/utils/constant';
import { Observable } from 'rxjs';
declare var jQuery: any;

@Component({
  selector: 'app-view-parter',
  templateUrl: './view-parter.component.html',
  styleUrls: ['./view-parter.component.css']
})
export class ViewParterComponent implements OnInit {

  percentDone: number;
  uploadSuccess: boolean;
  size:any;
  width:number;
  height:number;
  errorImage=[];
  showAddButton: boolean;
  @ViewChild('coverFilesInput', {static: false}) imgType:ElementRef;


  bannerArray=[];
  data: [];
  httpError: boolean;
  userTypeDetails= []
  errorMsg: any;
  newsDetails : any;
  totalnews : number;
  categoryList: [];
  htmlContent = '';
  newsId: number;
  file=[];
  imageShow= [];
  banner: FormArray;
  messageShow: any;
  showBanner: number;
  updateCheck: Boolean;
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
    this.updateCheck =false;
   }

   Addmore(){
     this.bannerArray.push('')
     this.banArr()
    //  this.addItem()
     console.log(this.frmNews)
   }
   ngOnInit() {
     this.Addmore()
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
      // newsType: [''],
      // newsTitle: [''],
      banner: this.fb.array([]),
      id:[''],
      // summary: [''],
      // newsDesc: [''],
      userCreate: [''],
      // userTypeId: ['']
    });
    
  }

  banArr(){
    return this.fb.group({
      imga: ['']
    })
  }
  createItem(): FormGroup {
    return this.fb.group({
      imga: ''
    });
  }
  

  addItem(): void {
    this.banner = this.frmNews.get('banner') as FormArray;
    this.banner.push(this.createItem());
  }

  onFileSelect(event, index) {
    console.log(event.target.files.length,index)
    if(event.type === "change"){
      if (event.target.files.length > 0) {
        // for (var i = 0; i < index; i++) { 
          this.file.push(event.target.files[0]);
          var reader = new FileReader();
          reader.readAsDataURL(event.target.files[0]);
          reader.onload = (event) => {
            this.imageShow[index] = (<FileReader>event.target).result;

            // this.addCreds(this.file[index])
            console.log("helllllo")
            for(let i = 0; i< index+1; i++){
              // this.banArr()

            }
            this.addItem()
            // this.addItem()
            // addItem(): void {
            //   this.banner = this.frmNews.get('banner') as FormArray;
            //   this.banner.push(this.createItem());
            // }
              console.log(this.frmNews.controls.banner['controls'][index])
              this.frmNews.controls.banner['controls'][index]['controls']['imga'].patchValue(this.file[index])

            // this.frmNews.get('banner').setValue(this.file[index]);
            this.showBanner = 0;
            this.showAddButton = true;
          }
        // }
       
        // this.file = event.target.files[0];
        // console.log(file);
        
      }
      
    }
    let image:any = event.target.files[0];
    console.log(image)
          this.size = image.size;
          console.log(this.size)
          let fr = new FileReader;
          console.log(fr)
          fr.onload = () => { // when file has loaded
            console.log("hello")
            let img = new Image()
           console.log(img)
       
           img.onload = () => {
             console.log(img.width)
               this.width = img.width;
              //  console.log(this.width)
               this.height = img.height;
               let rat = this.gcds(this.width,this.height)
                if(rat == '4:3'){
                  this.errorImage[index] = 0;
                }else{
                  this.errorImage[index] = 1;
                  this.imageShow = []
                }
           };
           const csv = fr.result;
           if(typeof csv == 'string'){
            img.src = csv
           }

           
           
           

          //  let ratio = Math.(this.width/this.height
          //  if()
       
           
          
         
      }
      
      fr.readAsDataURL(image);
      this.imgType.nativeElement.value = "";
    
    
 
  }

  addCreds(val) {
    console.log(this.frmNews.controls.banner as FormArray)
    console.log(typeof( this.frmNews.controls.banner as FormArray))
    const creds = this.frmNews.controls.banner as FormArray;
    console.log(creds, typeof creds)
              creds.push(val);


  }

  gcds(num_1,num_2){
  
    // var ratio = a / b;
    // console.log(Math.abs(ratio))
    // return ( Math.abs( ratio - 4 / 3 ) < Math.abs( ratio - 16 / 9 ) ) ? '4:3' : '16:9';
    for(let num=num_2; num>1; num--) {
      if((num_1 % num) == 0 && (num_2 % num) == 0) {
          num_1=num_1/num;
          num_2=num_2/num;
      }
  }
  var ratio = num_1+":"+num_2;
  return ratio;
}
    


    
    
 
  
  UpdateNews(){
    this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
    const newsData = {
      // newsType : this.frmNews.get('newsType').value,
      // newsTitle: this.frmNews.get('newsTitle').value,
      // summary: this.frmNews.get('summary').value,
      // newsDesc: this.frmNews.get('newsDesc').value,
      userId : this.frmNews.get('userCreate').value,
      // userTypeId: this.frmNews.get('userTypeId').value,
      flag: 'u',
      id: this.frmNews.get('id').value,
      status: 1
    };

    const formData = new FormData();
    formData.append('postImage', this.frmNews.get('banner').value);
    formData.append('data', JSON.stringify(newsData));

    console.log(formData);
    this.apiService.dataPostApi(formData, AppSettings.ourPartnersImages).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
        this.frmNews.reset();
        jQuery('#addAdmin-news5').modal('show')
        this.messageShow = 'Updated'
        setTimeout(()=>{
          jQuery('#addAdmin-news5').modal('hide')
        },2000)
        this.getUsertype()
        this.updateCheck =false;
        this.imageShow =[];
        console.log(this.file)
        this.file = [];
        var elemsss = (<HTMLInputElement>document.getElementById('file12'))
        
        elemsss.value = '';

        // document.getElementById('filesss').val('');


      }
    });
  }


  submitNews() {
    this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
    const newsData = {
      // newsType : this.frmNews.get('newsType').value,
      // newsTitle: this.frmNews.get('newsTitle').value,
      // summary: this.frmNews.get('summary').value,
      // newsDesc: this.frmNews.get('newsDesc').value,
      fileCount: this.imageShow.length,
      userId : this.frmNews.get('userCreate').value,
      flag: 'i'
      // userTypeId: this.frmNews.get('userTypeId').value
    };

    const formData = new FormData();
    for(let i= 1;i<=this.imageShow.length;i++){
      console.log(this.frmNews.controls.banner['controls'],i)
      if(this.frmNews.controls.banner['controls'] && this.frmNews.controls.banner['controls'][i-1]){
        formData.append('postImage_'+i, this.frmNews.controls.banner['controls'][i-1]['controls']['imga'].value);
      }
      
    }
    
    formData.append('data', JSON.stringify(newsData));

    console.log(formData);
    this.apiService.dataPostApi(formData, AppSettings.ourPartnersImages).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
        this.frmNews.reset();
        jQuery('#addAdmin-news5').modal('show')
        this.messageShow = 'Inserted'
        setTimeout(()=>{
          jQuery('#addAdmin-news5').modal('hide')
        },2000)
        this.getUsertype()
       
        this.frmNews.get('banner').reset();
        this.bannerArray=[];
        this.bannerArray.push('')
        this.imageShow = [];
        console.log(this.file)
        this.file = [];
        var elemsss = (<HTMLInputElement>document.getElementById('coverFilesInput'))
        
        elemsss.value = '';

        // document.getElementById('filesss').val('');


      }
    });

  }

  // getCategoryList(){
  //   this.apiService.dataPostApi(null,AppSettings.GET_NEWS_CATEGORY).then((data: any[]) => {
  //     this.categoryList = data['result'];
  //     console.log(this.categoryList);
  //   });
  // }

  getNewsData(newsId){
    const params = { id: newsId};
      this.apiService.dataPostApi(params,AppSettings.getOurPartners).then((data: any[]) => {
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
      this.frmNews.controls.banner['controls'][0]['controls']['imga'].push(this.newsDetails[0]['imagePath'])
      // this.frmNews.get('banner').setValue(this.newsDetails[0]['imagePath']);
this.frmNews.get('videoLink').clearValidators();
    this.frmNews.get('videoLink').updateValueAndValidity();
    }
    // this.frmNews.get('newsType').setValue(this.newsDetails[0]['newsType']);
    // this.frmNews.get('newsTitle').setValue(this.newsDetails[0]['newsTitle']);
    // this.frmNews.get('summary').setValue(this.newsDetails[0]['summary']);
    // this.frmNews.get('newsDesc').setValue(this.newsDetails[0]['newsDesc']);
    this.frmNews.get('id').setValue(this.newsDetails[0]['id']);
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
