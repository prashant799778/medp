import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { NgSelectModule } from '@ng-select/ng-select';
import { AngularEditorModule } from '@kolkov/angular-editor';
import { MyDatePickerModule } from 'mydatepicker';
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
import { PolicyDetaialsComponent } from './Component/policy-detaials/policy-detaials.component';
import { SubAdminComponent } from './sub-admin/sub-admin.component';
import { ProfileComponent } from './Component/profile/profile.component';
import { PostsComponent } from './Component/posts/posts.component';
import { EnterpenuressComponent } from './Component/enterpenuress/enterpenuress.component';
import { StudentListsComponent } from './Component/student-lists/student-lists.component';
import { CountryComponent } from './setting/country/country.component';
import { UniversityComponent } from './setting/university/university.component';
import { DoctorComponent } from './admin/doctor/doctor.component';
import { ProfessionalComponent } from './admin/professional/professional.component';
// import { DoctorsComponent } from './user/doctors/doctors.component';
import { UserDoctorComponent } from './user/user-doctor/user-doctor.component';
import { UserProfessionalComponent } from './user/user-professional/user-professional.component';
import { NewsComponent } from './setting/news/news.component';
import { EventsComponent } from './setting/events/events.component';
import { GalleryComponent } from './setting/gallery/gallery.component';
import { SharedComponent } from './setting/shared/shared.component';
import { AnnouncementComponent } from './setting/announcement/announcement.component';
import { CreateNewsComponent } from './news/create-news/create-news.component';
import { EditNewsComponent } from './news/edit-news/edit-news.component';
import { CreateEventComponent } from './event/create-event/create-event.component';
import { EditEventComponent } from './event/edit-event/edit-event.component';
import { EditGalleryComponent } from './gallery/edit-gallery/edit-gallery.component';
import { ViewGalleryComponent } from './gallery/view-gallery/view-gallery.component';
import { ViewAnnocementComponent } from './annocement/view-annocement/view-annocement.component';
import { EditAnnocementComponent } from './annocement/edit-annocement/edit-annocement.component';
import { OperationComponent } from './admin/operation/operation.component';
import { AccountVerificationComponent } from './account-verification/account-verification.component';
// import { MediaComponent } from './media/media.component';
import { CreateMediaComponent } from './media/create-media/create-media.component';
import { EditMediaComponent } from './media/edit-media/edit-media.component';
import { EditInfoComponent } from './info/edit-info/edit-info.component';
import { CreateInfoComponent } from './info/create-info/create-info.component';
import { CreateAboutComponent } from './about/create-about/create-about.component';
import { EditAboutComponent } from './about/edit-about/edit-about.component';
import { UserDecisionComponent } from './user/user-decision/user-decision.component';
import { CreatePromisingInitiativeComponent } from './promising/create-promising-initiative/create-promising-initiative.component';
import { EditPromisingInitiativeComponent } from './promising/edit-promising-initiative/edit-promising-initiative.component';
import { PipesPipe } from './pipes.pipe';
import { SanitizeHtmlPipe } from './pipes.pipe';
import { ViewMarketInsightPostComponent } from './market-insight-post/view-market-insight-post/view-market-insight-post.component';
import { EditMarketNewsComponent } from './market-news/edit-market-news/edit-market-news.component';
import { CreateMarketNewsComponent } from './market-news/create-market-news/create-market-news.component';
import { CreateUpSkillComponent } from './up-skill/create-up-skill/create-up-skill.component';
import { EditUpSkillComponent } from './up-skill/edit-up-skill/edit-up-skill.component';
import { ReplyPostMarketComponent } from './market-insight-post/reply-post-market/reply-post-market.component';
import { CommonPipe } from './common.pipe';

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
    AddAdminComponent,
    PolicyDetaialsComponent,
    SubAdminComponent,
    ProfileComponent,
    PostsComponent,
    EnterpenuressComponent,
    StudentListsComponent,
    CountryComponent,
    UniversityComponent,
    DoctorComponent,
    ProfessionalComponent,
    // DoctorsComponent,
    UserDoctorComponent,
    UserProfessionalComponent,
    NewsComponent,
    EventsComponent,
    GalleryComponent,
    SharedComponent,
    AnnouncementComponent,
    CreateNewsComponent,
    EditNewsComponent,
    CreateEventComponent,
    EditEventComponent,
    EditGalleryComponent,
    ViewGalleryComponent,
    ViewAnnocementComponent,
    EditAnnocementComponent,
    OperationComponent,
    AccountVerificationComponent,
    // MediaComponent,
    CreateMediaComponent,
    EditMediaComponent,
    EditInfoComponent,
    CreateInfoComponent,
    CreateAboutComponent,
    EditAboutComponent,
    UserDecisionComponent,
    CreatePromisingInitiativeComponent,
    EditPromisingInitiativeComponent,
    PipesPipe,
    SanitizeHtmlPipe,
    ViewMarketInsightPostComponent,
    EditMarketNewsComponent,
    CreateMarketNewsComponent,
    CreateUpSkillComponent,
    EditUpSkillComponent,
    ReplyPostMarketComponent,
    CommonPipe
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    NgSelectModule,
    AngularEditorModule,
    AppRoutingModule,
    MyDatePickerModule,
    NgZorroAntdModule,
    DeviceDetectorModule.forRoot(),
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
