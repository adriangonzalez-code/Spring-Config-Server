import { Injectable } from '@angular/core';
import { Property } from "../../features/scope-details/models/property.model";
import { Observable } from "rxjs";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PropertyService {

  private readonly apiUrl = 'http://localhost:8080/api/properties';

  constructor(private http: HttpClient) {}

  getProperties(scopeId: number): Observable<Property[]> {
    return this.http.get<Property[]>(`${this.apiUrl}/${scopeId}/scope`);
  }

  createProperty(scopeId: number, property: Property[]): Observable<Property[]> {
    return this.http.put<Property[]>(`${this.apiUrl}/${scopeId}/scope`, property);
  }
}
