import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CountDisplayService {
  private baseUrl = 'http://localhost:8081/api/v1';

  constructor(private http: HttpClient) {}

  // Customer methods
  addCustomer(): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/customer/add`, {});
  }

  removeCustomer(): Observable<number> {
    return this.http.delete<number>(`${this.baseUrl}/customer/remove`, {});
  }

  getCustomerCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/customer/count`);
  }

  // Vendor methods
  addVendor(): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/vendor/add`, {});
  }

  removeVendor(): Observable<number> {
    return this.http.delete<number>(`${this.baseUrl}/vendor/remove`, {});
  }

  getVendorCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/vendor/count`);
  }
}
