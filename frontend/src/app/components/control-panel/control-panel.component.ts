import {Component, OnInit} from '@angular/core';
import { ToggleButtonModule } from 'primeng/togglebutton';
import { FormsModule } from '@angular/forms';
import { ToastModule } from 'primeng/toast';
import { MessageService } from 'primeng/api';
import { HttpClient } from '@angular/common/http';
import {NgIf} from '@angular/common';
import {ChipModule} from 'primeng/chip';
import {CountDisplayService} from '../../services/count-display.service';
import {TagModule} from 'primeng/tag';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [
    ToggleButtonModule,
    FormsModule,
    ToastModule,
    NgIf,
    ChipModule,
    TagModule,
  ],
  templateUrl: './control-panel.component.html',
  styleUrl: './control-panel.component.css',
  providers: [MessageService]
})
export class ControlPanelComponent implements OnInit {
  metrics = {activeCustomers: 0, activeVendors: 0};

  constructor(
    private messageService: MessageService,
    private http: HttpClient,
    private metricsService: CountDisplayService
    ) {}

  private baseUrl = 'http://localhost:8081/api/v1';
  systemStatus: string = 'Stopped';

  ngOnInit(): void {
    this.updateSystemState();
    this.metricsService.metrics$.subscribe((updatedMetrics) => {
      this.metrics = updatedMetrics;
    });
  }

  updateSystemState(): void {
    this.http.get(`${this.baseUrl}/system/state`, { responseType: 'text' }).subscribe(
      (state: string) => {
        this.systemStatus = state;
        console.log('System state:', state);
      },
      (error) => {
        console.error('Failed to fetch system state:', error);
      }
    );
  }

  startSystem() {
    if (this.metrics.activeVendors === 0 && this.metrics.activeCustomers === 0) {
      this.messageService.add({
        severity: 'error',
        summary: 'Cannot Start System',
        detail: 'The system cannot start without vendors or customers.',
      });
    } else {
      this.http.post(`${this.baseUrl}/system/start`, {}, {responseType: 'text'}).subscribe((response) => {
        console.log('API call success: Start system');
        this.systemStatus = 'Running';
        console.log(response);
        this.messageService.add({severity: 'success', summary: 'System Started'});
      }, (error) => {
        console.error('Failed to start system');
      });
    }
  }

  pauseSystem() {
    this.http.post(`${this.baseUrl}/system/pause`, {}, {responseType: 'text'}).subscribe((response) => {
      console.log('API call success: Pause system');
      this.systemStatus = 'Paused';
      console.log(response);
      this.messageService.add({ severity: 'info', summary: 'System Paused' });
    });
  }

  resumeSystem() {
    this.http.post(`${this.baseUrl}/system/resume`, {}, {responseType: 'text'}).subscribe((response) => {
      console.log('API call success: Resume system');
      this.systemStatus = 'Running';
      console.log(response);
      this.messageService.add({ severity: 'success', summary: 'System Resumed' });
    });
  }

  stopSystem() {
    this.http.post(`${this.baseUrl}/system/stop`, {}, { responseType: 'text' }).subscribe({
      next: () => {
        console.log('API call success: Stop system');
        this.systemStatus = 'Stopped';
        this.messageService.add({ severity: 'warn', summary: 'System Stopped' });

        // Update the counts from the backend
        this.updateCounts();
      },
      error: (error) => {
        console.error('Failed to stop system:', error);
      },
    });
  }

  updateCounts() {
    let updatedMetrics = { activeCustomers: 0, activeVendors: 0, vipCustomers: 0 };

    this.metricsService.getCustomerCount().subscribe({
      next: (customerCount) => {
        updatedMetrics.activeCustomers = customerCount;

        this.metricsService.getVendorCount().subscribe({
          next: (vendorCount) => {
            updatedMetrics.activeVendors = vendorCount;

            // Send the updated metrics to the service
            this.metricsService.updateMetrics(updatedMetrics);
          },
          error: (err) => console.error('Failed to fetch vendor count:', err),
        });
      },
      error: (err) => console.error('Failed to fetch customer count:', err),
    });
  }

}
