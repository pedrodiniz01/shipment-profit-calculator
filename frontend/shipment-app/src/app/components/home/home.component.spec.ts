import { TestBed } from '@angular/core/testing';
import { HomeComponent } from './home.component';

describe('HomeComponent', () => {
  let component: HomeComponent;

  // Create new isolated component b4 each test
  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HomeComponent]
    }).compileComponents();
    
    component = TestBed.createComponent(HomeComponent).componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy(); // not null or undefined
  });
});
