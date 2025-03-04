// src/app/create-shipment/create-shipment.component.ts
import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-create-shipment',
  templateUrl: './create-shipment.component.html',
  styleUrls: ['./create-shipment.component.css'],
  standalone: false
})
export class CreateShipmentComponent {
  shipment = {
    referenceNumber: '',
    shipmentDate: ''
  };

  successMessage: string = '';
  errorMessage: string = '';

  constructor(private http: HttpClient) {}

  onSubmit() {
    const payload = {
      referenceNumber: this.shipment.referenceNumber,
      shipmentDate: this.shipment.shipmentDate
    };

    this.http.post<any>('http://localhost:8080/api/shipments/create', payload).subscribe({
      next: data => {
        this.successMessage = 'Shipment created successfully!'
        this.errorMessage = '';
      },
      error: error => {
        this.errorMessage = 'Failed to create shipment. Please try again.';
        this.successMessage = '';
        console.error('Error creating shipment:', error);
      }
    });
  }
}
