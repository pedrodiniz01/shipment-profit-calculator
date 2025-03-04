import { ComponentFixture, TestBed } from '@angular/core/testing';
import { CreateShipmentComponent } from './create-shipment.component';
import { ReactiveFormsModule } from '@angular/forms';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

describe('CreateShipmentComponent', () => {
  let component: CreateShipmentComponent;
  let fixture: ComponentFixture<CreateShipmentComponent>;
  let httpMock: HttpTestingController;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [CreateShipmentComponent],
      imports: [ReactiveFormsModule, HttpClientTestingModule]
    }).compileComponents();

    fixture = TestBed.createComponent(CreateShipmentComponent);
    component = fixture.componentInstance;
    httpMock = TestBed.inject(HttpTestingController);
    fixture.detectChanges();
  });

  afterEach(() => {
    httpMock.verify();
  });

  it('should create the component', () => {
    expect(component).toBeTruthy();
  });

  it('should have an invalid form when empty', () => {
    expect(component.shipmentForm.valid).toBeFalse();
  });

  it('should post shipment when form is valid', () => {
    component.shipmentForm.controls['referenceNumber'].setValue('ABC123');
    component.shipmentForm.controls['shipmentDate'].setValue('2025-03-04');
    expect(component.shipmentForm.valid).toBeTrue();

    component.onSubmit();
    
    const req = httpMock.expectOne('http://localhost:8080/api/shipments/create');
    expect(req.request.method).toBe('POST');
    expect(req.request.body).toEqual({
      referenceNumber: 'ABC123',
      shipmentDate: '2025-03-04'
    });
    
    req.flush({}); // simula resposta de sucesso

    expect(component.successMessage).toBe('Shipment created successfully!');
  });
});
