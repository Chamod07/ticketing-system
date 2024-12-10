import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CountDisplayService {
  private countDisplay = new BehaviorSubject({
    activeVendors: 0,
    activeCustomers: 0,
    vipCustomers: 0,
  });

  metrics$ = this.countDisplay.asObservable();

  updateMetrics(metrics: { activeVendors: number; activeCustomers: number; vipCustomers: number }) {
    this.countDisplay.next(metrics);
  }

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
