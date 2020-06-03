import {Entity, ListResponse, Response} from './common.model';

export interface ListModel extends Response, Entity {
  name?: string;
}

export interface ListListModel extends ListResponse<ListModel> {
}

export const LIST_LIST_SELECTOR = 'listList';
export const LISTS_LINK = 'lists';
export const ITEMS_LINK = 'items';
