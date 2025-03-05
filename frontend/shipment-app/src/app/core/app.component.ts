import { Component } from '@angular/core';

// Basic UI structure of angular
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  standalone: false,
  styleUrl: './app.component.css'
})
export class AppComponent {
  title = 'shipment-app';
}
