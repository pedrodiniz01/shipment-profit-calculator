import { Component } from '@angular/core';
// Importe o serviço
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

  // Injete o ShipmentService em vez de HttpClient
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
    // Chama o serviço para buscar um shipment
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

    // Usa o serviço para buscar todos os shipments
    this.shipmentService.getAllShipments().subscribe({
      next: data => {
        this.allShipments = data;
      },
      error: err => {
        this.errorMessage = 'Error retrieving shipments';
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
      // Close income and cost forms when opening financial summary
      this.showIncomeForm = false;
      this.showCostForm = false;
      this.refreshFinancialSummary();
    }
  }

  onAddIncome() {
    this.incomeSuccessMessage = '';
    this.incomeErrorMessage = '';
    // Use the service to add income
    this.shipmentService.addIncome(this.referenceNumber, this.newIncome).subscribe({
      next: data => {
        this.incomeSuccessMessage = `Income added successfully!`;
        this.refreshShipmentSummary();
        // Removed refreshFinancialSummary() so the financial summary won't auto-open
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
    // Use the service to add cost
    this.shipmentService.addCost(this.referenceNumber, this.newCost).subscribe({
      next: data => {
        this.costSuccessMessage = `Cost added successfully!`;
        this.refreshShipmentSummary();
        // Removed refreshFinancialSummary() so the financial summary won't auto-open
      },
      error: err => {
        this.costErrorMessage = 'Failed to add cost. Please try again.';
        console.error('Error adding cost:', err);
      }
    });
  }

  refreshShipmentSummary() {
    // Atualiza o shipmentSummary usando o serviço
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
    // Atualiza o financialSummary usando o serviço
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
    // Reset income fields to default values each time the button is clicked
    this.newIncome = { type: 'CUSTOMER_PAYMENT', amount: null };
    this.incomeSuccessMessage = ''; // Clear previous success message
  
    if (!this.showIncomeForm) {
      // Open the income form and close the cost form and financial summary
      this.showIncomeForm = true;
      this.showCostForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      // Close the income form
      this.showIncomeForm = false;
    }
  }
  
  toggleCostForm() {
    // Reset cost fields to default values each time the button is clicked
    this.newCost = { type: 'FUEL', amount: null };
    this.costSuccessMessage = ''; // Clear previous success message
  
    if (!this.showCostForm) {
      // Open the cost form and close the income form and financial summary
      this.showCostForm = true;
      this.showIncomeForm = false;
      this.shipmentFinancialSummary = null;
    } else {
      // Close the cost form
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
