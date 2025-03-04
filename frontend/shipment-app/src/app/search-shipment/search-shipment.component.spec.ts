import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search-shipment',
  templateUrl: './search-shipment.component.html',
  styleUrls: ['./search-shipment.component.css']
})
export class SearchShipmentComponent {
  referenceNumber: string = '';
  shipmentSummary: any;
  shipmentFinancialSummary: any; // holds the financial summary if loaded
  errorMessage: string = '';

  // Income form model
  newIncome = {
    type: 'CUSTOMER_PAYMENT',
    amount: null
  };

  incomeSuccessMessage: string = '';
  incomeErrorMessage: string = '';

  // Cost form model
  newCost = {
    type: 'FUEL',
    amount: null
  };

  costSuccessMessage: string = '';
  costErrorMessage: string = '';

  constructor(private http: HttpClient) {}

  onSearch() {
    // Clear previous messages and details
    this.errorMessage = '';
    this.shipmentSummary = null;
    this.shipmentFinancialSummary = null;
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';
    this.costSuccessMessage = '';
    this.costErrorMessage = '';

    this.http.get<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}`).subscribe({
      next: data => {
        this.shipmentSummary = data;
      },
      error: err => {
        this.errorMessage = 'Shipment not found.';
        console.error('Error fetching shipment:', err);
      }
    });
  }

  onMoreDetails() {
    // Toggle financial summary view: if already loaded, clear it, otherwise fetch data
    if (this.shipmentFinancialSummary) {
      this.shipmentFinancialSummary = null;
    } else {
      this.refreshFinancialSummary();
    }
  }

  onAddIncome() {
    // Clear previous messages
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';

    this.http.post<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}/income`, this.newIncome).subscribe({
      next: data => {
        this.incomeSuccessMessage = `Income added successfully! Amount: ${data.amount}`;
        this.refreshShipmentSummary();
        this.refreshFinancialSummary();
      },
      error: err => {
        this.incomeErrorMessage = 'Failed to add income. Please try again.';
        console.error('Error adding income:', err);
      }
    });
  }

  onAddCost() {
    // Clear previous messages
    this.costSuccessMessage = '';
    this.costErrorMessage = '';

    this.http.post<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}/costs`, this.newCost).subscribe({
      next: data => {
        this.costSuccessMessage = `Cost added successfully! Amount: ${data.amount}`;
        this.refreshShipmentSummary();
        this.refreshFinancialSummary();
      },
      error: err => {
        this.costErrorMessage = 'Failed to add cost. Please try again.';
        console.error('Error adding cost:', err);
      }
    });
  }

  // Helper method to refresh the shipment summary
  refreshShipmentSummary() {
    this.http.get<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}`).subscribe({
      next: refreshedData => {
        this.shipmentSummary = refreshedData;
      },
      error: refreshError => {
        console.error('Error refreshing shipment summary:', refreshError);
      }
    });
  }

  // Helper method to refresh the financial summary
  refreshFinancialSummary() {
    this.http.get<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}/financial-summary`).subscribe({
      next: data => {
        this.shipmentFinancialSummary = data;
      },
      error: err => {
        console.error('Error refreshing financial summary:', err);
      }
    });
  }

  // Helper methods to iterate over keys of income and cost maps
  getIncomeKeys(): string[] {
    return this.shipmentFinancialSummary && this.shipmentFinancialSummary.incomeByCategory
      ? Object.keys(this.shipmentFinancialSummary.incomeByCategory)
      : [];
  }

  getCostKeys(): string[] {
    return this.shipmentFinancialSummary && this.shipmentFinancialSummary.costByCategory
      ? Object.keys(this.shipmentFinancialSummary.costByCategory)
      : [];
  }
}
