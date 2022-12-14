import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RegisterComponent } from './register/register.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component';
import { ProfileComponent } from './profile/profile.component';
import { SearchbookComponent } from './searchbook/searchbook.component';
import { SubscribedbooksComponent } from './subscribedbooks/subscribedbooks.component';
import { CreatebookComponent } from './createbook/createbook.component';
import { CreatedbooksComponent } from './createdbooks/createdbooks.component';

const routes: Routes = [
  { path: 'home', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'profile', component: ProfileComponent },
  { path: 'search', component: SearchbookComponent },
  { path: 'subscribedbooks', component: SubscribedbooksComponent },
  {path: 'createbook',component:CreatebookComponent},
  {path: 'createdbooks',component:CreatedbooksComponent},
  { path: '', redirectTo: 'home', pathMatch: 'full' }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
