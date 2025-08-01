import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import {Scope} from "../../features/scopes/models/scope.model";
import {AccessKey} from "../../features/scope-details/models/access-key.model";

@Injectable({
  providedIn: 'root'
})
export class ScopesService {
  private readonly apiUrl = 'http://localhost:8080/api/scopes';

  constructor(private http: HttpClient) {}

  getScopes(search?: string): Observable<Scope[]> {
    let params = new HttpParams();
    if (search) {
      params = params.set('search', search);
      return this.http.get<Scope[]>(this.apiUrl, { params });
    }
    return this.http.get<Scope[]>(this.apiUrl);
  }

  createScope(scope: Partial<Scope>): Observable<Scope> {
    return this.http.post<Scope>(this.apiUrl, scope);
  }

  getScopeById(id: number): Observable<Scope> {
    return this.http.get<Scope>(`${this.apiUrl}/${id}`);
  }

  getAccessKeyById(id: number): Observable<AccessKey> {
    return this.http.get<AccessKey>(`${this.apiUrl}/${id}/access-key`);
  }

  setUsersToScope(scopeId: number, emails: String[]): Observable<void> {
    return this.http.put<void>(`${this.apiUrl}/${scopeId}/users`, emails);
  }
}
