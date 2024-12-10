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

  // tickets purchased count
  getTicketsPurchased(): Observable<number> {
    return this.http.get<number>(this.purchasedUrl);
  }

  // tickets released count
  getTicketsReleased(): Observable<number> {
    return this.http.get<number>(this.releasedUrl);
  }

  // Poll both endpoints
  pollTicketData(): Observable<{ purchased: number; released: number; time: string }> {
    return interval(500).pipe(
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
