export interface CreateShipmentRequest {
    referenceNumber: string;
    shipmentDate: string; // Use an ISO date string, e.g., "2025-03-03"
  }
  
  export interface Shipment {
    id: number;
    referenceNumber: string;
    shipmentDate: string;
    incomes: any[]; // Update these if you have specific interfaces
    costs: any[];
    netAmount: number;
    isProfit: boolean;
  }
  