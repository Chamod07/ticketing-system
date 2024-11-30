import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoggingService {
  private logsSubject = new BehaviorSubject<string[]>([]);
  logs$ = this.logsSubject.asObservable();

  log(message: string) {
    const currentLogs = this.logsSubject.value;
    const timestamp = new Date().toLocaleString();
    this.logsSubject.next([...currentLogs, `[${timestamp}] ${message}`]);
  }

  clearLogs() {
    this.logsSubject.next([]);
  }
}
