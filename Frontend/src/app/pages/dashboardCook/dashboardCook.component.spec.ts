import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DashboardCookComponent } from './dashboardCook.component';

describe('DashboardCookComponent', () => {
  let component: DashboardCookComponent;
  let fixture: ComponentFixture<DashboardCookComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DashboardCookComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(DashboardCookComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
