import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';
import { HttpClient } from '@angular/common/http';

import { infoRecipe } from '../recipe/infoRecipe';

@Component({
  selector: 'app-dashboardClient',
  templateUrl: './dashboardClient.component.html',
  styleUrl: './dashboardClient.component.css'
})
export class DashboardClientComponent implements OnInit{

  token: string | null = null;
  orders: any[] = [];

  constructor(
    private router:Router,
    private jwtHelper: JwtHelperService,
    private infoRecipe:infoRecipe,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token')

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }
    if (localStorage.getItem('role') === "COOK") {
      this.router.navigateByUrl('/cocinero');
    }
  
    this.getOrders();
  }

  goToRecipe(idRecipe: string) {
    localStorage.setItem('recetaSelecc', idRecipe);
    this.router.navigateByUrl('/receta');
  }

  getOrders(): void {
    const client = Number(localStorage.getItem('userId'));
  
    this.http.get<any[]>('http://localhost:9000/auth/orders')
      .subscribe(response => {
        console.log(response);
        this.orders = response.filter(order => order.status !== 'CANCELLED' && order.status !== 'COLLECTED' && order.client === client);
        this.orders = this.orders.map(order => `${order.id} - ${order.status}`);
    });
  }
}
