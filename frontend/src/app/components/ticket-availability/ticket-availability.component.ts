import { Component, OnInit, OnDestroy } from '@angular/core';
import {ProgressBarModule} from 'primeng/progressbar';
import {CardModule} from 'primeng/card';
import { TicketAvailabilityService} from '../../services/ticket-availability.service';
import { interval, Subscription } from 'rxjs';
import { switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-ticket-availability',
  standalone: true,
  templateUrl: './ticket-availability.component.html',
  imports: [
    ProgressBarModule,
    CardModule,
  ],
  styleUrl: './ticket-availability.component.css'
})
export class TicketAvailabilityComponent implements OnInit, OnDestroy {
  availableTickets = {
    current: 0,
    total: 0
  };

  private ticketSubscription: Subscription | undefined;
  private maxCapacitySubscription: Subscription | undefined;

  constructor(private ticketService: TicketAvailabilityService) {
  }

  ngOnInit(): void {
    this.maxCapacitySubscription = interval(500).pipe( // Polling interval of 5 seconds
      switchMap(() => this.ticketService.getMaxTicketCapacity())
    ).subscribe(
      (maxCapacity) => {
        this.availableTickets.total = maxCapacity; // Set the max ticket capacity
      },
      (error) => {
        console.error('Error fetching max ticket capacity', error);
      }
    );

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
    if (this.maxCapacitySubscription) {
      this.maxCapacitySubscription.unsubscribe(); // Unsubscribe on component destroy
    }
  }

  protected readonly Math = Math;
}
