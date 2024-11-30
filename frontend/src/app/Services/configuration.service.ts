import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Configuration} from '../Models/configuration.model';

@Injectable({
  providedIn: 'root'
})
export class ConfigurationService {
  private configSubject = new BehaviorSubject<Configuration>({
    totalTickets: 100,
    ticketReleaseRate: 5,
    customerRetrievalRate: 3,
    maxTicketCapacity: 500
  });

  config = this.configSubject.asObservable();

  updateConfiguration(config: Configuration) {
    this.configSubject.next(config);
  }

  validateConfiguration(config: Configuration): boolean {
    return (
      config.totalTickets > 0 &&
      config.ticketReleaseRate > 0 &&
      config.customerRetrievalRate > 0 &&
      config.maxTicketCapacity >= config.totalTickets
    );
  }

  saveConfigToLocalStorage(config: Configuration) {
    localStorage.setItem('ticketSystemConfig', JSON.stringify(config));
  }

  loadConfigFromLocalStorage(): Configuration | null {
    const savedConfig = localStorage.getItem('ticketSystemConfig');
    return savedConfig ? JSON.parse(savedConfig) : null;
  }
}
