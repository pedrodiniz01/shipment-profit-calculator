import { Component } from '@angular/core';
import { ShipmentService } from '../../shared/services/shipment.service';

@Component({
  selector: 'app-search-shipment',
  templateUrl: './search-shipment.component.html',
  styleUrls: ['./search-shipment.component.css'],
  standalone: false 
})
export class SearchShipmentComponent {
  searchQuery: string = '';
  referenceNumber: string = '';
  shipmentSummary: any;
  shipmentFinancialSummary: any;
  errorMessage: string = '';
  allShipments: any[] = [];
  showIncomeForm: boolean = false;
  showCostForm: boolean = false;

  newIncome = {
    type: 'CUSTOMER_PAYMENT',
    amount: null
  };

  incomeSuccessMessage: string = '';
  incomeErrorMessage: string = '';

  newCost = {
    type: 'FUEL',
    amount: null
  };

  costSuccessMessage: string = '';
  costErrorMessage: string = '';

  constructor(private shipmentService: ShipmentService) {}

  onSearch() {
    this.errorMessage = '';
    this.shipmentSummary = null;
    this.shipmentFinancialSummary = null;
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';
    this.costSuccessMessage = '';
    this.costErrorMessage = '';
    this.allShipments = [];
    this.showIncomeForm = false;
    this.showCostForm = false;

    this.referenceNumber = this.searchQuery;
    this.shipmentService.getShipment(this.referenceNumber).subscribe({
      next: data => {
        this.shipmentSummary = data;
      },
      error: err => {
        this.errorMessage = 'Shipment not found.';
        console.error('Error fetching shipment:', err);
      }
    });
  }

  onGetAll() {
    this.errorMessage = '';
    this.shipmentSummary = null;
    this.shipmentFinancialSummary = null;
    this.showIncomeForm = false;
    this.showCostForm = false;
    
    this.searchQuery = '';
    this.referenceNumber = '';
  
    this.shipmentService.getAllShipments().subscribe({
      next: data => {
        this.allShipments = data;
      },
      error: err => {
        this.errorMessage = 'Error retrieving shipments';
        console.error('Error fetching shipments:', err);
      }
    });
  }

  onSelectShipment(shipment: any) {
    this.shipmentSummary = shipment;
    this.referenceNumber = shipment.referenceNumber;
    this.allShipments = [];
    this.showIncomeForm = false;
    this.showCostForm = false;
    this.shipmentFinancialSummary = null;
  }

  onMoreDetails() {
    if (this.shipmentFinancialSummary) {
      this.shipmentFinancialSummary = null;
    } else {
      this.showIncomeForm = false;
      this.showCostForm = false;
      this.refreshFinancialSummary();
    }
  }

  onAddIncome() {
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';
    this.shipmentService.addIncome(this.referenceNumber, this.newIncome).subscribe({
      next: data => {
        this.incomeSuccessMessage = `Income added successfully!`;
        this.refreshShipmentSummary();
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
    this.shipmentService.addCost(this.referenceNumber, this.newCost).subscribe({
      next: data => {
        this.costSuccessMessage = `Cost added successfully!`;
        this.refreshShipmentSummary();
      },
      error: err => {
        this.costErrorMessage = 'Failed to add cost. Please try again.';
        console.error('Error adding cost:', err);
      }
    });
  }

  refreshShipmentSummary() {
    this.shipmentService.getShipment(this.referenceNumber).subscribe({
      next: refreshedData => {
        this.shipmentSummary = refreshedData;
      },
      error: refreshError => {
        console.error('Error refreshing shipment summary:', refreshError);
      }
    });
  }

  refreshFinancialSummary() {
    this.shipmentService.getFinancialSummary(this.referenceNumber).subscribe({
      next: data => {
        this.shipmentFinancialSummary = data;
      },
      error: err => {
        console.error('Error refreshing financial summary:', err);
      }
    });
  }

  toggleIncomeForm() {
    this.newIncome = { type: 'CUSTOMER_PAYMENT', amount: null };
    this.incomeSuccessMessage = '';
  
    if (!this.showIncomeForm) {
      this.showIncomeForm = true;
      this.showCostForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      this.showIncomeForm = false;
    }
  }
  
  toggleCostForm() {
    this.newCost = { type: 'FUEL', amount: null };
    this.costSuccessMessage = '';
  
    if (!this.showCostForm) {
      this.showCostForm = true;
      this.showIncomeForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      this.showCostForm = false;
    }
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
