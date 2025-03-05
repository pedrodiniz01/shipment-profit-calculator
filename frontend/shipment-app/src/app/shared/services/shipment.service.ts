import { Injectable } from '@angular/core';
import { HttpClient, HttpErrorResponse } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';
import { catchError } from 'rxjs/operators';

@Injectable({
  providedIn: 'root'
})
export class ShipmentService {
  private baseUrl = 'http://localhost:8080/api/shipments';

  constructor(private http: HttpClient) { }

  // Create a new shipment
  createShipment(payload: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/create`, payload)
      .pipe(catchError(this.handleError));
  }

  // Get a shipment by reference number
  getShipment(referenceNumber: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${referenceNumber}`)
      .pipe(catchError(this.handleError));
  }

  // Add income to a shipment
  addIncome(referenceNumber: string, income: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/${referenceNumber}/income`, income)
      .pipe(catchError(this.handleError));
  }

  // Add cost to a shipment
  addCost(referenceNumber: string, cost: any): Observable<any> {
    return this.http.post<any>(`${this.baseUrl}/${referenceNumber}/costs`, cost)
      .pipe(catchError(this.handleError));
  }

  // Get financial summary for a shipment
  getFinancialSummary(referenceNumber: string): Observable<any> {
    return this.http.get<any>(`${this.baseUrl}/${referenceNumber}/financial-summary`)
      .pipe(catchError(this.handleError));
  }

  // Get all shipments
  getAllShipments(): Observable<any[]> {
    return this.http.get<any[]>(`${this.baseUrl}`)
      .pipe(catchError(this.handleError));
  }

  // Error handling method
  private handleError(): Observable<never> {
    return throwError('Error happened :(');
  }
}
