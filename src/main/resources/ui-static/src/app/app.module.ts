import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {CompaniesComponent} from './componants/companies/companies.component';
import {ErrorComponent} from './componants/error/error.component';
import {HttpClientModule} from "@angular/common/http";
import {FormsModule} from "@angular/forms";
import { PagerComponent } from './componants/pager/pager.component';
import { CompanyFormComponent } from './componants/company-form/company-form.component';
import { SearchComponent } from './componants/search/search.component';

@NgModule({
  declarations: [
    AppComponent,
    CompaniesComponent,
    ErrorComponent,
    PagerComponent,
    CompanyFormComponent,
    SearchComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    FormsModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
