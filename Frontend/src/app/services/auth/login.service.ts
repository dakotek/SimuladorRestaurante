import { Injectable } from '@angular/core';
import { LoginRequest } from './loginRequest';
import { JwtHelperService } from '@auth0/angular-jwt';

@Injectable({
  providedIn: 'root'
})
export class LoginService {

  constructor(private jwtHelper: JwtHelperService) {}

  async login(credentials: LoginRequest): Promise<void> {
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
      return await response.json().then(data => {
        const token = data.token;
        const decodedToken = this.jwtHelper.decodeToken(token);
        
        const userId = decodedToken.userId;
        const username = decodedToken.username;
        const email = decodedToken.email;
        const role = decodedToken.role;

        localStorage.setItem('token', data.token);
        localStorage.setItem('userId', userId);
        localStorage.setItem('username', username);
        localStorage.setItem('email', email);
        localStorage.setItem('role', role);

      });
    }).catch(error => {
      throw new Error('El email o la contraseña son incorrectos');
    });
  }
}
