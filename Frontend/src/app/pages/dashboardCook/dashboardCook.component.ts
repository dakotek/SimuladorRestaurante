import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { CookService } from '../../services/cookService';

@Component({
  selector: 'app-dashboardCook',
  templateUrl: './dashboardCook.component.html',
  styleUrl: './dashboardCook.component.css'
})
export class DashboardCookComponent implements OnInit{

  token: string | null = null;
  pendingDishes : any[] = []

  constructor(private router:Router, private jwtHelper: JwtHelperService, private cookService: CookService) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token')

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }

    if (localStorage.getItem('role') === "CLIENT") {
      this.router.navigateByUrl('/cliente');
    }

    this.getPendingDishes()

    /*
    setInterval(() => {
      this.getPendingDishes();
    }, 30000);
    */
  }

  getPendingDishes(): void {
    console.log(this.pendingDishes)
    this.cookService.getPendingDishes().subscribe(dishes => {
      for (let i = 0; i < dishes.length; i++) {
        const dish = dishes[i];
        fetch(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${dish.recipe}`)
        .then((response : Response)  => {
          if (!response.ok) {
            throw new Error('Error getting response');
          }
          return response.json();
        })
        .then(data => {
          //console.log(data.meals[0])
          dish["name"] = data.meals[0].strMeal
          dish["img"] = data.meals[0].strMealThumb
        })
        this.pendingDishes.push(dish);
      }
    });
  }

  goToRecipe(id : number, recipe : string, status : string) {
    localStorage.setItem('recetaIdBBDD', id.toString());
    localStorage.setItem('recetaSelec', recipe);
    localStorage.setItem('recetaEstado', status);

    this.router.navigateByUrl('/receta-detallada');
  }

}
