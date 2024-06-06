import { Component, OnInit, OnDestroy } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient } from '@angular/common/http';
import { interval, Subscription } from 'rxjs';

import { infoRecipe } from '../recipe/infoRecipe';

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
    private infoRecipe: infoRecipe,
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
      .subscribe(response => {
        this.orders = response.filter(order => order.status !== 'CANCELLED' && order.status !== 'COLLECTED' && order.client === client);
      });
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
    const idOrder = String(orderId);

    const url = 'http://localhost:9000/auth/orders/' + idOrder.toString() + '/status';
    const request = { status: newStatus };
    const requestBody = JSON.stringify(request); // Convertir el objeto request a una cadena JSON
    this.http.put(url, requestBody)
      .subscribe(
        (response: any) => {
          console.log('Estado del pedido actualizado:', response);
        },
        (error: any) => {
          console.error('Error al actualizar el estado del pedido:', error);
        }
      );
  }
}
