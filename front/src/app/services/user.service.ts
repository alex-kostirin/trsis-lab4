import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {USER_URL} from './urls';
import {map} from 'rxjs/operators';

@Injectable()
export default class UserService {
  constructor(private http: HttpClient) {
  }

  authenticate(username: string, password: string): Observable<void> {
    const headers = new HttpHeaders({
      authorization: 'Basic ' + btoa(username + ':' + password)
    });
    return this.http.get<void>(USER_URL, {headers}).pipe(map(u => console.log(u)));
  }
}
