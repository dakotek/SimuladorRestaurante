import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LoginService } from '../../services/auth/login.service';
import { LoginRequest } from '../../services/auth/loginRequest';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  
  loginForm=this.formBuilder.group({
    email:['', [Validators.required, Validators.email]],
    password:['', [Validators.required]],
  })

  showPassword: boolean = false
  errorMessage: string | null = null

  constructor(private formBuilder:FormBuilder, private router:Router, private loginService:LoginService) { }

  ngOnInit(): void {
    
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
          if (localStorage.getItem('role') === 'CLIENT') {
            this.router.navigateByUrl('/cliente');
          } else if (localStorage.getItem('role') === 'COOK') {
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