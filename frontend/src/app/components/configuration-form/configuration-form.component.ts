import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MessageService } from 'primeng/api';
import {CardModule} from 'primeng/card';
import {ButtonModule} from 'primeng/button';
import {InputTextModule} from 'primeng/inputtext';

@Component({
  selector: 'app-configuration-form',
  templateUrl: './configuration-form.component.html',
  styleUrls: ['./configuration-form.component.css'],
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ButtonModule,
    CardModule,
    InputTextModule
  ],
  providers: [MessageService]
})
export class ConfigurationFormComponent implements OnInit {
  configForm: FormGroup | any;

  constructor(
    private fb: FormBuilder,
    private messageService: MessageService
  ) {}

  ngOnInit() {
    this.configForm = this.fb.group({
      totalSystemTickets: [0, [Validators.required, Validators.min(1)]],
      ticketReleaseRate: [0, [Validators.required, Validators.min(1)]],
      customerRetrievalRate: [0, [Validators.required, Validators.min(1)]],
      maxCapacity: [0, [Validators.required, Validators.min(1)]]
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
}
