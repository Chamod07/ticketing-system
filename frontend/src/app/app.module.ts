import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { ReactiveFormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ConfigurationComponent } from './Components/configuration/configuration.component';
import { TicketDisplayComponent } from './Components/ticket-display/ticket-display.component';
// Import other components...

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    ConfigurationComponent,
    TicketDisplayComponent
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
