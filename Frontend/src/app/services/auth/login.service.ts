import { Injectable } from '@angular/core';
import { LoginRequest } from './loginRequest';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor() { }

  login(credentials: LoginRequest): Promise<any> {
    // credentials = {name: 'Admin', email: 'admin@admin.com'}
    return fetch('/auth/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify(credentials)
    }).then(response => {
      if (!response.ok) {
        throw new Error('Fallo en el inicio de sesión');
      }
      return response.json() as Promise<string>;
    });
  }
}
