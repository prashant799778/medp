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
	userName: any;
	locations: any;
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
			this.userName  = this.local.get('userData1')[0].userName
console.log(this.local.get('userData1')[0].userTypeId)
			
			if(this.local.get('userData1')[0].userTypeId == '3'){
				this.enterpernure = true;
				this.students = false;
			
			}else if(this.local.get('userData1')[0].userTypeId == '4'){
				this.enterpernure = false;
				this.students = true;
			}
		}

		this.locations = window.location.href
		console.log(this.locations)
		if(this.locations == 'http://localhost:5002/allPosts'){
			
			setTimeout(()=>{
				$(".side-menu ul li a").removeClass("active");
				$("#postTabs").addClass("active");
			},100)
		}else if(this.locations == 'http://localhost:5002/studentsList'){
			
			
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".side-menu ul li a").removeClass("active");
				$('#studentTabs').addClass('active')
			},100)
			
			// element.classList.addClass("active");
		}else if(this.locations == 'http://localhost:5002/enterpenure'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".side-menu ul li a").removeClass("active");
				$('#enterpenureTabs').addClass('active')
			},100)
		}else if(this.locations == 'http://localhost:5002/User/policy'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".drop-showw").css({"display":"block"});
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("userPolicy")
				elem.click();
				// elem.addClass("active");
				$("#userPolicy").addClass("active")
			},100)
		}else if(this.locations == 'http://localhost:5002/User/enterpenure'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".drop-showw").css({"display":"block"});
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("userEnterpenure")
				elem.click();
				// elem.addClass("active");
				$("#userEnterpenure").addClass("active")
			},100)
		}else if(this.locations == 'http://localhost:5002/User/student'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".drop-showw").css({"display":"block"});
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("userStudents")
				elem.click();
				// elem.addClass("active");
				$("#userStudents").addClass("active")
			},100)
		}else if(this.locations == 'http://localhost:5002/Admin/policy'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".drop-showw").css({"display":"block"});
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("admin1Policy")
				elem.click();
				// elem.addClass("active");
				$("#admin1Policy").addClass("active")
			},100)
		}else if(this.locations == 'http://localhost:5002/Admin/enterpenure'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".drop-showw").css({"display":"block"});
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("admin1Enterpernure")
				elem.click();
				// elem.addClass("active");
				$("#admin1Enterpernure").addClass("active")
			},100)
		}else if(this.locations == 'http://localhost:5002/Admin/student'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".drop-showw").css({"display":"block"});
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("admin1Students")
				elem.click();
				// elem.addClass("active");
				$("#admin1Students").addClass("active")
			},100)
		}
		

	}
	goTo(routes, event){
		if(routes == 'dashboard'){
			console.log("dahsboard")
			$(".side-menu ul li a").removeClass("active");
			$("#dashboarId").addClass("active");
		}

		$(".side-menu ul li a").removeClass("active");
		console.log(event.target)

      	$(event.target).addClass("active");
		this.router.navigateByUrl('/'+routes)
	}

	showSLider(){
		
		if(this.numbers == 0){
			this.numbers = 1;
			$(".drop-showw").css({"display":"none"}); 
			 
		}else{
			this.numbers = 0;
			$(".drop-showw").css({"display":"block"}); 
			
			$(".side-menu ul li a").removeClass("active");
			var elem = document.getElementById("userPolicy")
			elem.click();
			// elem.addClass("active");
			$("#userPolicy").addClass("active")
		}
	}
	showSLiders(){
		
		if(this.numberss == 0){
			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			
			
			 
		}else{
			this.numberss = 0;
			$(".drop-show").css({"display":"block"}); 
			$(".side-menu ul li a").removeClass("active");
			var elem = document.getElementById("admin1Policy")
			elem.click();
			// elem.addClass("active");
			$("#admin1Policy").addClass("active")
		}
	}
	showSLider1(){
		
		if(this.numberss == 0){
			this.numberss = 1;
			$(".drop-show1").css({"display":"none"}); 
			 
		}else{
			this.numberss = 0;
			$(".drop-show1").css({"display":"block"}); 
			$(".side-menu ul li a").removeClass("active");
			var elem = document.getElementById("postPolicy")
			elem.click();
			// elem.addClass("active");
			$("#postPolicy").addClass("active")
		}
	}
	goToPost(id, event){
		$(".side-menu ul li a").removeClass("active");
		console.log(event.target)

      	$(event.target).addClass("active");
		this.router.navigate(['/allPosts'],{queryParams: {id: id}})
	}
}
