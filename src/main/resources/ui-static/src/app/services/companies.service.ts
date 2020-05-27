import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {Company} from "../domain/company";
import {HttpClient} from "@angular/common/http";
import {Search} from "../domain/search";

@Injectable({
  providedIn: 'root'
})
export class CompaniesService {
  private _url = "api/v1/company/";

  constructor(private _httpClient: HttpClient) {
  }

  findAll(): Observable<Company[]> {
    return this._httpClient.get<Company[]>(this._url + "findAll");
  }

  search(search: Search) {
    return this._httpClient.post<Company[]>(this._url + "search", search);

  }
}
