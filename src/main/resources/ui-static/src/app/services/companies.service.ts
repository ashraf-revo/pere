import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Company} from "../domain/company";
import {HttpClient} from "@angular/common/http";
import {Search} from "../domain/search";
import {Page} from "../domain/page";

@Injectable({
  providedIn: 'root'
})
export class CompaniesService {
  private _url = "api/v1/company/";

  constructor(private _httpClient: HttpClient) {
  }

  findOne(id: string): Observable<Company> {
    return this._httpClient.get<Company>(this._url + id);
  }

  findAll(): Observable<Company[]> {
    return this._httpClient.get<Company[]>(this._url + "findAll");
  }

  search(search: Search): Observable<Page<Company>> {
    return this._httpClient.post<Page<Company>>(this._url + "search", search);
  }

  save(company: Company): Observable<Company> {
    return this._httpClient.post<Company>(this._url, company);
  }

  update(company: Company): Observable<Company> {
    return this._httpClient.put<Company>(this._url+company.id, company);
  }

  delete(id: number): Observable<Object> {
    return this._httpClient.delete(this._url + id);
  }
}
