import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Subscription, interval } from 'rxjs';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-dashboardCook',
  templateUrl: './dashboardCook.component.html',
  styleUrl: './dashboardCook.component.css'
})
export class DashboardCookComponent implements OnInit, OnDestroy{

  private token: string | null = null;
  pendingDishes : any[] = []
  private pollingSubscription: Subscription | undefined;
  recipeDetails: any | null = null;

  constructor(private router:Router, private jwtHelper: JwtHelperService, private http : HttpClient) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token')

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }

    if (localStorage.getItem('role') === "CLIENT") {
      this.router.navigateByUrl('/cliente');
    }
    this.getPendingDishes()

    if (!localStorage.getItem('recetaSelec')) {
      const id = localStorage.getItem("userId")
      fetch(`http://localhost:9000/auth/orders/in-preparation/cook/${id}`)
      .then(response => {
        if (!response.ok) {
            throw new Error('Network response was not ok');
        }
        return response.json();
    })
    .then(data => {
      console.log(data)
      localStorage.setItem('recetaIdBBDD', data.id);
      localStorage.setItem('recetaSelec', data.recipe);
    })
    .then( () =>
      this.getRecipeDetails(localStorage.getItem('recetaSelec')!)
    )
    .catch(error => console.error('Error:', error));
    }


    this.pollingSubscription = interval(5000).subscribe(() => {
      this.getPendingDishes();
    });
    
  }

  ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }

  getPendingDishes(): void {
    this.http.get<any[]>('http://localhost:9000/auth/orders/pending')
      .subscribe(response => {
        this.pendingDishes =  response.filter(dish => dish.cook == null)
        this.pendingDishes.map(dish => 
          fetch(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${dish.recipe}`)
            .then(response => {
              if (!response.ok) {
                throw new Error('Error getting response');
              }
              return response.json();
            })
            .then(data => {
              dish.img = data.meals[0].strMealThumb;
              return dish;
            })
        );

    });
  }

  async pickOrder(id: number, recipe: string) {
    if (!localStorage.getItem('recetaSelec')) {
      try {
        localStorage.setItem('recetaIdBBDD', id.toString());
        localStorage.setItem('recetaSelec', recipe);
        
        const url1 = `http://localhost:9000/auth/orders/${id}/status`;
        const request1 = { status: 'IN_PREPARATION' };
  
        await this.http.put(url1, request1).toPromise();
  
        const cookId = localStorage.getItem("userId");
        const url2 = `http://localhost:9000/auth/orders/assign-cook/${id}?cookId=${cookId}`;
        
        await fetch(url2, {
          method: 'PUT'
        });
  
        console.log("Ambas solicitudes completadas correctamente");
        this.getPendingDishes();
        this.getRecipeDetails(recipe);
      } catch (error) {
        console.error('Error en alguna de las solicitudes:', error);
      }
    }
  }
  

  getRecipeDetails(idRecipe: string) {
    this.http.get(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${idRecipe}`)
      .subscribe(response=> {
        this.recipeDetails = response;
      });
  }

  getIngredients(): string[] {
    const ingredients = [];
    for (let i = 1; i <= 20; i++) {
      const ingredient = this.recipeDetails.meals[0][`strIngredient${i}`];
      const measure = this.recipeDetails.meals[0][`strMeasure${i}`];
      if (ingredient) {
        ingredients.push(measure+" of "+ingredient);
      }
    }
    return ingredients;
  }

  getIntructions(){
    return this.recipeDetails.meals[0][`strInstructions`];
  }

  finishOrder(){
    const id = localStorage.getItem('recetaIdBBDD')
    const url = `http://localhost:9000/auth/orders/${id}/status`;
      const request = { status: "READY" };
      this.http.put(url, request)
      .subscribe(
        () => {
          this.getPendingDishes();
        },
        (error) => {
          console.error('Error al actualizar el estado del pedido:', error);
        }
      );
    localStorage.removeItem('recetaSelec')
    localStorage.removeItem('recetaIdBBDD')
    this.recipeDetails = null
  }

}
