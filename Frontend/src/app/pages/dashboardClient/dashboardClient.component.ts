import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-dashboardClient',
  templateUrl: './dashboardClient.component.html',
  styleUrl: './dashboardClient.component.css'
})
export class DashboardClientComponent implements OnInit{

  constructor(private router:Router, private jwtHelper: JwtHelperService) {}

  ngOnInit(): void {
    if (this.jwtHelper.isTokenExpired()) {
      this.router.navigateByUrl('/inicio-sesion');
    }
    if (localStorage.getItem('role') === "COOK") {
      this.router.navigateByUrl('/cocinero');
    }
  }

}
