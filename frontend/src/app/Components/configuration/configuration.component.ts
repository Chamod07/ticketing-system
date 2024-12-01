import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import { ConfigurationService } from '../../Services/configuration.service';

@Component({
  selector: 'app-configuration',
  templateUrl: './configuration.component.html',
  standalone: true,
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./configuration.component.css']
})
export class ConfigurationComponent implements OnInit {
  configForm: FormGroup | undefined;

  constructor(
    private fb: FormBuilder,
    private configService: ConfigurationService
  ) {}

  ngOnInit() {
    const savedConfig = this.configService.loadConfigFromLocalStorage();

    this.configForm = this.fb.group({
      totalTickets: [savedConfig?.totalTickets || 100, [Validators.required, Validators.min(1)]],
      ticketReleaseRate: [savedConfig?.ticketReleaseRate || 5, [Validators.required, Validators.min(1)]],
      customerRetrievalRate: [savedConfig?.customerRetrievalRate || 3, [Validators.required, Validators.min(1)]],
      maxTicketCapacity: [savedConfig?.maxTicketCapacity || 500, [Validators.required, Validators.min(100)]]
    });
  }

  onSubmit() {
    // @ts-ignore
    if (this.configForm.valid) {
      // @ts-ignore
      const config = this.configForm.value;
      this.configService.updateConfiguration(config);
      this.configService.saveConfigToLocalStorage(config);
    }
  }

  protected readonly FormGroup = FormGroup;
}
