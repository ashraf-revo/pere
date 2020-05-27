import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {CompaniesComponent} from "./componants/companies/companies.component";
import {ErrorComponent} from "./componants/error/error.component";


const routes: Routes = [
  {path: '', component: CompaniesComponent},
  {path: 'companies', component: CompaniesComponent},
  {path: '**', component: ErrorComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
