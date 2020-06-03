import {BrowserModule} from '@angular/platform-browser';
import {Injectable, NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {ListsComponent} from './components/lists/lists.component';
import {MatCardModule} from '@angular/material/card';
import {HTTP_INTERCEPTORS, HttpClientModule, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {MatListModule} from '@angular/material/list';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {ItemsComponent} from './components/items/items.component';
import {LoginComponent} from './components/login/login.component';
import ListService from './services/list.service';
import ItemService from './services/item.service';
import UserService from './services/user.service';

function getCookie(name) {
  const value = `; ${document.cookie}`;
  const parts = value.split(`; ${name}=`);
  if (parts.length === 2) {
    return parts.pop().split(';').shift();
  }
}

@Injectable()
export class XhrInterceptor implements HttpInterceptor {

  intercept(req: HttpRequest<any>, next: HttpHandler) {
    const csrf = getCookie('XSRF-TOKEN');
    if (csrf) {
      const xhr = req.clone({
        headers: req.headers.set('X-XSRF-TOKEN', csrf)
      });
      return next.handle(xhr);
    } else {
      return next.handle(req);
    }
  }
}

@NgModule({
  declarations: [
    AppComponent,
    ListsComponent,
    ItemsComponent,
    LoginComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    AppRoutingModule,
    MatCardModule,
    MatListModule,
    MatIconModule,
    MatButtonModule,
    MatInputModule,
    BrowserAnimationsModule
  ],
  providers: [[ListService, {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}],
    [ItemService, {provide: HTTP_INTERCEPTORS, useClass: XhrInterceptor, multi: true}],
    UserService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
