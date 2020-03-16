import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserServiceService } from 'src/app/services/user-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { AppSettings } from 'src/app/utils/constant';
declare var jQuery: any;

@Component({
  selector: 'app-create-up-skill',
  templateUrl: './create-up-skill.component.html',
  styleUrls: ['./create-up-skill.component.css']
})
export class CreateUpSkillComponent implements OnInit {

  percentDone: number;
  uploadSuccess: boolean;
  size:any;
  width:number;
  height:number;
  errorImage: boolean;
  imageClick: boolean;

  @ViewChild('coverFilesInput', {static: false}) imgType:ElementRef;

  
  data: [];
  httpError: boolean;
  userTypeDetails= []
  errorMsg: any;
  newsDetails : any;
  messageShow: any;
  totalnews : number;
  categoryList: [];
  htmlContent = '';
  newsId: number;
  file: any;
  imageShow: any= '';
  showBanner: number;
  updateCheck: boolean;
  config: AngularEditorConfig = {
    editable: true,
    spellcheck: true,
    height: '15rem',
    minHeight: '5rem',
    placeholder: 'Enter Academic Council Description...',
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
   imageCLik1(){
    this.imageShow = '';
    this.imageClick = true
  }
  imageCLik(){
    this.imageClick = false;
    this.frmNews.get('videoLink').setValue('');
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
      videoLink: [''],
      newsType: [''],
      newsTitle: ['',Validators.required],
      banner: [''],
      summary: ['',Validators.required],
      newsDesc: ['',Validators.required],
      userCreate: [''],
      userTypeId: ['',Validators.required],
      id: [''],
      newsvideoTranscript: [''],
      newsLanguage: [''],
      newsLevel: [''],
      newsSubject: [''],
      newsInstitution: [''],
      newsPrice: [''],
      newsEffort: [''],
      newsLength: [''],
      
    });
    this.frmNews.valueChanges.subscribe(()=>{
      console.log(this.frmNews)
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
                  this.errorImage = false;
                }else{
                  this.errorImage = true;
                  this.imageShow = ''
                }
           };
           const csv = fr.result;
           if(typeof csv == 'string'){
            img.src = csv
           }
      }
      
      fr.readAsDataURL(image);
      this.imgType.nativeElement.value = "";
    
    
 
  }

  gcds(num_1,num_2){
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
    if(this.frmNews.valid){
      console.log(this.frmNews.value)
    this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
    const newsData = {
      // newsType : this.frmNews.get('newsType').value,
      newsTitle: this.frmNews.get('newsTitle').value,
      summary: this.frmNews.get('summary').value,
      newsDesc: this.frmNews.get('newsDesc').value,
      UserId : this.frmNews.get('userCreate').value,
      userTypeId: this.frmNews.get('userTypeId').value,
      flag: 'u',
      id: this.frmNews.get('id').value,
      status: 1,
      videoLink: this.frmNews.get('videoLink').value,



      videoTranscript: this.frmNews.get('newsvideoTranscript').value,
      language: this.frmNews.get('newsLanguage').value,
      level: this.frmNews.get('newsLevel').value,
      subject: this.frmNews.get('newsSubject').value,
      institutions: this.frmNews.get('newsInstitution').value,
      price: this.frmNews.get('newsPrice').value,
      effort: this.frmNews.get('newsEffort').value,
      length: this.frmNews.get('newsLength').value,
    };

    const formData = new FormData();
    formData.append('NewsBanner', this.frmNews.get('banner').value ? this.frmNews.get('banner').value : 'NULL');
    formData.append('news', JSON.stringify(newsData));

    console.log(formData);
    this.apiService.dataPostApi(formData, AppSettings.upSkillsOpportunity).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
        this.frmNews.reset();
        jQuery('#addAdmin-news7').modal('show')
        this.messageShow = 'Updated'
        setTimeout(()=>{
          jQuery('#addAdmin-news7').modal('hide')
        },2000)
        this.getUsertype()
        this.updateCheck =false;
        this.imageShow = '';
        console.log(this.file)
        this.file = '';
        var elemsss = (<HTMLInputElement>document.getElementById('file12'))
        
        elemsss.value = '';

        // document.getElementById('filesss').val('');


      }
    });
    }else{
      const controls = this.frmNews.controls;
		
      Object.keys(controls).forEach(controlName => {
        console.log(controls)
        controls[controlName].markAllAsTouched();
      });
      console.log(this.frmNews)
      return false;
    } 
    
  }

  submitNews() {
    if(this.frmNews.valid){
      this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
      const newsData = {
        // newsType : this.frmNews.get('newsType').value,
        newsTitle: this.frmNews.get('newsTitle').value,
        summary: this.frmNews.get('summary').value,
        newsDesc: this.frmNews.get('newsDesc').value,
        UserId : this.frmNews.get('userCreate').value,
        userTypeId: this.frmNews.get('userTypeId').value,
        flag: 'i',
        videoLink: this.frmNews.get('videoLink').value,


        videoTranscript: this.frmNews.get('newsvideoTranscript').value,
      language: this.frmNews.get('newsLanguage').value,
      level: this.frmNews.get('newsLevel').value,
      subject: this.frmNews.get('newsSubject').value,
      institutions: this.frmNews.get('newsInstitution').value,
      price: this.frmNews.get('newsPrice').value,
      effort: this.frmNews.get('newsEffort').value,
      length: this.frmNews.get('newsLength').value,
      };
  
      const formData = new FormData();
      formData.append('NewsBanner', this.frmNews.get('banner').value ? this.frmNews.get('banner').value : 'NULL');
      formData.append('news', JSON.stringify(newsData));
  
      console.log(formData);
      this.apiService.dataPostApi(formData, AppSettings.upSkillsOpportunity).then((data: any[]) => {
        console.log(data);
        if(data['status'] == 'true'){
          this.frmNews.reset();
          jQuery('#addAdmin-news7').modal('show')
          this.messageShow = 'Inserted'
          setTimeout(()=>{
            jQuery('#addAdmin-news7').modal('hide')
          },2000)
          this.getUsertype()
          // this.updateCheck =false;
          this.imageShow = '';
          console.log(this.file)
          this.file = '';
          var elemsss = (<HTMLInputElement>document.getElementById('file12'))
          
          elemsss.value = '';
  
          // document.getElementById('filesss').val('');
  
  
        }
      });
    }else{
      const controls = this.frmNews.controls;
		
      Object.keys(controls).forEach(controlName => {
        console.log(controls)
        controls[controlName].markAllAsTouched();
      });
      console.log(this.frmNews)
      return false;
    }
    

  }

  // getCategoryList(){
  //   this.apiService.dataPostApi(null,AppSettings.GET_NEWS_CATEGORY).then((data: any[]) => {
  //     this.categoryList = data['result'];
  //     console.log(this.categoryList);
  //   });
  // }

  getNewsData(newsId){
    const params = { id: newsId};
      this.apiService.dataPostApi(params,AppSettings.getUpSkillsOpportunity).then((data: any[]) => {
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
    this.frmNews.get('id').setValue(this.newsDetails[0]['id']);
    this.frmNews.get('newsvideoTranscript').setValue(this.newsDetails[0]['videoTranscript'])
      this.frmNews.get('newsLanguage').setValue(this.newsDetails[0]['language'])
      this.frmNews.get('newsLevel').setValue(this.newsDetails[0]['level'])
      this.frmNews.get('newsSubject').setValue(this.newsDetails[0]['subject'])
      this.frmNews.get('newsInstitution').setValue(this.newsDetails[0]['institutions'])
      this.frmNews.get('newsPrice').setValue(this.newsDetails[0]['price'])
      this.frmNews.get('newsEffort').setValue(this.newsDetails[0]['effort'])
      this.frmNews.get('newsLength').setValue(this.newsDetails[0]['length'])
    this.frmNews.get('videoLink').setValue(this.newsDetails[0]['videoLink']);
    // if(this.userTypeDetails){
    //   this.userTypeDetails.forEach(resp=>{
    //     if(resp.id == this.newsDetails[0]['userTypeId'])
    //   })
    // }
    this.frmNews.get('userTypeId').setValue(this.newsDetails[0]['userTypeId'])
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
