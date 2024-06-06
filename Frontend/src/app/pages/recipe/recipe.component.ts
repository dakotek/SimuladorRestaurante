import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { infoRecipe } from '../recipe/infoRecipe';
import { HttpClient } from '@angular/common/http';
import { OrderRequest } from '../../services/auth/orderRequest';
import { OrderService } from '../../services/auth/order.service';

@Component({
  selector: 'app-recipe',
  templateUrl: './recipe.component.html',
  styleUrls: ['./recipe.component.css']
})
export class RecipeComponent implements OnInit {
  recipeDetails: any;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private http: HttpClient,
    private orderService: OrderService
  ) { }

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

  encargarPedido() {
    const recipe = localStorage.getItem('recetaSelecc');
    const client = localStorage.getItem('userId');
    const status = 'PENDING';

    if (recipe && client) {
      const orderRequest: OrderRequest = {
        client: client,
        cook: '',
        status: status,
        recipe: recipe
      };

      this.orderService.createOrder(orderRequest).then(response => {
        console.log('Order created successfully', response);
        this.router.navigateByUrl('/cliente');
      }).catch(error => {
        console.error('Error creating order', error);
      });
    } else {
      console.error('No se pudo obtener la receta o el cliente del localStorage');
    }
  }
}