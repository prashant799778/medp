import { Component, OnInit, ViewChild, ElementRef } from '@angular/core';
import { AngularEditorConfig } from '@kolkov/angular-editor';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { UserServiceService } from 'src/app/services/user-service.service';
import { ActivatedRoute, Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
import { AppSettings } from 'src/app/utils/constant';
import { CropperComponent } from 'angular-cropperjs';
declare var jQuery: any;
@Component({
  selector: 'app-create-media',
  templateUrl: './create-media.component.html',
  styleUrls: ['./create-media.component.css']
})
export class CreateMediaComponent implements OnInit {
  @ViewChild('angularCropper', {static: false}) public angularCropper: CropperComponent;
  errorMessage: any;
  configs = {
    aspectRatio : 4/3,
    dragMode : 'move',
    background : true,
    movable: true,
    rotatable : true,
    scalable: true,
    zoomable: true,
    viewMode: 1,
    checkImageOrigin : true,
    // cropmove:this.cropMoved.bind(this),
    checkCrossOrigin: true
  };
  cropMoved(data){
    console.log(this.angularCropper.cropper)
    
    console.log(this.imageShow)
  }
  cropingDone(){
    this.imageShow = this.angularCropper.cropper.getCroppedCanvas().toDataURL();
    this.isCropDone= true;
  }
 
  isCropDone: boolean;
  percentDone: number;
  uploadSuccess: boolean;
  size:any;
  width:number;
  height:number;
  errorImage: boolean
  isCropDone2: boolean;


  @ViewChild('coverFilesInput', {static: false}) imgType:ElementRef;
  data: [];
  httpError: boolean;
  userTypeDetails= []
  errorMsg: any;
  newsDetails : any;
  totalnews : number;
  messageShow: any;
  categoryList: [];
  htmlContent = '';
  newsId: number;
  file: any;
  imageShow: any= '';
  showBanner: number;
  imageClick: boolean;
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
    this.imageClick =  false;
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
    // this.
    // getUsertype()
  }
  collapsed(event: any): void {
  }

  expanded(event: any): void {
  }

  initializeForm() {
    // Question,Answer,UserId
    this.frmNews = this.fb.group({
      // newsType: [''],
      newsTitle: ['',Validators.required],
      banner: [''],
      id:[''],
      videoLink: ['',Validators.required],
      // summary: [''],
      // newsDesc: [''],
      userCreate: [''],
      // userTypeId: ['']
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
         this.isCropDone2 = true;
         this.isCropDone = false;
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
                // if(rat == '4:3'){
                //   this.errorImage = false;
                // }else{
                //   this.errorImage = true;
                //   this.imageShow = ''
                // }
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
    if(!this.frmNews.get('newsTitle').valid){
      this.errorMessage = "Please Enter Description"
      jQuery("#errorModal").modal('show')
      return;
    }
    if(!this.frmNews.get('videoLink').valid){
      this.errorMessage = "Please Upload Video Link or Image"
      jQuery("#errorModal").modal('show')
      return;
    }
    if(!this.frmNews.get('banner').valid){
      this.errorMessage = "Please Upload Video Link or Image"
      jQuery("#errorModal").modal('show')
      return;
    }
    this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
    if(this.imageClick == true){
      let imageCheck = this.frmNews.get('newsTitle').value
      let imageText = this.frmNews.get('banner').value
      console.log(imageCheck, imageText)
      if(imageCheck == '' || imageText == ''){
        
        return false;
      }
    }
    const newsData = {
      text: this.frmNews.get('newsTitle').value,
      // summary: this.frmNews.get('summary').value,
      // newsDesc: this.frmNews.get('newsDesc').value,
      userId : this.frmNews.get('userCreate').value,
      
      videoLink: this.frmNews.get('videoLink').value,
      
      // userTypeId: this.frmNews.get('userTypeId').value,
      flag: 'u',
      id: this.frmNews.get('id').value,
      status: 1
    };

    const formData = new FormData();
    formData.append('postImage', this.frmNews.get('banner').value ? this.frmNews.get('banner').value : "");
    formData.append('data', JSON.stringify(newsData));

    console.log(formData);
    this.apiService.dataPostApi(formData, AppSettings.promisingInitiatives).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
        this.frmNews.reset();
        jQuery('#addAdmin-news6').modal('show')
        this.messageShow = 'Updated'
        setTimeout(()=>{
          jQuery('#addAdmin-news6').modal('hide')
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
  }

  resizeed(direction) {
    console.log(direction)
    var delta = 10 * direction;
  
    var element = jQuery('#imageFFFF').find('img')[1]
    var element2 = jQuery('#imageFFFF').find('img')[2]
    console.log(element)

    var positionInfo1 = element2.getBoundingClientRect();
  
    element2.style.width = positionInfo1.width+delta+'px';
    element2.style.height = positionInfo1.height+delta+'px';
 
    var positionInfo = element.getBoundingClientRect();
  
    element.style.width = positionInfo.width+delta+'px';
    element.style.height = positionInfo.height+delta+'px';
  }

  submitNews() {
    if(!this.frmNews.get('newsTitle').valid){
      this.errorMessage = "Please Enter Description"
      jQuery("#errorModal").modal('show')
      return;
    }
    if(!this.frmNews.get('videoLink').valid){
      this.errorMessage = "Please Upload Video Link or Image"
      jQuery("#errorModal").modal('show')
      return;
    }
    if(!this.frmNews.get('banner').valid){
      this.errorMessage = "Please Upload Video Link or Image"
      jQuery("#errorModal").modal('show')
      return;
    }
    if(this.frmNews.valid){
    this.frmNews.get('userCreate').setValue(this.local.get('userData1')[0].userId)
    if(this.imageClick == true){
      let imageCheck = this.frmNews.get('newsTitle').value
      let imageText = this.frmNews.get('banner').value
      console.log(imageCheck, imageText)
      if(imageCheck == '' || imageText == ''){
        
        return false;
      }
    }
    const newsData = {
      // newsType : this.frmNews.get('newsType').value,
      text: this.frmNews.get('newsTitle').value,
      // summary: this.frmNews.get('summary').value,
      // newsDesc: this.frmNews.get('newsDesc').value,
      userId : this.frmNews.get('userCreate').value,
      flag: 'i',
      videoLink: this.frmNews.get('videoLink').value,
      // userTypeId: this.frmNews.get('userTypeId').value
    };

    const formData = new FormData();
    
    formData.append('postImage', this.frmNews.get('banner').value ? this.frmNews.get('banner').value : "");
    formData.append('data', JSON.stringify(newsData));

    console.log(formData);
    this.apiService.dataPostApi(formData, AppSettings.promisingInitiatives).then((data: any[]) => {
      console.log(data);
      if(data['status'] == 'true'){
        this.frmNews.reset();
        jQuery('#addAdmin-news6').modal('show')
        this.messageShow = 'Inserted'
        setTimeout(()=>{
          jQuery('#addAdmin-news6').modal('hide')
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
      this.apiService.dataPostApi(params,AppSettings.getpromisingInitiatives).then((data: any[]) => {
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
    if(this.newsDetails[0]['videoPath']){
      console.log("news if")
      this.imageClick = false;
      this.frmNews.get('videoLink').setValue(this.newsDetails[0]['videoPath']);  
    }else{
      console.log("news else")
      this.imageClick = true;
      this.frmNews.get('newsTitle').setValue(this.newsDetails[0]['text']);
    }
    this.frmNews.get('videoLink').setValue(this.newsDetails[0]['videoPath']);
    this.frmNews.get('newsTitle').setValue(this.newsDetails[0]['text']);
    // this.frmNews.get('summary').setValue(this.newsDetails[0]['summary']);
    // this.frmNews.get('newsDesc').setValue(this.newsDetails[0]['newsDesc']);
    this.frmNews.get('id').setValue(this.newsDetails[0]['id']);
  }
  getUsertype(){
    this.apiService.dataPostApi(null,AppSettings.userDropDown).then(resp=>{
      console.log(resp)
      if(resp['status'] == 'true'){
        this.userTypeDetails = resp['result']
      }
    })
  }
  imageCLik1(){
    this.imageShow = '';
    this.imageClick = true
    
    this.frmNews.get('videoLink').clearValidators();
    this.frmNews.get('videoLink').updateValueAndValidity();
    this.frmNews.get('banner').setValidators(Validators.required)
    this.frmNews.get('banner').updateValueAndValidity();
    console.log("Image UPload",this.frmNews)
  }
  
  imageCLik(){
    this.imageClick = false;
    this.frmNews.get('videoLink').setValue('');
    // this.frmNews.get('videoLink').validator
    this.frmNews.get('banner').clearValidators()
    this.frmNews.get('banner').updateValueAndValidity();
    this.frmNews.get('videoLink').setValidators(Validators.required)
    this.frmNews.get('videoLink').updateValueAndValidity();
    console.log("Video UPload",this.frmNews)
    // this.frmNews.get('videoLink')
  }
}
