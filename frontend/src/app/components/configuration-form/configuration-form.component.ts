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
import {Configuration} from '../../models/configuration.model';
import {TooltipModule} from 'primeng/tooltip';

/**
 * Custom validator to ensure maxCapacity is greater than totalTickets.
 * @returns A ValidatorFn that checks if maxCapacity is greater than totalTickets.
 */
export function maxCapacityValidator(): ValidatorFn {
  return (control: AbstractControl): {[key: string]: any} | null => {
    const form = control.parent;
    if (!form) return null;

    const totalTickets = form.get('totalTickets')?.value;
    const maxCapacity = control.value;

    // Check if maxCapacity is less than or equal to totalTickets
    return maxCapacity <= totalTickets
      ? { maxCapacityTooLow: true }
      : null;
  };
}

/**
 * Validator to check if the value is less than the difference between maxCapacity and totalTickets.
 * @param maxCapacityControlName - The name of the maxCapacity control.
 * @param totalTicketsControlName - The name of the totalTickets control.
 * @returns A ValidatorFn that checks if the value is less than the difference between maxCapacity and totalTickets.
 */
function lessThanMaxCapacityTotalTickets(maxCapacityControlName: string, totalTicketsControlName: string): ValidatorFn {
  return (control: AbstractControl): { [key: string]: any } | null => {
    const form = control.parent;
    if (!form) return null;

    const maxCapacity = form.get(maxCapacityControlName)?.value;
    const totalTickets = form.get(totalTicketsControlName)?.value;
    const value = control.value;

    // Check if the value is greater than or equal to the difference between maxCapacity and totalTickets
    return value >= (maxCapacity - totalTickets)
      ? { lessThanMaxCapacityTotalTickets: true }
      : null;
  };
}

/**
 * Component for the configuration form.
 * Provides functionality to load and save system configuration.
 */
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
  http = inject(HttpClient); // Inject directly to avoid circular dependency

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  /**
   * Initializes the component by loading the configuration and setting up the form.
   */
  ngOnInit() {
    this.loadConfiguration();
    this.configForm = this.fb.group({
      totalTickets: [0, [
        Validators.required,
        Validators.min(1)
      ]],
      ticketReleaseRate: [0, [
        Validators.required,
        Validators.min(1),
        lessThanMaxCapacityTotalTickets('maxCapacity', 'totalTickets')
      ]],
      customerRetrievalRate: [0, [
        Validators.required,
        Validators.min(1),
        lessThanMaxCapacityTotalTickets('maxCapacity', 'totalTickets')
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

  /**
   * Loads the configuration from the server and updates the form values.
   */
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

        this.configForm.get('ticketReleaseRate')?.updateValueAndValidity();
        this.configForm.get('customerRetrievalRate')?.updateValueAndValidity();

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

  /**
   * Saves the configuration to the server if the form is valid.
   * Displays success or error messages based on the result.
   */
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
