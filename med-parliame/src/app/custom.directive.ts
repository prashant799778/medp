import { Directive, ElementRef, HostListener, Input, Renderer, OnInit, Optional, AfterViewInit } from '@angular/core';

@Directive({
  selector: '[appCustom]'
})
export class CustomDirective {

  constructor() { }

}

@Directive({
  selector: '[numberOnly]'
})
export class ChangeTextDirective {
  constructor(Element: ElementRef) {
    console.log("directive1123232 ::: ", Element)
  }
      
     // @Input() OnlyNumber: boolean;
     @HostListener('keydown', ['$event']) onKeyDown(event) {
       let e = <KeyboardEvent> event;
       //if (this.OnlyNumber) {
         if ([46, 8, 9, 27, 13, 110, 190].indexOf(e.keyCode) !== -1 ||
           // Allow: Ctrl+A
           (e.keyCode === 65 && (e.ctrlKey || e.metaKey)) ||
           // Allow: Ctrl+C
           (e.keyCode === 67 && (e.ctrlKey || e.metaKey)) ||
           // Allow: Ctrl+V
           (e.keyCode === 86 && (e.ctrlKey || e.metaKey)) ||
           // Allow: Ctrl+X
           (e.keyCode === 88 && (e.ctrlKey || e.metaKey)) ||
           // Allow: home, end, left, right
           (e.keyCode >= 35 && e.keyCode <= 39)) {
             // let it happen, don't do anything
             console.log(" allowwwwsssss",e.keyCode)
             return;
           }
           // Ensure that it is a number and stop the keypress
           else if((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
             console.log(" nootttt allow",e.keyCode)  
             e.preventDefault();
               
           }
         }
     }