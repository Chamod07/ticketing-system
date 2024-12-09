import { Component } from '@angular/core';
import {ToggleButtonModule} from 'primeng/togglebutton';
import {FormsModule} from '@angular/forms';
import {Button, ButtonDirective} from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';

@Component({
  selector: 'app-control-panel',
  standalone: true,
  imports: [
    ToggleButtonModule,
    FormsModule,
    ButtonDirective,
    Button,
    ToastModule
  ],
  templateUrl: './control-panel.component.html',
  styleUrl: './control-panel.component.css',
  providers: [MessageService]
})
export class ControlPanelComponent {
  constructor(private messageService: MessageService) {
  }
  play: any;
  stop: any;
  pause: any;
  show() {
    this.messageService.add({
      severity:'info',
      summary:'Information',
      detail:'Content',
      life: 2000
    })
  };
}
