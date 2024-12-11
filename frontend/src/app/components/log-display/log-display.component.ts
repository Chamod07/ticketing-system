import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';
import { CardModule } from 'primeng/card';
import { ScrollPanelModule } from 'primeng/scrollpanel';
import { DatePipe, NgForOf } from '@angular/common';
import { LogEntry } from '../../models/log-entry.model';

/**
 * Component for displaying logs.
 */
@Component({
  selector: 'app-log-display',
  standalone: true,
  imports: [
    CardModule,
    ScrollPanelModule,
    NgForOf,
    DatePipe
  ],
  templateUrl: './log-display.component.html',
  styleUrls: ['./log-display.component.css']
})
export class LogDisplayComponent implements OnInit, OnDestroy {

  /**
   * Array to hold log entries.
   */
  logs: LogEntry[] = [];

  /**
   * Subscription for periodic polling.
   */
  private pollingSubscription?: Subscription;

  /**
   * Constructor to inject HttpClient.
   * @param http - HttpClient for making HTTP requests.
   */
  constructor(private http: HttpClient) {}

  /**
   * Lifecycle hook that is called after data-bound properties are initialized.
   * Sets up periodic polling to fetch logs.
   */
  ngOnInit(): void {
    // periodic polling
    this.pollingSubscription = interval(500).subscribe(() => {
      this.fetchLogs();
    });

    // fetch initially
    this.fetchLogs();
  }

  /**
   * Fetches the latest logs from the server.
   */
  fetchLogs(): void {
    this.http.get<LogEntry[]>('http://localhost:8081/api/v1/log/latest').subscribe({
      next: (data) => {
        this.logs = data; // Update logs
      },
      error: (error) => {
        console.error('Error fetching logs:', error);
      }
    });
  }

  /**
   * Clears the log entries.
   */
  clearLogs(): void {
    this.logs = [];
  }

  /**
   * Lifecycle hook that is called when the component is destroyed.
   * Unsubscribes from the polling subscription.
   */
  ngOnDestroy(): void {
    // unsubscribe from polling
    this.pollingSubscription?.unsubscribe();
  }
}
