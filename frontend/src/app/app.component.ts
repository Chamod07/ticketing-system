import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import {ConfigurationComponent} from './Components/configuration/configuration.component';
import {ControlPanelComponent} from './Components/control-panel/control-panel.component';
import {TicketDisplayComponent} from './Components/ticket-display/ticket-display.component';
import {LogDisplayComponent} from './Components/log-display/log-display.component';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, ConfigurationComponent, ControlPanelComponent, TicketDisplayComponent, LogDisplayComponent],
  templateUrl: './app.component.html',
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'ticketing-system-frontend';
}
