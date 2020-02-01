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



const routes: Routes = [
  { path: '',redirectTo: '/dashboard',pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard]},
  { path: 'policyDetails', component: PolicyDetaialsComponent, canActivate:[AuthGuard]},
  { path: 'profile', component: ProfileComponent, canActivate:[AuthGuard]},
  
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
      path:'enterpenure',
      component: EnterpenureComponent,
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
  }
    
      
  ]
  },
]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
