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
	secondLocation: any;
	AdminImage: boolean;
	UserImage: boolean;
	PostImage: boolean;
	constructor(public router: Router,
				public local: LocalStorageService) { 
		this.numbers = 0;
		this.numberss = 0;
		this.number1 = 0;
		this.AdminImage = false;
		this.UserImage = false;	
		this.PostImage = false;
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
		this.secondLocation = this.locations.substring(0, this.locations.lastIndexOf("/") + 1)
		this.locations = this.locations.substring(this.locations.lastIndexOf("/") + 1, this.locations.length );
		console.log(this.secondLocation)
		this.secondLocation = this.secondLocation.substring(this.secondLocation.lastIndexOf("/") + 1, this.secondLocation.length - 5 );
		console.log(this.secondLocation)
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
		}else if(this.locations == 'policy' && this.secondLocation == 'User/'){
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
		}else if(this.locations == 'enterpenure' && this.secondLocation == 'User/'){
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
		}else if(this.locations == 'student' && this.secondLocation == 'User/'){
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
		}else if(this.locations == 'policy' && this.secondLocation == 'dmin/'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				$(".drop-show").css({"display":"block"}); 
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("admin1Policy")
				elem.click();
				this.AdminImage = true;
				$("#admin1Policy").addClass("active")
			},100)
		}else if(this.locations == 'enterpenure' && this.secondLocation == 'dmin/'){
			setTimeout(()=>{
				// var element = document.getElementById("studentTabs")
				// console.log(element)


				$(".drop-show").css({"display":"block"}); 
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("admin1Enterpernure")
				elem.click();
				// elem.addClass("active");
				$("#admin1Enterpernure").addClass("active")

				// $(".drop-showw").css({"display":"block"});
				// $(".side-menu ul li a").removeClass("active");
				// var elem = document.getElementById("admin1Enterpernure")
				// elem.click();
				// // elem.addClass("active");
				// $("#admin1Enterpernure").addClass("active")
			},100)
		}else if(this.locations == 'student' && this.secondLocation == 'dmin/'){
			setTimeout(()=>{


				$(".drop-show").css({"display":"block"}); 
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("admin1Students")
				elem.click();
				// elem.addClass("active");
				$("#admin1Students").addClass("active")
				// var element = document.getElementById("studentTabs")
				// console.log(element)
				// $(".drop-showw").css({"display":"block"});
				// $(".side-menu ul li a").removeClass("active");
				// var elem = document.getElementById("admin1Students")
				// elem.click();
				// // elem.addClass("active");
				// $("#admin1Students").addClass("active")
			},100)
		}
		else if(this.locations == 'allPosts?id=5'){
			setTimeout(()=>{


				$(".drop-show1").css({"display":"block"}); 
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("postPolicy")
				elem.click();
				// elem.addClass("active");
				$("#postPolicy").addClass("active")
				
			},200)
		}
		else if(this.locations == 'allPosts?id=6'){
			setTimeout(()=>{


				$(".drop-show1").css({"display":"block"}); 
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("postEnterpenure")
				elem.click();
				// elem.addClass("active");
				$("#postEnterpenure").addClass("active")
				
			},200)
		}
		else if(this.locations == 'allPosts?id=7'){
			setTimeout(()=>{


				$(".drop-show1").css({"display":"block"}); 
				$(".side-menu ul li a").removeClass("active");
				var elem = document.getElementById("postStudentss")
				elem.click();
				// elem.addClass("active");
				$("#postStudentss").addClass("active")
				
			},200)
		}
		

	}
	goTo(routes, event){
		if(routes == 'dashboard'){
			console.log("dahsboard")
			$(".side-menu ul li a").removeClass("active");
			$("#dashboarId").addClass("active");
			
			this.numbers = 1;
			this.UserImage = false;	
			$(".drop-showw").css({"display":"none"}); 

			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			this.AdminImage = false;

			this.number1 = 1;
			this.PostImage = false;
			$(".drop-show1").css({"display":"none"}); 
		}

		$(".side-menu ul li a").removeClass("active");
		console.log(event.target)

      	$(event.target).addClass("active");
		this.router.navigateByUrl('/'+routes)
	}

	showSLider(){
		this.AdminImage = false;
		this.PostImage = false;
		if(this.numbers == 0){
			this.numbers = 1;
			this.UserImage = false;	
			$(".drop-showw").css({"display":"none"}); 
			 
		}else{
			this.numbers = 0;
			
			$(".drop-showw").css({"display":"block"}); 
			
			$(".side-menu ul li a").removeClass("active");
			var elem = document.getElementById("userPolicy")
			elem.click();
			this.UserImage = true;	
			// elem.addClass("active");
			$("#userPolicy").addClass("active")


			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			this.AdminImage = false;

			this.number1 = 1;
			this.PostImage = false;
			$(".drop-show1").css({"display":"none"}); 
			
		}
	}
	showSLiders(){
		this.UserImage = false;	
		this.PostImage = false;
		if(this.numberss == 0){
			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			this.AdminImage = false;
			
			 
		}else{
			this.numberss = 0;
			$(".drop-show").css({"display":"block"}); 
			$(".side-menu ul li a").removeClass("active");
			var elem = document.getElementById("admin1Policy")
			elem.click();
			this.AdminImage = true;
			$("#admin1Policy").addClass("active")


			this.numbers = 1;
			this.UserImage = false;	
			$(".drop-showw").css({"display":"none"}); 

			this.number1 = 1;
			this.PostImage = false;
			$(".drop-show1").css({"display":"none"}); 
		}
	}
	showSLider1(){
		this.AdminImage = false;
		if(this.number1 == 0){
			this.number1 = 1;
			this.PostImage = false;
			$(".drop-show1").css({"display":"none"}); 
			 
		}else{
			this.number1 = 0;
			this.PostImage = true;
			$(".drop-show1").css({"display":"block"}); 
			$(".side-menu ul li a").removeClass("active");
			var elem = document.getElementById("postPolicy")
			elem.click();
			// elem.addClass("active");
			$("#postPolicy").addClass("active")

			this.numbers = 1;
			this.UserImage = false;	
			$(".drop-showw").css({"display":"none"}); 

			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			this.AdminImage = false;
		}
	}
	goToPost(id, event){
		$(".side-menu ul li a").removeClass("active");
		console.log(event.target)

      	$(event.target).addClass("active");
		this.router.navigate(['/allPosts'],{queryParams: {id: id}})
	}
	setting(event){
		this.AdminImage = false;
		this.UserImage = false;
		this.PostImage = false;	

			this.numbers = 1;
			this.UserImage = false;	
			$(".drop-showw").css({"display":"none"}); 

			this.numberss = 1;
			$(".drop-show").css({"display":"none"}); 
			this.AdminImage = false;

			this.number1 = 1;
			this.PostImage = false;
			$(".drop-show1").css({"display":"none"}); 

		$(".side-menu ul li a").removeClass("active");
		console.log(event.target)

      	$(event.target).addClass("active");
		console.log("hello setting")
		this.router.navigateByUrl('/setting')
	}
}
