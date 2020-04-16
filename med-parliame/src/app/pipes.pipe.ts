import { Pipe, PipeTransform } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';

@Pipe({
  name: 'pipes'
})
export class PipesPipe implements PipeTransform {

  transform(value: any, ...args: any[]): any {
    return null;
  }

}

@Pipe({
  name: 'sanitizeHtml'
})
export class SanitizeHtmlPipe implements PipeTransform {
  constructor(private sanitizer: DomSanitizer) { }
  transform(value: any): any {
    return this.sanitizer.bypassSecurityTrustHtml(value);
  }
}




@Pipe({
  name: 'userTypeId'
})
export class UserTypeId implements PipeTransform {
  // constructor(private sanitizer: DomSanitizer) { }
  transform(value: any): any {
   let data;
    if(value == 7){
      data = "Student"
    }else if(value == 6){
      data = "Entrepreneur" 
    }else if(value == 5){
      data = "Policy Maker" 
    }else if(value == 8){
      data = "Doctor" 
    }else if(value == 9){
      data = "Professional"  
    }else if(value == 13){
      data = "Decision Maker" 
    }
    return data
  }
}