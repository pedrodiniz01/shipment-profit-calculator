// src/app/shared/services/shipment.service.spec.ts
import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { ShipmentService } from './shipment.service';

describe('ShipmentService', () => {
  let service: ShipmentService;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      providers: [ShipmentService]
    });
    service = TestBed.inject(ShipmentService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  afterEach(() => {
    // Verifies that no unmatched requests remain.
    httpMock.verify();
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should fetch a shipment by reference number', () => {
    // Define a dummy shipment object to be returned by the API
    const dummyShipment = { referenceNumber: 'ABC123', shipmentDate: '2025-03-04' };

    // Call the service method
    service.getShipment('ABC123').subscribe(data => {
      expect(data).toEqual(dummyShipment);
    });

    // Expect that a GET request has been made to the correct URL
    const req = httpMock.expectOne('http://localhost:8080/api/shipments/ABC123');
    expect(req.request.method).toBe('GET');

    // Simulate a successful response by flushing the dummy shipment
    req.flush(dummyShipment);
  });
});
