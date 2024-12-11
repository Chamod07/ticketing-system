import {Component, inject, OnInit} from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
  AbstractControl,
  ValidatorFn
} from '@angular/forms';
import { CommonModule } from '@angular/common';
import { MessageService } from 'primeng/api';
import { CardModule } from 'primeng/card';
import { ButtonModule } from 'primeng/button';
import { InputTextModule } from 'primeng/inputtext';
import { ToastModule } from 'primeng/toast';
import { HttpClient } from '@angular/common/http';
import {Configuration} from '../../models/configuration';
import {TooltipModule} from 'primeng/tooltip';

// Custom validator to ensure maxCapacity is greater than totalTickets
export function maxCapacityValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const form = control.parent;
    if (!form) return null;

    const totalTickets = form.get('totalTickets')?.value;
    const maxCapacity = control.value;

    return maxCapacity <= totalTickets
      ? { maxCapacityTooLow: true }
      : null;
  };
}

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css'],
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    ButtonModule,
    CardModule,
    InputTextModule,
    ToastModule,
    TooltipModule
  ],
  providers: [MessageService]
})
export class ConfigurationFormComponent implements OnInit {
  configForm!: FormGroup;
  http = inject(HttpClient);

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.loadConfiguration();
    this.configForm = this.fb.group({
      totalTickets: [0, [
        Validators.required,
        Validators.min(1)
      ]],
      ticketReleaseRate: [0, [
        Validators.required,
        Validators.min(1)
      ]],
      customerRetrievalRate: [0, [
        Validators.required,
        Validators.min(1)
      ]],
      maxCapacity: [0, [
        Validators.required,
        Validators.min(1),
        maxCapacityValidator()
      ]]
    });

    // Add listener to totalTickets to re-validate maxCapacity when it changes
    this.configForm.get('totalTickets')?.valueChanges.subscribe(() => {
      this.configForm.get('maxCapacity')?.updateValueAndValidity();
    });
  }

  loadConfiguration() {
    this.http.get<Configuration>("http://localhost:8081/api/v1/system-config").subscribe({
      next: (config) => {
        // Update form values
        this.configForm.patchValue({
          totalTickets: config.totalTickets,
          ticketReleaseRate: config.ticketReleaseRate,
          customerRetrievalRate: config.customerRetrievalRate,
          maxCapacity: config.maxTicketCapacity
        });

        this.messageService.add({
          severity: 'success',
          summary: 'Configuration Loaded',
          detail: 'System configuration has been loaded successfully'
        });
      },
      error: (err) => {
        this.messageService.add({
          severity: 'error',
          summary: 'Load Error',
          detail: 'Failed to load configuration'
        });
        console.error('Error loading configuration:', err);
      }
    });
  }

  saveConfiguration() {
    if (this.configForm.valid) {
      const configToSave: Configuration = {
        totalTickets: this.configForm.get('totalTickets')?.value,
        ticketReleaseRate: this.configForm.get('ticketReleaseRate')?.value,
        customerRetrievalRate: this.configForm.get('customerRetrievalRate')?.value,
        maxTicketCapacity: this.configForm.get('maxCapacity')?.value
      };

      this.http.post<Configuration>("http://localhost:8081/api/v1/system-config", configToSave)
        .subscribe({
          next: () => {
            this.messageService.add({
              severity: 'success',
              summary: 'Configuration Saved',
              detail: 'System configuration has been updated successfully'
            });
          },
          error: (err) => {
            this.messageService.add({
              severity: 'error',
              summary: 'Save Error',
              detail: 'Failed to save configuration'
            });
            console.error('Error saving configuration:', err);
          }
        });
    } else {
      this.messageService.add({
        severity: 'error',
        summary: 'Validation Error',
        detail: 'Please fill out all required fields correctly!',
        life: 3000
      });
    }
  }
}
