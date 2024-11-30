import { Injectable } from '@angular/core';
import {BehaviorSubject} from 'rxjs';
import {Ticket} from '../Models/ticket.model';
import {LoggingService} from './logging.service';

@Injectable({
  providedIn: 'root'
})
export class TicketPoolService {
  private ticketsSubject = new BehaviorSubject<Ticket[]>([]);
  tickets$ = this.ticketsSubject.asObservable();

  constructor(private loggingService: LoggingService) {}

  addTicket(ticket: Ticket) {
    const currentTickets = this.ticketsSubject.value;
    this.ticketsSubject.next([...currentTickets, ticket]);
    this.loggingService.log(`Ticket added: ${ticket.id} from Vendor ${ticket.vendorId}`);
  }

  removeTicket(ticketId: string): boolean {
    const currentTickets = this.ticketsSubject.value;
    const ticketIndex = currentTickets.findIndex(t => t.id === ticketId);

    if (ticketIndex !== -1) {
      const updatedTickets = currentTickets.filter(t => t.id !== ticketId);
      this.ticketsSubject.next(updatedTickets);
      this.loggingService.log(`Ticket purchased: ${ticketId}`);
      return true;
    }
    return false;
  }

  getAvailableTickets(): Ticket[] {
    return this.ticketsSubject.value;
  }
}
