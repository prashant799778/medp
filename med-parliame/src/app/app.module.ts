import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule, HttpClient, HTTP_INTERCEPTORS }    from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AccountComponent } from './account/account.component';
import { DashboardComponent } from './dashboard/dashboard.component';
import { DeviceDetectorModule } from 'ngx-device-detector';
import { AdminComponent } from './Component/admin/admin.component';
import { EnterpenureComponent } from './admin/enterpenure/enterpenure.component';
import { StudentComponent } from './admin/student/student.component';
import { PolicyComponent } from './admin/policy/policy.component';
import { SidebarComponent } from './sidebar/sidebar.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { NgZorroAntdModule } from 'ng-zorro-antd';
import { UserPolicyComponent } from './user/user-policy/user-policy.component';
import { UserStudentComponent } from './user/user-student/user-student.component';
import { UserEnterpenureComponent } from './user/user-enterpenure/user-enterpenure.component';
import { AddAdminComponent } from './admin/add-admin/add-admin.component';

@NgModule({
  declarations: [
    AppComponent,
    AccountComponent,
    DashboardComponent,
    AdminComponent,
    EnterpenureComponent,
    StudentComponent,
    PolicyComponent,
    SidebarComponent,
    HeaderComponent,
    FooterComponent,
    UserPolicyComponent,
    UserStudentComponent,
    UserEnterpenureComponent,
    AddAdminComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    AppRoutingModule,
    NgZorroAntdModule,
    DeviceDetectorModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
