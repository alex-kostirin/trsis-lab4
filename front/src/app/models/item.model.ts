import {Entity, ListResponse, Response} from './common.model';

export interface ItemModel extends Response, Entity {
  name?: string;
  count?: number;
  bought?: boolean;
}

export interface ItemListModel extends ListResponse<ItemModel> {

}

export const ITEM_LIST_SELECTOR = 'itemList';

export const LIST_LINK = 'lists';

export const ITEMS_LINK = 'items';
