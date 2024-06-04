import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/auth/login.service';
import { LoginRequest } from '../../services/auth/loginRequest';
import { JwtHelperService } from '@auth0/angular-jwt';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit {

  loginForm = this.formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required]],
  });

  showPassword: boolean = false;
  errorMessage: string | null = null;
  token: string | null = null;

  constructor(private formBuilder: FormBuilder, private router: Router, private loginService: LoginService, private jwtHelper: JwtHelperService) { }

  ngOnInit(): void {
    this.token = localStorage.getItem('token');

    if (this.token && this.jwtHelper.isTokenExpired(this.token)) {
      this.errorMessage = 'La sesión ha expirado, inicia sesión de nuevo';
      localStorage.clear();
    } else {
      const role = localStorage.getItem('role');
      if (role === 'CLIENT') {
        this.router.navigateByUrl('/cliente');
      }
      if (role === 'COOK') {
        this.router.navigateByUrl('/cocinero');
      }
    }
  }

  get email() {
    return this.loginForm.controls.email;
  }

  get password() {
    return this.loginForm.controls.password;
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  login() {
    if (this.loginForm.valid) {
      this.loginService.login(this.loginForm.value as LoginRequest)
        .then(() => {
          const role = localStorage.getItem('role');
          if (role === 'CLIENT') {
            this.router.navigateByUrl('/cliente');
          } else if (role === 'COOK') {
            this.router.navigateByUrl('/cocinero');
          }
          this.loginForm.reset();
        })
        .catch(error => {
          this.errorMessage = error.message;
        });
    } else {
      this.loginForm.markAllAsTouched();
    }
  }
}
