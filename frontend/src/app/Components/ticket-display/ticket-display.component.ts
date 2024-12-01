import { Component, OnInit } from '@angular/core';
import { TicketPoolService } from '../../Services/ticket-pool.service';
import { Ticket } from '../../Models/ticket.model';
import {CurrencyPipe, DatePipe} from '@angular/common';

@Component({
  selector: 'app-ticket-display',
  templateUrl: './ticket-display.component.html',
  standalone: true,
  imports: [
    CurrencyPipe,
    DatePipe
  ],
  styleUrls: ['./ticket-display.component.css']
})
export class TicketDisplayComponent implements OnInit {
  tickets: Ticket[] = [];

  constructor(private ticketPoolService: TicketPoolService) {}

  ngOnInit() {
    this.ticketPoolService.tickets$.subscribe(
      tickets => this.tickets = tickets
    );
  }
}
