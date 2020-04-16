import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { AuthGuard } from './services/auth.guard';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PolicyComponent } from './admin/policy/policy.component';
import { StudentComponent } from './admin/student/student.component';
import { EnterpenureComponent } from './admin/enterpenure/enterpenure.component';
import { UserPolicyComponent } from './user/user-policy/user-policy.component';
import { UserStudentComponent } from './user/user-student/user-student.component';
import { UserEnterpenureComponent } from './user/user-enterpenure/user-enterpenure.component';
import { AddAdminComponent } from './admin/add-admin/add-admin.component';
import { PolicyDetaialsComponent } from './Component/policy-detaials/policy-detaials.component';
import { ProfileComponent } from './Component/profile/profile.component';
import { PostsComponent } from './Component/posts/posts.component';
import { EnterpenuressComponent } from './Component/enterpenuress/enterpenuress.component';
import { StudentListsComponent } from './Component/student-lists/student-lists.component';
import { CountryComponent } from './setting/country/country.component';
import { DoctorComponent } from './admin/doctor/doctor.component';
import { ProfessionalComponent } from './admin/professional/professional.component';
import { UserDoctorComponent } from './user/user-doctor/user-doctor.component';
import { UserProfessionalComponent } from './user/user-professional/user-professional.component';
import { NewsComponent } from './setting/news/news.component';
import { AnnouncementComponent } from './setting/announcement/announcement.component';
import { EventsComponent } from './setting/events/events.component';
import { GalleryComponent } from './setting/gallery/gallery.component';
import { CreateNewsComponent } from './news/create-news/create-news.component';
import { EditNewsComponent } from './news/edit-news/edit-news.component';
import { ViewGalleryComponent } from './gallery/view-gallery/view-gallery.component';
import { CreateEventComponent } from './event/create-event/create-event.component';
import { EditEventComponent } from './event/edit-event/edit-event.component';
import { ViewAnnocementComponent } from './annocement/view-annocement/view-annocement.component';
import { EditAnnocementComponent } from './annocement/edit-annocement/edit-annocement.component';
import { EditGalleryComponent } from './gallery/edit-gallery/edit-gallery.component';
import { OperationComponent } from './admin/operation/operation.component';
import { AccountVerificationComponent } from './account-verification/account-verification.component';
import { CreateMediaComponent } from './media/create-media/create-media.component';
import { EditMediaComponent } from './media/edit-media/edit-media.component';
import { CreateInfoComponent } from './info/create-info/create-info.component';
import { EditInfoComponent } from './info/edit-info/edit-info.component';
import { CreateAboutComponent } from './about/create-about/create-about.component';
import { EditAboutComponent } from './about/edit-about/edit-about.component';
import { UserDecisionComponent } from './user/user-decision/user-decision.component';
import { CreatePromisingInitiativeComponent } from './promising/create-promising-initiative/create-promising-initiative.component';
import { EditPromisingInitiativeComponent } from './promising/edit-promising-initiative/edit-promising-initiative.component';
import { ViewMarketInsightPostComponent } from './market-insight-post/view-market-insight-post/view-market-insight-post.component';
import { CreateMarketNewsComponent } from './market-news/create-market-news/create-market-news.component';
import { EditMarketNewsComponent } from './market-news/edit-market-news/edit-market-news.component';
import { CreateUpSkillComponent } from './up-skill/create-up-skill/create-up-skill.component';
import { EditUpSkillComponent } from './up-skill/edit-up-skill/edit-up-skill.component';
import { ReplyPostMarketComponent } from './market-insight-post/reply-post-market/reply-post-market.component';
import { EditParterComponent } from './our-partner/edit-parter/edit-parter.component';
import { ViewParterComponent } from './our-partner/view-parter/view-parter.component';
import { ViewContentComponent } from './user-content/view-content/view-content.component';
import { EditContentComponent } from './user-content/edit-content/edit-content.component';
import { CreateOperationAdminComponent } from './operation-admin/create-operation-admin/create-operation-admin.component';
import { EditOperationAdminComponent } from './operation-admin/edit-operation-admin/edit-operation-admin.component';
import { EventPostComponent } from './event-post/event-post.component';
import { ReplyEventPostComponent } from './event-post/reply-event-post.component';
import { EndorseComponent } from './news/endorse/endorse.component';
import { Endorse1Component } from './event/endorse1/endorse1.component';
import { CommentssComponent } from './news/commentss/commentss.component';
import { Commentss1Component } from './event/commentss1/commentss1.component';
import { NewsInterestComponent } from './news/news-interest/news-interest.component';
import { EventInterestComponent } from './event/event-interest/event-interest.component';
import { EnrolledComponent } from './up-skill/enrolled/enrolled.component';
import { NotificationComponent } from './notification/notification.component';

