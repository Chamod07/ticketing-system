import {NgModule} from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {ReactiveFormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {AppComponent} from './app.component';
import { ButtonModule } from 'primeng/button';
import {ToastModule} from 'primeng/toast';
import {MessageService} from 'primeng/api';

@NgModule({
  declarations: [],
  imports: [
    BrowserModule,
    BrowserAnimationsModule,
    ReactiveFormsModule,
    ToastModule,
    ButtonModule,
    AppComponent,
    HttpClientModule,
  ],
  providers: [MessageService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
