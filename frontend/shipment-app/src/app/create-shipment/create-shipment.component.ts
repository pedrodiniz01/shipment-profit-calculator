import { Component, OnInit } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';

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

   // Injected via constructor, easy json handling, provides all methods
  constructor(private http: HttpClient) {}

  // method that validates both fields can't be empty
  ngOnInit(): void {
    this.shipmentForm = new FormGroup({
      referenceNumber: new FormControl('', Validators.required),
      shipmentDate: new FormControl('', Validators.required)
    });
  }

  onSubmit(): void {
    if (this.shipmentForm.valid) {
      const payload = this.shipmentForm.value;
      this.http.post<any>('http://localhost:8080/api/shipments/create', payload).subscribe({
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
