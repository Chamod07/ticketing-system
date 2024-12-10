import { Component, OnInit } from '@angular/core';
import { Button } from 'primeng/button';
import { CardModule } from 'primeng/card';
import { CountDisplayService } from '../../services/count-display.service';

@Component({
  selector: 'app-count-display',
  templateUrl: './count-display.component.html',
  standalone: true,
  imports: [
    Button,
    CardModule,
  ],
  styleUrls: ['./count-display.component.css']
})
export class CountDisplayComponent implements OnInit {

  metrics = {
    activeVendors: 0,
    activeCustomers: 0,
    vipCustomers: 0
  };

  constructor(private metricsService: CountDisplayService) {}

  ngOnInit() {
    this.loadCounts();
  }

  loadCounts() {
    this.metricsService.getCustomerCount().subscribe({
      next: (count) => {
        this.metrics.activeCustomers = count;
      },
      error: (err) => {
        console.error('Error fetching customer count', err);
      }
    });

    this.metricsService.getVendorCount().subscribe({
      next: (count) => {
        this.metrics.activeVendors = count;
      },
      error: (err) => {
        console.error('Error fetching vendor count', err);
      }
    });
  }

  updateMetric(metric: string, increment: boolean, decrement: boolean) {
    switch (metric) {
      case 'customers':
        if (increment) {
          this.metricsService.addCustomer().subscribe({
            next: (count) => {
              console.log('Received customer count:', count);
              // Ensure count is a valid number
              this.metrics.activeCustomers = count != null ? count : this.metrics.activeCustomers;
            },
            error: (err) => console.error('Error adding customer', err)
          });
        } else if (decrement) {
          this.metricsService.removeCustomer().subscribe({
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
          this.metricsService.addVendor().subscribe({
            next: (count) => {
              console.log('Received vendor count:', count);
              // Ensure count is a valid number
              this.metrics.activeVendors = count != null ? count : this.metrics.activeVendors;
            },
            error: (err) => console.error('Error adding vendor', err)
          });
        } else if (decrement) {
          this.metricsService.removeVendor().subscribe({
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
          this.metrics.vipCustomers++;
        } else if (decrement && this.metrics.vipCustomers > 0) {
          this.metrics.vipCustomers--;
        }
        break;
    }
  }
}
