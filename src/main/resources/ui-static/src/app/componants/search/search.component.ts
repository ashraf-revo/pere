import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {Search} from "../../domain/search";

@Component({
  selector: 'pere-search',
  templateUrl: './search.component.html',
  styleUrls: ['./search.component.css']
})
export class SearchComponent implements OnInit {
  @Input()
  search: Search = new Search();
  @Output()
  onLoad: EventEmitter<string> = new EventEmitter();

  constructor() {
  }

  ngOnInit() {
  }

  filter() {
    this.search.page = 0;
    this.onLoad.emit("load")
  }

}
