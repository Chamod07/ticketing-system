/**
 * Service to manage and display count metrics for vendors, customers, and VIP customers.
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CountDisplayService {

  // BehaviorSubject to hold the count metrics.
  private countDisplay = new BehaviorSubject({
    activeVendors: 0,
    activeCustomers: 0,
    vipCustomers: 0,
  });

  metrics$ = this.countDisplay.asObservable();

  /**
   * Updates the count metrics.
   * @param metrics - The new metrics to update.
   */
  updateMetrics(metrics: { activeVendors: number; activeCustomers: number; vipCustomers: number }) {
    this.countDisplay.next(metrics);
  }

  private baseUrl = 'http://localhost:8081/api/v1';

  constructor(private http: HttpClient) {}

  /**
   * Adds a new customer.
   * @returns An Observable of the new customer count.
   */
  addCustomer(): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/customer/add`, {});
  }

  /**
   * Removes a customer.
   * @returns An Observable of the new customer count.
   */
  removeCustomer(): Observable<number> {
    return this.http.delete<number>(`${this.baseUrl}/customer/remove`, {});
  }

  /**
   * Gets the current customer count.
   * @returns An Observable of the customer count.
   */
  getCustomerCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/customer/count`);
  }

  /**
   * Adds a new vendor.
   * @returns An Observable of the new vendor count.
   */
  addVendor(): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/vendor/add`, {});
  }

  /**
   * Removes a vendor.
   * @returns An Observable of the new vendor count.
   */
  removeVendor(): Observable<number> {
    return this.http.delete<number>(`${this.baseUrl}/vendor/remove`, {});
  }

  /**
   * Gets the current vendor count.
   * @returns An Observable of the vendor count.
   */
  getVendorCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/vendor/count`);
  }

  /**
   * Adds a new VIP customer.
   * @returns An Observable of the new VIP customer count.
   */
  addVipCustomer(): Observable<number> {
    return this.http.post<number>(`${this.baseUrl}/customer/add-vip`, {});
  }

  /**
   * Removes a VIP customer.
   * @returns An Observable of the new VIP customer count.
   */
  removeVipCustomer(): Observable<number> {
    return this.http.delete<number>(`${this.baseUrl}/customer/remove-vip`, {});
  }

  /**
   * Gets the current VIP customer count.
   * @returns An Observable of the VIP customer count.
   */
  getVipCustomerCount(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/customer/count-vip`);
  }
}
