// src/app/app.module.ts
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms'; // Importe ReactiveFormsModule
import { HttpClientModule } from '@angular/common/http';

import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { CreateShipmentComponent } from '../components/create-shipment/create-shipment.component';
import { SearchShipmentComponent } from '../components/search-shipment/search-shipment.component';
import { HomeComponent } from '../components/home/home.component';

@NgModule({
  declarations: [
    AppComponent,
    CreateShipmentComponent,
    SearchShipmentComponent,
    HomeComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule, // Adicione este módulo
    HttpClientModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {}
