import { Component } from '@angular/core';
import {NgStyle} from '@angular/common';
import {Button} from 'primeng/button';
import {CardModule} from 'primeng/card';

@Component({
  selector: 'app-count-display',
  standalone: true,
  imports: [
    NgStyle,
    Button,
    CardModule
  ],
  templateUrl: './count-display.component.html',
  styleUrl: './count-display.component.css'
})
export class CountDisplayComponent {
  metrics = {
    activeVendors: 1,
    activeCustomers: 1,
    vipCustomers: 1
  };

  updateMetric(customers: string, b: boolean) {

  }
}
