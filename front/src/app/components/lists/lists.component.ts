import {Component, OnInit} from '@angular/core';
import ListService from '../../services/list.service';
import {ListModel} from '../../models/list.model';

@Component({
  selector: 'app-lists',
  templateUrl: './lists.component.html',
  styleUrls: ['./lists.component.css']
})
export class ListsComponent implements OnInit {

  lists: EditableListModel[];

  constructor(private service: ListService) {
    this.lists = [];
  }

  ngOnInit(): void {
    this.service.getLists().subscribe(
      (l: EditableListModel[]) => this.lists = l.sort((l1, l2) => l1.id - l2.id)
    );
  }

  new() {
    this.lists.push({
      _edit: true,
      _edit_id: Math.floor(Math.random() * Math.floor(Number.MAX_SAFE_INTEGER)),
      name: ''
    });
  }

  edit(list: EditableListModel) {
    for (let i = 0; i < this.lists.length; i++) {
      if (this.lists[i].id === list.id) {
        this.lists[i] = {
          ...list,
          _edit: true
        };
        break;
      }
    }
  }

  create(list: EditableListModel) {
    this.service.createList(list).subscribe(l => {
      for (let i = 0; i < this.lists.length; i++) {
        if (this.lists[i]._edit_id === list._edit_id) {
          this.lists[i] = l as EditableListModel;
          break;
        }
      }
    });
  }

  update(list: EditableListModel) {
    this.service.updateList(list).subscribe(l => {
      for (let i = 0; i < this.lists.length; i++) {
        if (this.lists[i].id === list.id) {
          this.lists[i] = l as EditableListModel;
          break;
        }
      }
    });
  }

  delete(list: EditableListModel) {
    if (list.id) {
      this.service.deleteList(list).subscribe(() => {
          for (let i = 0; i < this.lists.length; i++) {
            if (this.lists[i].id === list.id) {
              this.lists.splice(i, 1);
              break;
            }
          }
        }
      );
    } else {
      for (let i = 0; i < this.lists.length; i++) {
        if (this.lists[i]._edit_id === list._edit_id) {
          this.lists.splice(i, 1);
          break;
        }
      }
    }
  }

  onChange(event: Event) {
    const target = event.target as HTMLInputElement;
    const split = target.id.split('_');
    const action = split[1];
    const id = parseInt(split[2], 10);
    if (!isNaN(id)) {
      for (let i = 0; i < this.lists.length; i++) {
        let idToCheck: number;
        if (action === 'update') {
          idToCheck = this.lists[i].id;
        } else {
          idToCheck = this.lists[i]._edit_id;
        }
        if (idToCheck === id) {
          this.lists[i] = {
            ...this.lists[i],
            name: target.value,
          };
          break;
        }
      }
    }
  }
}

interface EditableListModel extends ListModel {
  _edit: boolean;
  _edit_id: number;
}
