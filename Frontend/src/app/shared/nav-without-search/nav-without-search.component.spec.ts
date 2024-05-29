import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavWithoutSearchComponent } from './nav-without-search.component';

describe('NavWithoutSearchComponent', () => {
  let component: NavWithoutSearchComponent;
  let fixture: ComponentFixture<NavWithoutSearchComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavWithoutSearchComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(NavWithoutSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
