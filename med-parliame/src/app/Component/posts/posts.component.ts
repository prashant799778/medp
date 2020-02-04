import { Component, OnInit } from '@angular/core';
import { UserServiceService } from 'src/app/services/user-service.service';
import { AuthsService } from 'src/app/services/auths.service';
import { LocalStorageService } from 'angular-web-storage';
import { Router, ActivatedRoute } from '@angular/router';
import { AppSettings } from 'src/app/utils/constant';

@Component({
  selector: 'app-posts',
  templateUrl: './posts.component.html',
  styleUrls: ['./posts.component.css']
})
export class PostsComponent implements OnInit {

  postList=[];
  postStatus = [];
  id: any;
  userTypeId: any;
  constructor(public userService: UserServiceService,
    public authsService: AuthsService,
    public local: LocalStorageService,
    public route: ActivatedRoute,
    public router: Router) { }

  ngOnInit() {
    this.route.queryParams.subscribe(params => {
      this.id = params['id'];
      if(this.id == 5){
        let datas = {
          'userTypeId': 5
        }
        this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
          this.postList = resp['result']
          console.log(this.postList)
          this.postList.forEach(resp=>{
            this.getStatus(resp.status)
          })
        })
      }else if(this.id == 6){
        let datas = {
          'userTypeId': 6
        }
        this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
          this.postList = resp['result']
          console.log(this.postList)
          this.postList.forEach(resp=>{
            this.getStatus(resp.status)
          })
        })
      }else if(this.id == 7){
        let datas = {
          'userTypeId': 7
        }
        this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
          this.postList = resp['result']
          console.log(this.postList)
          this.postList.forEach(resp=>{
            this.getStatus(resp.status)
          })
        })
      }
    })
    if(this.local.get('userData1')[0].userTypeId == '3'){
      let datas = {
        'userTypeId': 6
      }
      this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
        this.postList = resp['result']
        console.log(this.postList)
        this.postList.forEach(resp=>{
          this.getStatus(resp.status)
        })
      })
    }else  if(this.local.get('userData1')[0].userTypeId == '2'){
      let datas = {
        'userTypeId': 5
      }
      this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
        this.postList = resp['result']
        console.log(this.postList)
        this.postList.forEach(resp=>{
          this.getStatus(resp.status)
        })
      })
        
    }else if(this.local.get('userData1')[0].userTypeId == '4'){
      let datas = {
        'userTypeId': 7
      }
      this.userService.dataPostApi(datas, AppSettings.AllPosts).then(resp=>{
        this.postList = resp['result']
        console.log(this.postList)
        this.postList.forEach(resp=>{
          this.getStatus(resp.status)
        })
      })
    }
    
      
      
    
  }
  getStatus(status){

		if(status == '0'){
			this.postStatus.push(0)
		}else if(status == '1'){
			this.postStatus.push(1);
		}else{
			this.postStatus.push(2);
		}
  }
  policyDetail(id,userTypeId){
		this.router.navigate(['/policyDetails'],{queryParams: {id: id,userTypeId: userTypeId}})
	}

}
