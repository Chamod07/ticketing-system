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

  // Polling every half second
  getAvailableTickets(): Observable<number> {
    return interval(500).pipe( // Polling interval
      switchMap(() => this.http.get<number>(`${this.baseUrl}/availability`)) // Request ticket count
    );
  }

  getMaxTicketCapacity(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/max-capacity`);
  }
}
