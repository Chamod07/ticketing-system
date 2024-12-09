import { Component } from '@angular/core';
import {Button} from 'primeng/button';
import {CardModule} from 'primeng/card';

@Component({
  selector: 'app-count-display',
  templateUrl: './count-display.component.html',
  standalone: true,
  imports: [
    Button,
    CardModule
  ],
  styleUrls: ['./count-display.component.css']
})
export class CountDisplayComponent {
  metrics = {
    activeVendors: 1,
    activeCustomers: 1,
    vipCustomers: 1
  };

  updateMetric(metric: string, increment: boolean) {
    // Implement the logic to update the corresponding metric
  }
}
