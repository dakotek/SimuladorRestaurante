import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-searched-recipes',
  templateUrl: './searched-recipes.component.html',
  styleUrls: ['./searched-recipes.component.css']
})
export class SearchedRecipesComponent implements OnInit {

  searchResults: any;
  categoryResults: any;
  ingredientResults: any;

  searchIds: string[] = [];
  recipeDetails: any[] = [];

  constructor(private router: Router, public http: HttpClient) { }

  ngOnInit() {
    const searchItem = localStorage.getItem('searchTerm');
    const searchType = localStorage.getItem('searchType')
    if (searchType == 'normal') {
      if (searchItem) {
        this.getSearchDetails(searchItem);
      }
    } else {
      if (searchItem) {
        this.getSearchArea(searchItem);
      }
    }
    
  }

  getSearchDetails(searchItem: string) {
    // Busqueda
    this.http.get(`https://www.themealdb.com/api/json/v1/1/search.php?s=${searchItem}`)
      .subscribe(response => {
        this.searchResults = response;
        this.getDetails(this.searchResults);
      });

    // Categoria
    this.http.get(`https://www.themealdb.com/api/json/v1/1/filter.php?c=${searchItem}`)
      .subscribe(response => {
        this.categoryResults = response;
        this.getDetails(this.categoryResults);
      });

    // Ingrediente
    this.http.get(`https://www.themealdb.com/api/json/v1/1/filter.php?i=${searchItem}`)
      .subscribe(response => {
        this.ingredientResults = response;
        this.getDetails(this.ingredientResults);
      });
  }

  getSearchArea(searchItem: string) {
    this.http.get(`https://www.themealdb.com/api/json/v1/1/filter.php?a=${searchItem}`)
    .subscribe(response => {
      this.searchResults = response;
      this.getDetails(this.searchResults);
    });
  }

  getDetails(results: any): void {
    if (results && results.meals) {
      results.meals.forEach((meal: any) => {
        const idMeal = meal.idMeal;
        if (idMeal && !this.searchIds.includes(idMeal)) {
          this.searchIds.push(idMeal);

          this.http.get(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${idMeal}`)
            .subscribe((response: any) => {
              const mealDetails = response.meals[0];
              this.recipeDetails.push(mealDetails);
            });
        }
      });
    }
  }

  menuPrincipal() {
    this.router.navigateByUrl('/cliente');
  }

  goToRecipe(idRecipe: string) {
    localStorage.setItem('recetaSelecc', idRecipe);
    this.router.navigateByUrl('/receta');
  }
}
