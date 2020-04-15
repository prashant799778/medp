import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AppSettings } from 'src/app/utils/constant';

@Component({
  selector: 'app-event-interest',
  templateUrl: './event-interest.component.html',
  styleUrls: ['./event-interest.component.css']
})
export class EventInterestComponent implements OnInit {
  newsId: any;
  allnews=[];


  constructor(public route: ActivatedRoute, private apiService: UserServiceService,) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      console.log(params);
      this.newsId = params['NewsId'];
      console.log(this.newsId);
      let data ={
        'Id': this.newsId
      }
      this.apiService.dataPostApi(data, AppSettings.getParliamentEventInterest).then(resp=>{
        console.log(resp)
        if(resp && resp['status']=='true'){
          this.allnews = resp['result']
        }
      })
    });
  }

}
