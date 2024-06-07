import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CookService {
  constructor(private http: HttpClient) {}

  getPendingDishes(): Observable<any> {
    return this.http.get<any>('http://localhost:9000/auth/orders/pending');
  }
}