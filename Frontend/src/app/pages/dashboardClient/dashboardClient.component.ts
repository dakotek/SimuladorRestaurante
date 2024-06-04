import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

import { infoRecipe } from '../recipe/infoRecipe';

@Component({
  selector: 'app-dashboardClient',
  templateUrl: './dashboardClient.component.html',
  styleUrl: './dashboardClient.component.css'
})
export class DashboardClientComponent implements OnInit{

  token: string | null = null;

  constructor(private router:Router, private jwtHelper: JwtHelperService, private infoRecipe:infoRecipe) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token')

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }
    if (localStorage.getItem('role') === "COOK") {
      this.router.navigateByUrl('/cocinero');
    }
  }

  goToRecipe(idRecipe: string) {
    localStorage.setItem('recetaSelecc', idRecipe);
    this.router.navigateByUrl('/receta');
  }
}
