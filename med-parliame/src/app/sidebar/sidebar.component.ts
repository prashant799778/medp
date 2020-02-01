import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
declare var $: any;

@Component({
  selector: 'app-sidebar',
  templateUrl: './sidebar.component.html',
  styleUrls: ['./sidebar.component.css']
})
export class SidebarComponent implements OnInit {
	numbers: number;
	numberss: number;
	constructor(public router: Router) { 
		this.numbers = 0;
		this.numbers = 1;
			
	}

	ngOnInit() {
	}
	goTo(routes){
		this.router.navigateByUrl('/'+routes)
	}

	showSLider(event, num){
		
		if(this.numbers == 0){
			this.numbers = 1;
			$(".drop-showw").css({"display":"none"}); 
			 
		}else{
			this.numbers = 0;
			$(".drop-showw").css({"display":"block"}); 
		}
	}
	showSLiders(event, num){
		
		if(this.numberss == 0){
			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			 
		}else{
			this.numberss = 0;
			$(".drop-show").css({"display":"block"}); 
		}
	}
}
