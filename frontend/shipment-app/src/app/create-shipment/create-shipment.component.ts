import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ShipmentService } from '../shipment.service';
import { Shipment } from '../models/shipment.model';

@Component({
  selector: 'app-create-shipment',
  templateUrl: './create-shipment.component.html',
  standalone: false
})
export class CreateShipmentComponent implements OnInit {
  shipmentForm: FormGroup;
  createdShipment: Shipment | null = null;
  errorMessage: string | null = null;

  constructor(
    private fb: FormBuilder,
    private shipmentService: ShipmentService
  ) {
    this.shipmentForm = this.fb.group({
      referenceNumber: ['', Validators.required],
      shipmentDate: ['', Validators.required]
    });
  }

  ngOnInit(): void {}

  onSubmit(): void {
    if (this.shipmentForm.invalid) {
      return;
    }
    
    const requestPayload = {
      referenceNumber: this.shipmentForm.value.referenceNumber,
      shipmentDate: this.shipmentForm.value.shipmentDate
    };
  
    this.shipmentService.createShipment(requestPayload).subscribe({
      next: (shipment: Shipment) => {
        // Clear error message and set success shipment
        this.errorMessage = null;
        this.createdShipment = shipment;
        console.log('Shipment created successfully', shipment);
      },
      error: (err) => {
        // Clear any previous success shipment and set error message
        this.createdShipment = null;
        this.errorMessage = 'Error creating shipment. Please try again.';
        console.error('Error:', err);
      }
    });
  }  
}
