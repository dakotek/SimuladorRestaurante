import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { DeleteService } from '../../services/deleteUser';
import { HttpClient } from '@angular/common/http';
import { forkJoin, interval, of, Subscription } from 'rxjs';
import { switchMap, map } from 'rxjs/operators';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  username : string | null = null
  email : string | null = null
  token : string | null = null
  pedidos : string | null = null

  orders: any[] = [];
  private pollingSubscription: Subscription | undefined;

  constructor(
    private jwtHelper : JwtHelperService,
    private router: Router,
    private deleteUser : DeleteService,
    private http: HttpClient
  ){}

  ngOnInit() : void{
    this.token = localStorage.getItem('token')
    

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }
    this.username = localStorage.getItem("username")
    this.email = localStorage.getItem("email")
    const role = localStorage.getItem('role')
    if (role === "CLIENT") {
      this.pedidos = 'Historial de pedidos recogidos:'
    } else {
      this.pedidos = 'Historial de comandas completadas:'
    }

    this.getOrders();
    // Refrescar cada 5 segundos
    this.pollingSubscription = interval(5000).subscribe(() => {
      this.getOrders();
    });
  }

  closeCurrentSession() : void{
      localStorage.clear()
      this.router.navigateByUrl("/inicio-sesion")
  }

  ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }

  deleteProfile() : void{
    this.deleteUser.delete()
    .then( () => {
      console.log('Si'),
      localStorage.clear(),
      this.router.navigateByUrl('/inicio-sesion');
    })
    .catch( (error) => {
      console.log(error)
    })
  }

  getOrders(): void {
    const client = Number(localStorage.getItem('userId'));

    this.http.get<any[]>('http://localhost:9000/auth/orders')
      .pipe(
        switchMap(orders => {
          const clientOrders = orders.filter(order => order.status === 'COLLECTED' && (order.client === client || order.cook === client));
          return this.addRecipeNamesToOrders(clientOrders);
        })
      )
      .subscribe(updatedOrders => {
        this.orders = updatedOrders;
      });
  }

  addRecipeNamesToOrders(orders: any[]) {
    const requests = orders.map(order =>
      this.getRecipeDetails(order.recipe).pipe(
        map(recipeDetails => {
          order.recipeName = recipeDetails.meals[0].strMeal;
          return order;
        })
      )
    );
    return requests.length ? forkJoin(requests) : of([]);
  }

  getRecipeDetails(idRecipe: string) {
    return this.http.get<{ meals: any[] }>(`https://www.themealdb.com/api/json/v1/1/lookup.php?i=${idRecipe}`).pipe(
      map(response => response)
    );
  }
}
