import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { infoRecipe } from '../recipe/infoRecipe';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {
  recipeDetails: any;

  constructor(private route: ActivatedRoute, private router: Router, private infoRecipe: infoRecipe, public http: HttpClient) { }

  ngOnInit() {
    const idRecipe = localStorage.getItem('recetaSelecc');
    if (idRecipe) {
      this.getRecipeDetails(idRecipe);
    }
  }

  getRecipeDetails(idRecipe: string) {
    this.http.get(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${idRecipe}`)
      .subscribe(response=> {
        console.log(response);
        this.recipeDetails = response;
      });
  }

  getIngredients(): string[] {
    const ingredients = [];
    for (let i = 1; i <= 20; i++) {
      const ingredient = this.recipeDetails.meals[0][`strIngredient${i}`];
      if (ingredient) {
        ingredients.push(ingredient);
      }
    }
    return ingredients;
  }

  menuPrincipal() {
    this.router.navigateByUrl('/cliente');
  }
}