const routes: Routes = [
  { path: '',redirectTo: '/dashboard',pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard]},
  { path: 'policyDetails', component: PolicyDetaialsComponent, canActivate:[AuthGuard]},
  { path: 'profile', component: ProfileComponent, canActivate:[AuthGuard]},
  { path: 'allPosts', component: PostsComponent, canActivate:[AuthGuard]},
  { path: 'enterpenure', component: EnterpenuressComponent, canActivate:[AuthGuard]},
  { path: 'studentsList', component: StudentListsComponent, canActivate:[AuthGuard]},
  { path: 'AccountVerification', component: AccountVerificationComponent,canActivate:[AuthGuard]},
  { path: 'marketInsightPost', component: ViewMarketInsightPostComponent,canActivate:[AuthGuard]},
  { path: 'replyPostMarketing', component: ReplyPostMarketComponent,canActivate:[AuthGuard]},
  { path: 'eventPost', component: EventPostComponent,canActivate:[AuthGuard]},
  { path: 'eventReplyPost', component: ReplyEventPostComponent,canActivate:[AuthGuard]},
  { path: 'notification', component: NotificationComponent,canActivate:[AuthGuard]},
  { path: 'setting', 
    children: [                          //<---- child components declared here
      { path: '',redirectTo: '/Setting/add',pathMatch:'full'}, 
    
        {
            path:'add',
            component: CountryComponent,
            canActivate: [AuthGuard],
        },
        {
          path:'news',
          component: NewsComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'annocement',
        component: AnnouncementComponent,
        canActivate: [AuthGuard],
    },
    {
      path:'event',
      component: EventsComponent,
      canActivate: [AuthGuard],
  },
  {
    path:'gallery',
    component: GalleryComponent,
    canActivate: [AuthGuard],
  },
    
      
        
    ]
  },
  
  


  { path: 'news', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/news/createNews',pathMatch:'full'}, 
   
       {
          path:'createNews',
          component: CreateNewsComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editNews',
        component: EditNewsComponent,
        canActivate: [AuthGuard],
    },
    {
      path:'endorse',
      component: EndorseComponent,
      canActivate: [AuthGuard],
  },
  {
    path:'comment',
    component: CommentssComponent,
    canActivate: [AuthGuard],
},
{
  path:'interest',
  component: NewsInterestComponent,
  canActivate: [AuthGuard],
},
    
      
  ]
  },
  { path: 'upSkill', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/upSkill/createUpSkill',pathMatch:'full'}, 
   
       {
          path:'createUpSkill',
          component: CreateUpSkillComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editUpSkill',
        component: EditUpSkillComponent,
        canActivate: [AuthGuard],
    },
    {
      path:'enrolls',
      component: EnrolledComponent,
      canActivate: [AuthGuard],
  },
  
    
      
  ]
  },
  { path: 'marketInsight', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/marketInsight/createMarketInsight',pathMatch:'full'}, 
   
       {
          path:'createMarketInsight',
          component: CreateMarketNewsComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editMarketInsight',
        component: EditMarketNewsComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },


  { path: 'media', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/media/createMedia',pathMatch:'full'}, 
   
       {
          path:'createMedia',
          component: CreateMediaComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editMedia',
        component: EditMediaComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },


  { path: 'info', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/info/createInfo',pathMatch:'full'}, 
   
       {
          path:'createInfo',
          component: CreateInfoComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editInfo',
        component: EditInfoComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },

  { path: 'gallery', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/gallery/createGallery',pathMatch:'full'}, 
   
       {
          path:'createGallery',
          component: ViewGalleryComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editGallery',
        component: EditGalleryComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },

  { path: 'ourPartner', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/ourPartner/createPartner',pathMatch:'full'}, 
   
       {
          path:'createPartner',
          component: ViewParterComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editPartner',
        component: EditParterComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },

  { path: 'userContent', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/userContent/createContent',pathMatch:'full'}, 
   
       {
          path:'createContent',
          component: ViewContentComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editContent',
        component: EditContentComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },


  { path: 'operationDashboard', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/operationDashboard/createOperationDashboard',pathMatch:'full'}, 
   
       {
          path:'createOperationDashboard',
          component: CreateOperationAdminComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editOperationDashboard',
        component: EditOperationAdminComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },


  { path: 'about', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/about/createAbout',pathMatch:'full'}, 
   
       {
          path:'createAbout',
          component: CreateAboutComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editAbout',
        component: EditAboutComponent,
        canActivate: [AuthGuard],
       },
    
  
    
      
  ]
  },

  { path: 'event', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/event/createEvent',pathMatch:'full'}, 
   
       {
          path:'createEvent',
          component: CreateEventComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editEvent',
        component: EditEventComponent,
        canActivate: [AuthGuard],
    },
    {
      path:'endorse1',
      component: Endorse1Component,
      canActivate: [AuthGuard],
  },
  {
    path:'comment1',
    component: Commentss1Component,
    canActivate: [AuthGuard],
},
{
  path:'interest',
  component: EventInterestComponent,
  canActivate: [AuthGuard],
},
    
      
  ]
  },

  { path: 'promising', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/promising/createPromising',pathMatch:'full'}, 
   
       {
          path:'createPromising',
          component: CreatePromisingInitiativeComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editPromising',
        component: EditPromisingInitiativeComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },

  { path: 'announcement', 
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/announcement/createAnnouncement',pathMatch:'full'}, 
   
       {
          path:'createAnnouncement',
          component: ViewAnnocementComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'editAnnouncement',
        component: EditAnnocementComponent,
        canActivate: [AuthGuard],
    },
    
  
    
      
  ]
  },
  


  { path: 'Admin',
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/Admin/policy',pathMatch:'full'}, 
   
       {
          path:'policy',
          component: PolicyComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'student',
        component: StudentComponent,
        canActivate: [AuthGuard],
    },

    {
      path:'operation',
      component: OperationComponent,
      canActivate: [AuthGuard],
  },
    {
      path:'enterpenure',
      component: EnterpenureComponent,
      canActivate: [AuthGuard],
  },
  {
    path:'doctor',
    component: DoctorComponent,
    canActivate: [AuthGuard],
},
{
  path:'professional',
  component: ProfessionalComponent,
  canActivate: [AuthGuard],
},
  {
    path:'addAdmin',
    component: AddAdminComponent,
    canActivate: [AuthGuard],
}
    
      
  ]
  },
  { path: 'User',
  children: [                          //<---- child components declared here
    { path: '',redirectTo: '/User/policy',pathMatch:'full'}, 
   
       {
          path:'policy',
          component: UserPolicyComponent,
          canActivate: [AuthGuard],
      },
      {
        path:'student',
        component: UserStudentComponent,
        canActivate: [AuthGuard],
    },
    {
      path:'decision',
      component: UserDecisionComponent,
      canActivate: [AuthGuard],
  },
    {
      path:'enterpenure',
      component: UserEnterpenureComponent,
      canActivate: [AuthGuard],
  },
  {
    path:'doctor',
    component: UserDoctorComponent,
    canActivate: [AuthGuard],
},{
  path:'professional',
  component: UserProfessionalComponent,
  canActivate: [AuthGuard],
},
      
  ]
  },
]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
