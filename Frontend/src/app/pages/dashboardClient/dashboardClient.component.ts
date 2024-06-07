import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient } from '@angular/common/http';
import { forkJoin, interval, of, Subscription } from 'rxjs';
import { switchMap, map } from 'rxjs/operators';

@Component({
  selector: 'app-dashboardClient',
  templateUrl: './dashboardClient.component.html',
  styleUrls: ['./dashboardClient.component.css']
})
export class DashboardClientComponent implements OnInit, OnDestroy {

  token: string | null = null;
  orders: any[] = [];
  private pollingSubscription: Subscription | undefined;

  constructor(
    private router: Router,
    private jwtHelper: JwtHelperService,
    private http: HttpClient
  ) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('token');

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }
    if (localStorage.getItem('role') === "COOK") {
      this.router.navigateByUrl('/cocinero');
    }

    this.getOrders();
    // Refrescar cada 5 segundos
    this.pollingSubscription = interval(5000).subscribe(() => {
      this.getOrders();
    });
  }

  ngOnDestroy(): void {
    if (this.pollingSubscription) {
      this.pollingSubscription.unsubscribe();
    }
  }

  goToRecipe(idRecipe: string) {
    localStorage.setItem('recetaSelecc', idRecipe);
    this.router.navigateByUrl('/receta');
  }

  getOrders(): void {
    const client = Number(localStorage.getItem('userId'));

    this.http.get<any[]>('http://localhost:9000/auth/orders')
      .pipe(
        switchMap(orders => {
          const clientOrders = orders.filter(order => order.status !== 'CANCELLED' && order.status !== 'COLLECTED' && order.client === client);
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

  cancelOrder(orderId: number) {
    const newStatus = 'CANCELLED';
    this.updateOrderStatus(orderId, newStatus);
  }

  collectOrder(orderId: number) {
    const newStatus = 'COLLECTED';
    this.updateOrderStatus(orderId, newStatus);
  }

  updateOrderStatus(orderId: number, newStatus: string) {
    const url = `http://localhost:9000/auth/orders/${orderId}/status`;
    const request = { status: newStatus };
    this.http.put(url, request)
      .subscribe(
        (response: any) => {
          this.getOrders();
        },
        (error: any) => {
          console.error('Error al actualizar el estado del pedido:', error);
        }
      );
  }
}
