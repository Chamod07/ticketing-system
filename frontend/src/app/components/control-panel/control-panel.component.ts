import { Component } from '@angular/core';
import {ToggleButtonModule} from 'primeng/togglebutton';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [
    ToggleButtonModule,
    FormsModule
  ],
  templateUrl: './control-panel.component.html',
  styleUrl: './control-panel.component.css'
})
export class ControlPanelComponent {
  play: any;
  stop: any;

}
