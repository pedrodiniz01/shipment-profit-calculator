import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search-shipment',
  templateUrl: './search-shipment.component.html',
  styleUrls: ['./search-shipment.component.css'],
  standalone: false
})
export class SearchShipmentComponent {
  // For the search input field
  searchQuery: string = '';
  // Used for API calls
  referenceNumber: string = '';

  shipmentSummary: any;
  shipmentFinancialSummary: any;
  errorMessage: string = '';

  // For "Get All Shipments" view, using allShipments property
  allShipments: any[] = [];

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

  // Search for a single shipment by reference number
  onSearch() {
    // Clear previous messages and details
    this.errorMessage = '';
    this.shipmentSummary = null;
    this.shipmentFinancialSummary = null;
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';
    this.costSuccessMessage = '';
    this.costErrorMessage = '';
    // Clear the "Get All" view if active
    this.allShipments = [];
    // Hide forms
    this.showIncomeForm = false;
    this.showCostForm = false;

    // Use searchQuery to set the referenceNumber for API calls.
    this.referenceNumber = this.searchQuery;

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

  // Get all shipments and display them without extra buttons
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
        this.allShipments = data;
      },
      error: err => {
        this.errorMessage = 'Error retrieving shipments';
        console.error('Error fetching shipments:', err);
      }
    });
  }

  // When clicking on a shipment card in the "All Shipments" view,
  // load its detailed view without changing the search input.
  onSelectShipment(shipment: any) {
    this.shipmentSummary = shipment;
    // Update referenceNumber for API calls, but leave searchQuery unchanged.
    this.referenceNumber = shipment.referenceNumber;
    this.allShipments = []; // Clear the list to focus on the selected shipment
    // Reset any extra view flags if needed
    this.showIncomeForm = false;
    this.showCostForm = false;
    this.shipmentFinancialSummary = null;
  }

  // Toggle financial details for a single shipment (search view)
  onMoreDetails() {
    if (this.shipmentFinancialSummary) {
      this.shipmentFinancialSummary = null;
    } else {
      // Close the forms before showing details
      this.showIncomeForm = false;
      this.showCostForm = false;
      this.refreshFinancialSummary();
    }
  }

  // Add income for a single shipment (search view)
  onAddIncome() {
    // Clear previous messages
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';

    this.http.post<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}/income`, this.newIncome).subscribe({
      next: data => {
        this.incomeSuccessMessage = `Income added successfully! Amount: ${data.amount}`;
        // Optionally refresh shipment summary
        this.refreshShipmentSummary();
      },
      error: err => {
        this.incomeErrorMessage = 'Failed to add income. Please try again.';
        console.error('Error adding income:', err);
      }
    });
  }

  // Add cost for a single shipment (search view)
  onAddCost() {
    // Clear previous messages
    this.costSuccessMessage = '';
    this.costErrorMessage = '';

    this.http.post<any>(`http://localhost:8080/api/shipments/${this.referenceNumber}/costs`, this.newCost).subscribe({
      next: data => {
        this.costSuccessMessage = `Cost added successfully! Amount: ${data.amount}`;
        // Optionally refresh shipment summary
        this.refreshShipmentSummary();
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

  // Toggle the display of the Income form (search view)
  toggleIncomeForm() {
    if (!this.showIncomeForm) {
      this.showIncomeForm = true;
      // Close the cost form and hide financial details
      this.showCostForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      this.showIncomeForm = false;
    }
  }

  // Toggle the display of the Cost form (search view)
  toggleCostForm() {
    if (!this.showCostForm) {
      this.showCostForm = true;
      // Close the income form and hide financial details
      this.showIncomeForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      this.showCostForm = false;
    }
  }

  // Helper method to get income keys for iteration in the template
  getIncomeKeys(): string[] {
    return this.shipmentFinancialSummary && this.shipmentFinancialSummary.incomeByCategory
      ? Object.keys(this.shipmentFinancialSummary.incomeByCategory)
      : [];
  }

  // Helper method to get cost keys for iteration in the template
  getCostKeys(): string[] {
    return this.shipmentFinancialSummary && this.shipmentFinancialSummary.costByCategory
      ? Object.keys(this.shipmentFinancialSummary.costByCategory)
      : [];
  }
}
