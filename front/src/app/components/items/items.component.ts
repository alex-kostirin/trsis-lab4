import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import ItemService from '../../services/item.service';
import ListService from '../../services/list.service';
import {ListModel} from '../../models/list.model';
import {switchMap} from 'rxjs/operators';
import {ItemModel} from '../../models/item.model';

@Component({
  selector: 'app-items',
  templateUrl: './items.component.html',
  styleUrls: ['./items.component.css']
})
export class ItemsComponent implements OnInit {

  list: ListModel;
  items: EditableItemModel[];

  constructor(private route: ActivatedRoute,
              private service: ItemService,
              private listService: ListService) {
  }

  ngOnInit(): void {
    const listId = parseInt(this.route.snapshot.paramMap.get('id'), 10);
    this.listService.getListById(listId).pipe(switchMap(l => {
        this.list = l;
        return this.service.getItemsForList(l);
      })
    ).subscribe((i: EditableItemModel[]) => this.items = i);
  }

  new() {
    this.items.push({
      _edit: true,
      _edit_id: Math.floor(Math.random() * Math.floor(Number.MAX_SAFE_INTEGER)),
      name: '',
      count: 0,
      bought: false,
    });
  }

  edit(list: EditableItemModel) {
    for (let i = 0; i < this.items.length; i++) {
      if (this.items[i].id === list.id) {
        this.items[i] = {
          ...list,
          _edit: true
        };
        break;
      }
    }
  }


  create(item: EditableItemModel) {
    this.service.creteItemForList(item, this.list).subscribe(it => {
      for (let i = 0; i < this.items.length; i++) {
        if (this.items[i]._edit_id === item._edit_id) {
          this.items[i] = it as EditableItemModel;
          break;
        }
      }
    });
  }

  update(item: EditableItemModel) {
    this.service.updateItem(item).subscribe(it => {
      for (let i = 0; i < this.items.length; i++) {
        if (this.items[i].id === item.id) {
          this.items[i] = it as EditableItemModel;
          break;
        }
      }
    });
  }

  delete(item: EditableItemModel) {
    if (item.id) {
      this.service.deleteItem(item).subscribe(() => {
          for (let i = 0; i < this.items.length; i++) {
            if (this.items[i].id === item.id) {
              this.items.splice(i, 1);
              break;
            }
          }
        }
      );
    } else {
      for (let i = 0; i < this.items.length; i++) {
        if (this.items[i]._edit_id === item._edit_id) {
          this.items.splice(i, 1);
          break;
        }
      }
    }
  }

  done(item: EditableItemModel) {
    this.service.updateItem({... item, bought: true}).subscribe(it => {
      for (let i = 0; i < this.items.length; i++) {
        if (this.items[i].id === item.id) {
          this.items[i] = it as EditableItemModel;
          break;
        }
      }
    });
  }

  onNameChange(event: Event) {
    const target = event.target as HTMLInputElement;
    const split =  target.id.split('_');
    const action = split[1];
    const id = parseInt(split[2], 10);
    if (!isNaN(id)) {
      for (let i = 0; i < this.items.length; i++) {
        let idToCheck: number;
        if (action === 'update') {
          idToCheck = this.items[i].id;
        } else {
          idToCheck = this.items[i]._edit_id;
        }
        if (idToCheck === id) {
          this.items[i] = {
            ...this.items[i],
            name: target.value,
          };
          break;
        }
      }
    }
  }

  onCountChange(event: Event) {
    const target = event.target as HTMLInputElement;
    const split =  target.id.split('_');
    const action = split[1];
    const id = parseInt(split[2], 10);
    if (!isNaN(id)) {
      for (let i = 0; i < this.items.length; i++) {
        let idToCheck: number;
        if (action === 'update') {
          idToCheck = this.items[i].id;
        } else {
          idToCheck = this.items[i]._edit_id;
        }
        if (idToCheck === id) {
          this.items[i] = {
            ...this.items[i],
            count: parseInt(target.value, 10),
          };
          break;
        }
      }
    }
  }

  isValidItem(item: EditableItemModel) {
    return item.name.length > 0 && !isNaN(item.count);
  }

}

interface EditableItemModel extends ItemModel {
  _edit: boolean;
  _edit_id: number;
}
