// src/app/app-routing.module.ts
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { CreateShipmentComponent } from './create-shipment/create-shipment.component';
import { SearchShipmentComponent } from './search-shipment/search-shipment.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'create-shipment', component: CreateShipmentComponent },
  { path: 'search-shipment', component: SearchShipmentComponent }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {}
