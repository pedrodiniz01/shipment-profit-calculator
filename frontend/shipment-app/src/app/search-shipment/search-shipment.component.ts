import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search-shipment',
  templateUrl: './search-shipment.component.html',
  styleUrls: ['./search-shipment.component.css'],
  standalone: false
})
export class SearchShipmentComponent {
  referenceNumber: string = '';
  shipmentSummary: any;
  shipmentFinancialSummary: any;
  errorMessage: string = '';

  // New property to store all shipments
  shipments: any[] = [];

  // Flags for toggling form display (used only for search view)
  showIncomeForm: boolean = false;
  showCostForm: boolean = false;

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

  // Existing search method remains unchanged
  onSearch() {
    // Clear previous messages and details
    this.errorMessage = '';
    this.shipmentSummary = null;
    this.shipmentFinancialSummary = null;
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';
    this.costSuccessMessage = '';
    this.costErrorMessage = '';
    // Clear list of shipments (in case a previous "Get All" view is active)
    this.shipments = [];
    // Hide forms
    this.showIncomeForm = false;
    this.showCostForm = false;

    this.http.get<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}`).subscribe({
      next: data => {
        this.shipmentSummary = data;
      },
      error: err => {
        this.errorMessage = 'Shipment not found or error occurred';
        console.error('Error fetching shipment:', err);
      }
    });
  }

  // New method to retrieve all shipments
  onGetAll() {
    // Clear previous details and messages
    this.errorMessage = '';
    this.shipmentSummary = null;
    this.shipmentFinancialSummary = null;
    // Hide forms
    this.showIncomeForm = false;
    this.showCostForm = false;

    this.http.get<any[]>(`http://localhost:8080/api/shipments`).subscribe({
      next: data => {
        this.shipments = data;
      },
      error: err => {
        this.errorMessage = 'Error retrieving shipments';
        console.error('Error fetching shipments:', err);
      }
    });
  }

  onMoreDetails() {
    if (this.shipmentFinancialSummary) {
      this.shipmentFinancialSummary = null;
    } else {
      // Close the forms before showing details.
      this.showIncomeForm = false;
      this.showCostForm = false;
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
        // Optionally refresh shipment summary (if needed)
        this.refreshShipmentSummary();
        // Removed refreshFinancialSummary() call
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
        // Optionally refresh shipment summary (if needed)
        this.refreshShipmentSummary();
        // Removed refreshFinancialSummary() call
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

  // Toggle the display of the Income form
  toggleIncomeForm() {
    if (!this.showIncomeForm) {
      this.showIncomeForm = true;
      // Close the cost form and financial summary.
      this.showCostForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      this.showIncomeForm = false;
    }
  }

  // Toggle the display of the Cost form
  toggleCostForm() {
    if (!this.showCostForm) {
      this.showCostForm = true;
      // Close the income form and financial summary.
      this.showIncomeForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      this.showCostForm = false;
    }
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
