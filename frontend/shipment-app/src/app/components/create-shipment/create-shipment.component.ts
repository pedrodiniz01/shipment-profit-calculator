// src/app/components/create-shipment/create-shipment.component.ts
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ShipmentService } from '../../shared/services/shipment.service';
import { CreateShipment } from '../../shared/models/createshipment.model';

@Component({
  selector: 'app-create-shipment',
  templateUrl: './create-shipment.component.html',
  styleUrls: ['./create-shipment.component.css'],
  standalone: false
})
export class CreateShipmentComponent implements OnInit {
  shipmentForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  constructor(private shipmentService: ShipmentService) {}

  ngOnInit(): void {
    this.shipmentForm = new FormGroup({
      referenceNumber: new FormControl('', Validators.required),
      shipmentDate: new FormControl('', Validators.required)
    });
  }

  onSubmit(): void {
    if (this.shipmentForm.valid) {
      // Type the payload as Shipment
      const payload: CreateShipment = this.shipmentForm.value;
      this.shipmentService.createShipment(payload).subscribe({
        next: data => {
          this.successMessage = 'Shipment created successfully!';
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
}
