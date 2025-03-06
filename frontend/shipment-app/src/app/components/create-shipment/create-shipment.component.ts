// src/app/components/create-shipment/create-shipment.component.ts
import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { ShipmentService } from '../../shared/services/shipment.service';
import { CreateShipment } from '../../shared/models/createshipment.model';

@Component({
  selector: 'app-create-shipment', // can be reference in other html tag to reuse this component
  templateUrl: './create-shipment.component.html',
  styleUrls: ['./create-shipment.component.css'],
  standalone: false
})

// implement interface onInit
export class CreateShipmentComponent implements OnInit {
  // Like attributes
  shipmentForm!: FormGroup;
  successMessage: string = '';
  errorMessage: string = '';

  // inject shipment service // AE
  constructor(private shipmentService: ShipmentService) {}

  // This method is called after component is created | Angular specific
  ngOnInit(): void {
    this.shipmentForm = new FormGroup({
      referenceNumber: new FormControl('', Validators.required), // initial value is '', but can't be empty
      shipmentDate: new FormControl('', Validators.required)
    });
  }

  onSubmit(): void {
    // from ngOnIt above
    if (this.shipmentForm.valid) {
      // Payload should have CreateShipment structure
      const payload: CreateShipment = this.shipmentForm.value;
      this.shipmentService.createShipment(payload).subscribe({ // subscribe deals with async backend response
        next: data => {
          this.successMessage = 'Shipment created successfully!'; // updates succesMessage variable in html
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
