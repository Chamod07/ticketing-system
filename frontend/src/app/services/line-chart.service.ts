/**
 * Service to handle line chart data operations.
 */
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, combineLatest, interval } from 'rxjs';
import { switchMap, map } from 'rxjs/operators';

@Injectable({
  providedIn: 'root',
})
export class LineChartService {
  private purchasedUrl = 'http://localhost:8081/api/v1/tickets/purchased';
  private releasedUrl = 'http://localhost:8081/api/v1/tickets/released';

  constructor(private http: HttpClient) {}

  /**
   * Fetches the count of purchased tickets.
   * @returns {Observable<number>} An observable containing the count of purchased tickets.
   */
  getTicketsPurchased(): Observable<number> {
    return this.http.get<number>(this.purchasedUrl);
  }

  /**
   * Fetches the count of released tickets.
   * @returns {Observable<number>} An observable containing the count of released tickets.
   */
  getTicketsReleased(): Observable<number> {
    return this.http.get<number>(this.releasedUrl);
  }

  /**
   * Polls both endpoints to get the count of purchased and released tickets at regular intervals.
   * @returns {Observable<{ purchased: number; released: number; time: string }>}
   * An observable containing the counts of purchased and released tickets along with a timestamp.
   */
  pollTicketData(): Observable<{ purchased: number; released: number; time: string }> {
    return interval(100).pipe(
      switchMap(() =>
        combineLatest([
          this.getTicketsPurchased(),
          this.getTicketsReleased(),
        ])
      ),
      map(([purchased, released]) => ({
        purchased,
        released,
        time: new Date().toLocaleTimeString(), // Add a timestamp
      }))
    );
  }
}
