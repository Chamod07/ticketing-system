import { Component, OnInit } from '@angular/core';
import { Button } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CountDisplayService } from '../../services/count-display.service';
import { FormsModule } from "@angular/forms";
import { TooltipModule } from "primeng/tooltip";

/**
 * Component to display various counts such as active vendors, active customers, and VIP customers.
 */
@Component({
  selector: 'app-count-display',
  templateUrl: './count-display.component.html',
  standalone: true,
  imports: [
    Button,
    CardModule,
    FormsModule,
    TooltipModule,
  ],
  styleUrls: ['./count-display.component.css']
})
export class CountDisplayComponent implements OnInit {

  /**
   * Metrics object to hold the counts of active vendors, active customers, and VIP customers.
   */
  metrics = {
    activeVendors: 0,
    activeCustomers: 0,
    vipCustomers: 0
  };

  /**
   * Constructor to inject CountDisplayService.
   * @param countDisplayService - Service to fetch and update counts.
   */
  constructor(private countDisplayService: CountDisplayService) {}

  /**
   * Lifecycle hook that is called after data-bound properties of a directive are initialized.
   * Initializes the component by loading counts and subscribing to metrics updates.
   */
  ngOnInit() {
    this.loadCounts();
    this.countDisplayService.metrics$.subscribe((updatedMetrics) => {
      this.metrics = updatedMetrics;
    });
  }

  /**
   * Loads the counts for customers, vendors, and VIP customers from the service.
   */
  loadCounts() {
    this.countDisplayService.getCustomerCount().subscribe({
      next: (count) => {
        this.metrics.activeCustomers = count;
        this.updateServiceMetrics();
      },
      error: (err) => {
        console.error('Error fetching customer count', err);
      }
    });

    this.countDisplayService.getVendorCount().subscribe({
      next: (count) => {
        this.metrics.activeVendors = count;
        this.updateServiceMetrics();
      },
      error: (err) => {
        console.error('Error fetching vendor count', err);
      }
    });

    this.countDisplayService.getVipCustomerCount().subscribe({
      next: (count) => {
        this.metrics.vipCustomers = count;
        this.updateServiceMetrics();
      },
      error: (err) => {
        console.error('Error fetching VIP customer count', err);
      }
    });
  }

  /**
   * Updates the specified metric by incrementing or decrementing its value.
   * @param metric - The metric to update ('customers', 'vendors', 'vip').
   * @param increment - Whether to increment the metric.
   * @param decrement - Whether to decrement the metric.
   */
  updateMetric(metric: string, increment: boolean, decrement: boolean) {
    switch (metric) {
      case 'customers':
        if (increment) {
          this.countDisplayService.addCustomer().subscribe({
            next: (count) => {
              console.log('Received customer count:', count);
              // Ensure count is a valid number
              this.metrics.activeCustomers = count != null ? count : this.metrics.activeCustomers;
            },
            error: (err) => console.error('Error adding customer', err)
          });
        } else if (decrement) {
          this.countDisplayService.removeCustomer().subscribe({
            next: (count) => {
              console.log('Received customer count:', count);
              // Ensure count is a valid number
              this.metrics.activeCustomers = count != null ? count : this.metrics.activeCustomers;
            },
            error: (err) => console.error('Error removing customer', err)
          });
        }
        break;

      case 'vendors':
        if (increment) {
          this.countDisplayService.addVendor().subscribe({
            next: (count) => {
              console.log('Received vendor count:', count);
              // Ensure count is a valid number
              this.metrics.activeVendors = count != null ? count : this.metrics.activeVendors;
            },
            error: (err) => console.error('Error adding vendor', err)
          });
        } else if (decrement) {
          this.countDisplayService.removeVendor().subscribe({
            next: (count) => {
              console.log('Received vendor count:', count);
              // Ensure count is a valid number
              this.metrics.activeVendors = count != null ? count : this.metrics.activeVendors;
            },
            error: (err) => console.error('Error removing vendor', err)
          });
        }
        break;

      case 'vip':
        if (increment) {
          this.countDisplayService.addVipCustomer().subscribe({
            next: (count) => {
              console.log('Received VIP customer count:', count);
              // Ensure count is a valid number
              this.metrics.vipCustomers = count != null ? count : this.metrics.vipCustomers;
            },
            error: (err) => console.error('Error adding VIP customer', err)
          });
        } else if (decrement) {
          this.countDisplayService.removeVipCustomer().subscribe({
            next: (count) => {
              console.log('Received VIP customer count:', count);
              // Ensure count is a valid number
              this.metrics.vipCustomers = count != null ? count : this.metrics.vipCustomers;
            },
            error: (err) => console.error('Error removing VIP customer', err)
          });
        }
        break;
    }
    this.updateServiceMetrics();
  }

  /**
   * Updates the metrics in the service with the current metrics.
   */
  private updateServiceMetrics() {
    this.countDisplayService.updateMetrics(this.metrics);
  }
}
