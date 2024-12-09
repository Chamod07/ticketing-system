import {Component} from '@angular/core';
import {CountDisplayComponent} from './components/count-display/count-display.component';
import {RouterOutlet} from '@angular/router';
import {ConfigurationFormComponent} from './components/configuration-form/configuration-form.component';
import {TicketAvailabilityComponent} from './components/ticket-availability/ticket-availability.component';
import {ControlPanelComponent} from './components/control-panel/control-panel.component';

@Component({
  selector: 'app-root',
  imports: [
    CountDisplayComponent,
    RouterOutlet,
    ConfigurationFormComponent,
    CountDisplayComponent,
    TicketAvailabilityComponent,
    ControlPanelComponent
  ],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Event Ticketing System';
}
