import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { MessageService } from 'primeng/api';
import {ToastModule} from 'primeng/toast';
import {Button} from 'primeng/button';
import {CardModule} from 'primeng/card';
import {ProgressBarModule} from 'primeng/progressbar';
import {InputTextModule} from 'primeng/inputtext';

@Component({
  selector: 'app-system-config',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css'],
  standalone: true,
  imports: [
    ToastModule,
    Button,
    ReactiveFormsModule,
    CardModule,
    ProgressBarModule,
    InputTextModule
  ],
  providers: [MessageService]
})

export class ConfigurationFormComponent implements OnInit {
  configForm: FormGroup | any;
  metrics = {
    activeVendors: 1,
    activeCustomers: 1,
    vipCustomers: 1
  };
  availableTickets = {
    current: 120,
    total: 500
  };

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.configForm = this.fb.group({
      totalSystemTickets: [500, [Validators.required, Validators.min(1)]],
      ticketReleaseRate: [40, [Validators.required, Validators.min(1)]],
      customerRetrievalRate: [30, [Validators.required, Validators.min(1)]],
      maxCapacity: [100, [Validators.required, Validators.min(1)]]
    });
  }

  saveConfiguration() {
    // @ts-ignore
    if (this.configForm.valid) {
      // API call would go here
      this.messageService.add({
        severity: 'success',
        summary: 'Success',
        detail: 'System configuration has been updated'
      });
    }
  }

  updateMetric(vendors: string, b: boolean) {

  }
}
