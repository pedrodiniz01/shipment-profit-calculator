import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from '../components/home/home.component';
import { CreateShipmentComponent } from '../components/create-shipment/create-shipment.component';
import { SearchShipmentComponent } from '../components/search-shipment/search-shipment.component';

// Defines navegation structure of app, renders each component by path

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
