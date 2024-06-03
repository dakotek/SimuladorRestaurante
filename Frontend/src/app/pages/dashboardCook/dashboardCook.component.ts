import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-dashboardCook',
  templateUrl: './dashboardCook.component.html',
  styleUrl: './dashboardCook.component.css'
})
export class DashboardCookComponent implements OnInit{

  token: string | null = null;

  constructor(private router:Router, private jwtHelper: JwtHelperService) {}

  ngOnInit(): void {
    this.token = localStorage.getItem('token')

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }

    if (localStorage.getItem('role') === "CLIENT") {
      this.router.navigateByUrl('/cliente');
    }
  }

}
