import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';
import { FormGroup, FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-endorse',
  templateUrl: './endorse.component.html',
  styleUrls: ['./endorse.component.css']
})
export class EndorseComponent implements OnInit {
  newsId: any;
  allnews=[];


  pageSize: number = 10;
  totalRecords: number;
  paginationDisplay: boolean;
  frmShowNews: FormGroup;


  constructor(public fb: FormBuilder,public route: ActivatedRoute, private apiService: UserServiceService,) { 
    this.frmShowNews = this.fb.group({
      startlimit: [0],
      endlimit: [10],
      Id: ['']
     
    });
  }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      console.log(params);
      this.newsId = params['NewsId'];
      console.log(this.newsId);
      this.getNews()
     
    });
  }

  pageChanged(event){
    console.log(event)
    // this.pageSize = event;
    // let totalpagess = (this.totalRecords / 2)
    let endlimit = this.pageSize;
    let startlimit = (this.pageSize * event) - this.pageSize;
    if(endlimit > this.totalRecords){
    endlimit = this.totalRecords;
    
    this.frmShowNews.get('endlimit').setValue(endlimit)
    }else{
    this.frmShowNews.get('endlimit').setValue(this.pageSize)
    }
    
    this.frmShowNews.get('startlimit').setValue(startlimit)
    // this.frmShowNews.get('CategoryId').setValue(this.CategoryId)
    let data = this.frmShowNews.getRawValue();
    
    this.apiService.dataPostApi(data,AppSettings.getmarketingInsightslikes).then((data: any[]) => {
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
    this.frmShowNews.get('Id').setValue(this.newsId)
    let data = this.frmShowNews.getRawValue()
      this.apiService.dataPostApi(data,AppSettings.getmarketingInsightslikes).then((data: any[]) => {
        this.totalRecords = data['totalCount']

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
