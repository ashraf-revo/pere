import {Component, OnInit} from '@angular/core';
import {CompaniesService} from "../../services/companies.service";
import {Search} from "../../domain/search";
import {Company} from "../../domain/company";
import {NgForm} from "@angular/forms";
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
  company: Company = new Company();
  companies: Page<Company>;
  submitted: boolean = false;

  constructor(private companiesService: CompaniesService) {
    this.search.size = 5;
    this.search.page = 0;
  }

  ngOnInit() {
    this.load();
  }

  next() {
    if (this.search.page + 1 < this.companies.totalPages) {
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
    this.companiesService.search(this.search).subscribe(it => {
      this.companies = it;
    });
  }

  success = it => {
    this.company = new Company();
    this.submitted = false;
    this.load()
  };
  error = error => {
    console.log("error")
    console.log(error)
  };

  save(form: NgForm) {
    this.submitted = true;
    if (form.valid) {
      if (this.company.id)
        this.companiesService.update(this.company).subscribe(this.success, this.error)
      else
        this.companiesService.save(this.company).subscribe(this.success, this.error)
    }
  }

  setCompany(c: Company) {
    Object.assign(this.company, c);
  }

  delete(id: number) {
    this.companiesService.delete(id).subscribe(this.success, this.error)
  }

  setFilter(id: string) {
    this.search.property = PropertyDirection[id];
    this.search.direction = (this.search.direction == Direction.DESC ? Direction.ASC : Direction.DESC);
    this.load();
  }

  filter() {
    this.search.page = 0;
    this.load();
  }
}
