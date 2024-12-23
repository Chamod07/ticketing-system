import {Component} from '@angular/core';
import {CountDisplayComponent} from './components/count-display/count-display.component';
import {ConfigurationFormComponent} from './components/configuration-form/configuration-form.component';
import {TicketAvailabilityComponent} from './components/ticket-availability/ticket-availability.component';
import {ControlPanelComponent} from './components/control-panel/control-panel.component';
import {ToastModule} from 'primeng/toast';
import {LineChartComponent} from './components/line-chart/line-chart.component';
import {LogDisplayComponent} from './components/log-display/log-display.component';

@Component({
  selector: 'app-root',
  imports: [
    CountDisplayComponent,
    ConfigurationFormComponent,
    CountDisplayComponent,
    TicketAvailabilityComponent,
    ControlPanelComponent,
    ToastModule,
    LineChartComponent,
    LogDisplayComponent
  ],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Event Ticketing System';
}
