import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { interval } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class TicketAvailabilityService {
  private baseUrl = 'http://localhost:8081/api/v1/system/available-tickets';

  constructor(private http: HttpClient) {}

  // Polling every second
  getAvailableTickets(): Observable<number> {
    return interval(500).pipe( // Polling interval
      switchMap(() => this.http.get<number>(this.baseUrl)) // Request ticket count
    );
  }
}
