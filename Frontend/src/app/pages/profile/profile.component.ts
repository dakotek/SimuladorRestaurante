import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.css'
})
export class ProfileComponent {
  username : string | null = null
  email : string | null = null
  token : string | null = null

  constructor(private jwtHelper : JwtHelperService, private router: Router){}

  ngOnInit() : void{
    this.token = localStorage.getItem('token')

    if (this.jwtHelper.isTokenExpired(this.token)) {
      this.router.navigateByUrl('/inicio-sesion');
    }
    this.username = localStorage.getItem("username")
    this.email = localStorage.getItem("email")
  }

  ngOnChange() : void{

  }

}
