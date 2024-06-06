import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { OrderRequest } from './orderRequest';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  constructor(private jwtHelper: JwtHelperService) {}

  async createOrder(order: OrderRequest): Promise<void> {
    
    return fetch('http://localhost:9000/auth/orders', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer '+ localStorage.getItem('token'),
      },
      body: JSON.stringify(order)
    }).then(async response => {
      if (!response.ok) {
        return response.json().then(error => {
          throw new Error(error.message || 'Error desconocido');
        });
      }

    }).catch(error => {
      throw new Error(error.message || 'No se pudo conectar al servidor');
    });
  }
}