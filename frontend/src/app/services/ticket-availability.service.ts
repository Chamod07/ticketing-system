/**
 * Service to handle ticket availability operations.
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { interval } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class TicketAvailabilityService {
  private baseUrl = 'http://localhost:8081/api/v1/tickets';

  constructor(private http: HttpClient) {}

  /**
   * Polls the server every half second to get the number of available tickets.
   * @returns {Observable<number>} An observable that emits the number of available tickets.
   */
  getAvailableTickets(): Observable<number> {
    return interval(500).pipe( // Polling interval
      switchMap(() => this.http.get<number>(`${this.baseUrl}/availability`)) // Request ticket count
    );
  }

  /**
   * Gets the maximum ticket capacity from the server.
   * @returns {Observable<number>} An observable that emits the maximum ticket capacity.
   */
  getMaxTicketCapacity(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/max-capacity`);
  }
}
