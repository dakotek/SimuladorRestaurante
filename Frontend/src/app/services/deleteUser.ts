import { Injectable } from '@angular/core';

@Injectable({
    providedIn: 'root'
  })
export class DeleteService {

    constructor() {}

  async delete(): Promise<void> {
    console.log(localStorage.getItem('userId'))
    return fetch('http://localhost:9000/users/'+localStorage.getItem('userId'), {
      method: 'DELETE',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': 'Bearer '+localStorage.getItem('token'),
      },
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