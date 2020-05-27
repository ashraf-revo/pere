import {Component, OnInit} from '@angular/core';
import {CompaniesService} from "../../services/companies.service";
import {Search} from "../../domain/search";
import {Company} from "../../domain/company";

@Component({
  selector: 'pere-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit {
  search: Search = new Search();

  constructor(private companiesService: CompaniesService) {
    this.search.size = 5;
  }

  ngOnInit() {
    this.load();
  }

  next() {
    this.search.page++;
    this.load()
  }

  prev() {
    this.search.page--;
    this.load()
  }

  load() {
    this.companiesService.search(this.search).subscribe(it => {
      this.companies = it;
    });
  }

  companies: Company[] = [];
}
