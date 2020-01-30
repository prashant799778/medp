import { Injectable } from '@angular/core';
import { Router, CanActivate, CanActivateChild, CanLoad, Route, UrlSegment, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionStorageService, LocalStorageService } from 'angular-web-storage';
import { AuthsService } from './auths.service';
import { UserServiceService } from './user-service.service';
// import { UserService } from './user.service';
// import { SessionStorageService } from 'angular-web-storage';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate, CanActivateChild, CanLoad {
  KEY = 'value';
  constructor(
    private router: Router,
    
    public session: SessionStorageService,
    public local: LocalStorageService,
    public authservice: AuthsService,
    public userService: UserServiceService,
    // private authenticationService: AuthenticationService
) { }
  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      console.log(this.local)
      if (this.session.get(this.KEY)) {
        let isuserLoggedIn = this.local.get('userData1');
       
       
        this.userService.getSaveCustomer(isuserLoggedIn);
        return true;
    }else{
      let isuserLoggedIn = this.local.get('userData1');
      this.userService.getSaveCustomer(isuserLoggedIn);
      
      return true;
    }

    
    
  
     



   
  }
  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      // this.session
    return true;
  }
  canLoad(
    route: Route,
    segments: UrlSegment[]): Observable<boolean> | Promise<boolean> | boolean {
    return true;
  }
}
