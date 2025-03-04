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
  shipmentFinancialSummary: any;
  errorMessage: string = '';

  // Define os modelos com tipagem para amount
  newIncome: { type: string; amount: number | null } = {
    type: 'CUSTOMER_PAYMENT',
    amount: null
  };

  incomeSuccessMessage: string = '';
  incomeErrorMessage: string = '';

  newCost: { type: string; amount: number | null } = {
    type: 'FUEL',
    amount: null
  };

  costSuccessMessage: string = '';
  costErrorMessage: string = '';

  constructor(private http: HttpClient) {}

  onSearch() {
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
    if (this.shipmentFinancialSummary) {
      this.shipmentFinancialSummary = null;
    } else {
      this.refreshFinancialSummary();
    }
  }

  onAddIncome() {
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
