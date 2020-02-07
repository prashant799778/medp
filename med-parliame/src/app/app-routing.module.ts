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



const routes: Routes = [
  { path: '',redirectTo: '/dashboard',pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard]},
  { path: 'policyDetails', component: PolicyDetaialsComponent, canActivate:[AuthGuard]},
  { path: 'profile', component: ProfileComponent, canActivate:[AuthGuard]},
  { path: 'allPosts', component: PostsComponent, canActivate:[AuthGuard]},
  { path: 'enterpenure', component: EnterpenuressComponent, canActivate:[AuthGuard]},
  { path: 'studentsList', component: StudentListsComponent, canActivate:[AuthGuard]},
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
