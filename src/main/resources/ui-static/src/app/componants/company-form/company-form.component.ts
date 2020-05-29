import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {NgForm} from "@angular/forms";
import {Company} from "../../domain/company";
import {CompaniesService} from "../../services/companies.service";

@Component({
  selector: 'pere-company-form',
  templateUrl: './company-form.component.html',
  styleUrls: ['./company-form.component.css']
})
export class CompanyFormComponent implements OnInit {
  @Input()
  company: Company
  submitted: boolean = false;
  @Output()
  onLoad: EventEmitter<string> = new EventEmitter();

  constructor(private companiesService: CompaniesService) {
  }

  ngOnInit() {
  }

  success = it => {
    this.submitted = false;
    this.onLoad.emit("load")
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

  reset() {
    this.success("load")
  }
}
