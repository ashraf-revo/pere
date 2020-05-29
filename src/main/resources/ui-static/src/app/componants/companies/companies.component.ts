import {Component, OnInit} from '@angular/core';
import {CompaniesService} from "../../services/companies.service";
import {Search} from "../../domain/search";
import {Company} from "../../domain/company";
import {PropertyDirection} from "../../domain/property-direction.enum";
import {Direction} from "../../domain/direction.enum";
import {Page} from "../../domain/page";

@Component({
  selector: 'pere-companies',
  templateUrl: './companies.component.html',
  styleUrls: ['./companies.component.css']
})
export class CompaniesComponent implements OnInit {
  search: Search = new Search();
  companies: Page<Company>;
  selectedCompany: Company = new Company();

  constructor(private companiesService: CompaniesService) {
    this.search.size = 5;
    this.search.page = 0;
  }

  ngOnInit() {
    this.load("load");
  }


  load($event: string) {
    this.selectedCompany = new Company();
    this.companiesService.search(this.search).subscribe(it => this.companies = it, this.error);
  }

  setCompany(c: Company) {
    this.selectedCompany = c;
  }

  delete(id: number) {
    this.companiesService.delete(id).subscribe(it => this.load("load"), this.error)
  }

  error = error => {
    console.log("error")
    console.log(error)
  };

  setFilter(id: string) {
    this.search.property = PropertyDirection[id];
    this.search.direction = (this.search.direction == Direction.DESC ? Direction.ASC : Direction.DESC);
    this.load("load");
  }
}
