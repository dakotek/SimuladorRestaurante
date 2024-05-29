import { Injectable } from '@angular/core';
import { RegisterRequest } from './registerRequest';

@Injectable({
  providedIn: 'root'
})
export class RegisterService {

  constructor() { }

  register(credentials: RegisterRequest): Promise<any> {
    // credentials = {name: 'Admin', email: 'admin@admin.com', password: '123456', role: 'COOK'}
    return fetch('/auth/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    }).then(response => {
      if (!response.ok) {
        throw new Error('Fallo en el registro');
      }
      return response.json();
    });
  }
}
