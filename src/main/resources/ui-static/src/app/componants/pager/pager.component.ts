import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Search} from "../../domain/search";

@Component({
  selector: 'pere-pager',
  templateUrl: './pager.component.html',
  styleUrls: ['./pager.component.css']
})
export class PagerComponent implements OnInit {
  @Input()
  search: Search;
  @Input()
  totalPages: number;
  @Output()
  onLoad: EventEmitter<string> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  next() {
    if (this.search.page + 1 < this.totalPages) {
      this.search.page++;
      this.load()
    }
  }

  prev() {
    if (this.search.page > 0) {
      this.search.page--;
      this.load()
    }
  }

  load() {
    this.onLoad.emit("load")
  }
}
