export interface Entity {
  id?: number;
}

export interface ListResponse<T extends Response> {
  _embedded: {
    [s: string]: T[]
  };
  _links: Links;
}

export interface Response {
  _links?: Links;
}

export interface Links {
  [s: string]: Link;
}

export interface Link {
  href: string;
}

export const SELF_LINK = 'self';

export const clear = <T extends any>(l: T): T => {
  const ret: any = {};
  for (const prop in l) {
    if ( l.hasOwnProperty(prop) && !prop.startsWith('_')){
      ret[prop] = l[prop];
    }
  }
  return ret as T;
};
