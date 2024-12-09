import { Component } from '@angular/core';
import {ProgressBarModule} from 'primeng/progressbar';
import {CardModule} from 'primeng/card';

@Component({
  selector: 'app-ticket-availability',
  standalone: true,
  templateUrl: './ticket-availability.component.html',
  imports: [
    ProgressBarModule,
    CardModule
  ],
  styleUrl: './ticket-availability.component.css'
})
export class TicketAvailabilityComponent {
  availableTickets = {
    current: 200,
    total: 500
  };
}
