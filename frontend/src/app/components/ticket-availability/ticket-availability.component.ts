import { Component, OnInit, OnDestroy } from '@angular/core';
import {ProgressBarModule} from 'primeng/progressbar';
import {CardModule} from 'primeng/card';
import { TicketAvailabilityService} from '../../services/ticket-availability.service';
import { Subscription } from 'rxjs';
import {Button} from 'primeng/button';

@Component({
  selector: 'app-ticket-availability',
  standalone: true,
  templateUrl: './ticket-availability.component.html',
  imports: [
    ProgressBarModule,
    CardModule,
    Button
  ],
  styleUrl: './ticket-availability.component.css'
})
export class TicketAvailabilityComponent implements OnInit, OnDestroy {
  availableTickets = {
    current: 0,
    total: 100
  };

  private ticketSubscription: Subscription | undefined;

  constructor(private ticketService: TicketAvailabilityService) {
  }

  ngOnInit(): void {
    this.ticketSubscription = this.ticketService.getAvailableTickets().subscribe(
      (count) => {
        this.availableTickets.current = count; // Update the current ticket count
      },
      (error) => {
        console.error('Error polling ticket availability', error);
      }
    );
  }

  ngOnDestroy(): void {
    if (this.ticketSubscription) {
      this.ticketSubscription.unsubscribe(); // Unsubscribe on component destroy
    }
  }
}
