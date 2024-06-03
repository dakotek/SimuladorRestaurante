import { ComponentFixture, TestBed } from '@angular/core/testing';

import { CountryImgsComponent } from './country-imgs.component';

describe('CountryImgsComponent', () => {
  let component: CountryImgsComponent;
  let fixture: ComponentFixture<CountryImgsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [CountryImgsComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(CountryImgsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
