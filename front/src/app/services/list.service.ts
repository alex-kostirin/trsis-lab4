import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {LIST_LIST_SELECTOR, ListListModel, ListModel} from '../models/list.model';
import {LISTS_URL} from './urls';
import {map} from 'rxjs/operators';
import {Observable} from 'rxjs';
import {clear, SELF_LINK} from '../models/common.model';


@Injectable()
export default class ListService {
  constructor(private http: HttpClient) {
  }

  getLists(): Observable<ListModel[]> {
    return this.http.get<ListListModel>(LISTS_URL, {withCredentials: true}).pipe(map(
      (l) => l._embedded === undefined ? [] : l._embedded[LIST_LIST_SELECTOR])
    );
  }

  getListById(id: number): Observable<ListModel> {
    return this.http.get<ListModel>(`${LISTS_URL}${id}/`, {withCredentials: true});
  }

  createList(list: ListModel): Observable<ListModel> {
    return this.http.post<ListModel>(LISTS_URL, clear(list), {withCredentials: true});
  }

  updateList(list: ListModel): Observable<ListModel> {
    return this.http.patch<ListModel>(list._links[SELF_LINK].href, clear(list), {withCredentials: true});
  }

  deleteList(list: ListModel): Observable<void> {
    return this.http.delete<void>(list._links[SELF_LINK].href, {withCredentials: true});
  }
}
