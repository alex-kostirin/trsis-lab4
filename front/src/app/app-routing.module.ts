import {NgModule} from '@angular/core';
import {Routes, RouterModule} from '@angular/router';
import {ListsComponent} from './components/lists/lists.component';
import {ItemsComponent} from './components/items/items.component';
import {LoginComponent} from './components/login/login.component';


const routes: Routes = [
  {path: '', redirectTo: '/lists', pathMatch: 'full'},
  {path: 'login', pathMatch: 'full', component: LoginComponent},
  {path: 'lists', pathMatch: 'full', component: ListsComponent},
  {path: 'lists/:id', pathMatch: 'full', component: ItemsComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
