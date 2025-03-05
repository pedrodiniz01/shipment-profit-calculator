import { ComponentFixture, TestBed } from '@angular/core/testing';
import { SearchShipmentComponent } from './search-shipment.component';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { FormsModule } from '@angular/forms';

describe('SearchShipmentComponent', () => {
  let component: SearchShipmentComponent;
  let fixture: ComponentFixture<SearchShipmentComponent>;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SearchShipmentComponent],
      imports: [FormsModule, HttpClientTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(SearchShipmentComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should fetch shipment summary on onSearch()', () => {
    // Set search query and call onSearch()
    component.searchQuery = 'ABC123';
    component.onSearch();

    // Expect a GET request to the correct URL
    const req = httpMock.expectOne('http://localhost:8080/api/shipments/ABC123');
    expect(req.request.method).toBe('GET');

    // Simulate a successful response
    const dummyShipment = { referenceNumber: 'ABC123', shipmentDate: '2025-03-04' };
    req.flush(dummyShipment);

    // Verify the component is updated
    expect(component.referenceNumber).toBe('ABC123');
    expect(component.shipmentSummary).toEqual(dummyShipment);
    expect(component.errorMessage).toBe('');
  });

  it('should set errorMessage when onSearch() fails', () => {
    component.searchQuery = 'XYZ';
    component.onSearch();

    const req = httpMock.expectOne('http://localhost:8080/api/shipments/XYZ');
    expect(req.request.method).toBe('GET');

    // Simulate an error response
    req.flush('Not found', { status: 404, statusText: 'Not Found' });

    expect(component.errorMessage).toBe('Shipment not found.');
  });
});
