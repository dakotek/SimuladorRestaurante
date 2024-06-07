import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SearchedRecipesComponent } from './searched-recipes.component';

describe('SearchedRecipesComponent', () => {
  let component: SearchedRecipesComponent;
  let fixture: ComponentFixture<SearchedRecipesComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [SearchedRecipesComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(SearchedRecipesComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
