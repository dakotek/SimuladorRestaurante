import { Injectable } from '@angular/core';
import { LoginRequest } from './loginRequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor() {}

  async login(credentials: LoginRequest): Promise<any> {
    // credentials = {name: 'Admin', email: 'admin@admin.com'}
    return fetch('http://localhost:9000/auth/inicio-sesion', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    }).then(async response => {
      if (!response.ok) {
        return response.json().then(error => {
          throw new Error(error.message || 'Error desconocido');
        });
      }
      return await response.json().then(data => data.token)
    }).catch(error => {
      throw new Error(error.message || 'No se pudo conectar al servidor');
    });
  }
}
