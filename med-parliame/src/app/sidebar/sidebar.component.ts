import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'angular-web-storage';
declare var $: any;

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
	numbers: number;
	numberss: number;
	number1: number;
	superLogin: boolean;
	enterpernure: boolean;
	students: boolean;
	constructor(public router: Router,
				public local: LocalStorageService) { 
		this.numbers = 0;
		this.numbers = 0;
		this.numbers = 0;
			
	}

	ngOnInit() {
		
		if(this.local.get('userData2')){


			if(this.local.get('userData2').superLogin == 'yes'){
				this.superLogin = true;
			
			}else{
				this.superLogin = false
			}
		}

		if(this.local.get('userData1')){
console.log(this.local.get('userData1')[0].userTypeId)
			
			if(this.local.get('userData1')[0].userTypeId == '3'){
				this.enterpernure = true;
				this.students = false;
			
			}else if(this.local.get('userData1')[0].userTypeId == '4'){
				this.enterpernure = false;
				this.students = true;
			}
		}

	}
	goTo(routes){
		this.router.navigateByUrl('/'+routes)
	}

	showSLider(){
		
		if(this.numbers == 0){
			this.numbers = 1;
			$(".drop-showw").css({"display":"none"}); 
			 
		}else{
			this.numbers = 0;
			$(".drop-showw").css({"display":"block"}); 
		}
	}
	showSLiders(){
		
		if(this.numberss == 0){
			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			 
		}else{
			this.numberss = 0;
			$(".drop-show").css({"display":"block"}); 
		}
	}
	showSLider1(){
		
		if(this.numberss == 0){
			this.numberss = 1;
			$(".drop-show1").css({"display":"none"}); 
			 
		}else{
			this.numberss = 0;
			$(".drop-show1").css({"display":"block"}); 
		}
	}
}
