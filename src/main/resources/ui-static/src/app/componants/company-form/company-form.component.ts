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
  beErrors: Map<string,string> = new Map<string, string>();

  constructor(private companiesService: CompaniesService) {
  }

  ngOnInit() {
  }

  success = it => {
    this.submitted = false;
    this.beErrors = new Map<string, string>();
    this.onLoad.emit("load")
  };
  error = error => {
    this.beErrors = error.error;
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
    this.beErrors = new Map<string, string>();
  }

  clearBeError(name: string) {
    delete this.beErrors[name];
  }
}
