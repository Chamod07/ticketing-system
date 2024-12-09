import {Component} from '@angular/core';
import {Button} from 'primeng/button';
import {CountDisplayComponent} from './components/count-display/count-display.component';
import {RouterOutlet} from '@angular/router';
import {ControlPanelComponent} from './components/control-panel/control-panel.component';
import {LineChartComponent} from './components/line-chart/line-chart.component';
import {ConfigurationFormComponent} from './components/configuration-form/configuration-form.component';

@Component({
  selector: 'app-root',
  imports: [
    Button,
    CountDisplayComponent,
    RouterOutlet,
    ControlPanelComponent,
    LineChartComponent,
    ConfigurationFormComponent
  ],
  templateUrl: './app.component.html',
  standalone: true,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'Event Ticketing System';
}
