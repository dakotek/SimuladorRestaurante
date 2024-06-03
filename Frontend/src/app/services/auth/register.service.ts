import { Injectable } from '@angular/core';
import { RegisterRequest } from './registerRequest';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor() { }

  register(credentials: RegisterRequest): Promise<any> {
    // credentials = {name: 'Admin', email: 'admin@admin.com', password: '123456', role: 'COOK'}
    return fetch('http://localhost:9000/auth/registro', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    }).then(response => {
      if (!response.ok) {
        return response.json().then(error => {
          throw new Error(error.message || 'Error desconocido');
        });
      }
      return response.json();
    }).catch(error => {
      throw new Error(error.message || 'No se pudo conectar al servidor');
    });
  }
}
