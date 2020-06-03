import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ITEMS_LINK, ListModel} from '../models/list.model';
import {map} from 'rxjs/operators';
import {ITEM_LIST_SELECTOR, ItemListModel, ItemModel} from '../models/item.model';
import {clear, SELF_LINK} from '../models/common.model';

@Injectable()
export default class ItemService {
  constructor(private http: HttpClient) {
  }

  getItemsForList(list: ListModel): Observable<ItemModel[]> {
    return this.http.get<ItemListModel>(list._links[ITEMS_LINK].href, {withCredentials: true}).pipe(map(
      (i) => i._embedded === undefined ? [] : i._embedded[ITEM_LIST_SELECTOR])
    );
  }

  creteItemForList(item: ItemModel, list: ListModel): Observable<ItemModel> {
    return this.http.post<ItemModel>(list._links[ITEMS_LINK].href, clear(item), {withCredentials: true});
  }

  updateItem(item: ItemModel): Observable<ItemModel> {
    return this.http.patch<ItemModel>(item._links[SELF_LINK].href, clear(item), {withCredentials: true});
  }

  deleteItem(item: ItemModel): Observable<void> {
    return this.http.delete<void>(item._links[SELF_LINK].href, {withCredentials: true});
  }

}
