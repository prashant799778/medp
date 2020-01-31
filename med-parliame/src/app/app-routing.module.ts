import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AccountComponent } from './account/account.component';
import { AuthGuard } from './services/auth.guard';
import { DashboardComponent } from './dashboard/dashboard.component';
import { PolicyComponent } from './admin/policy/policy.component';
import { StudentComponent } from './admin/student/student.component';
import { EnterpenureComponent } from './admin/enterpenure/enterpenure.component';



const routes: Routes = [
  { path: '',redirectTo: '/dashboard',pathMatch: 'full'},
  { path: 'dashboard', component: DashboardComponent, canActivate:[AuthGuard]},
  
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
  }
    
      
  ]
  },
]
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
