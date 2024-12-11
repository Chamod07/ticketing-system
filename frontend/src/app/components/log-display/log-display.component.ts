import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';
import {CardModule} from 'primeng/card';
import {ScrollPanelModule} from 'primeng/scrollpanel';
import {DatePipe, NgForOf} from '@angular/common';

interface LogEntry {
  id: number;
  action: string;
  timestamp: string;
}

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

  logs: LogEntry[] = [];

  private pollingSubscription?: Subscription;

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    // periodic polling
    this.pollingSubscription = interval(500).subscribe(() => {
      this.fetchLogs();
    });

    // fetch initially
    this.fetchLogs();
  }

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

  clearLogs(): void {
    this.logs = [];
  }

  ngOnDestroy(): void {
    // unsubscribe from polling
    this.pollingSubscription?.unsubscribe();
  }
}
