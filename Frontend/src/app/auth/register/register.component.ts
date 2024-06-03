import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../../services/auth/register.service';
import { RegisterRequest } from '../../services/auth/registerRequest';
import { confirmPasswordValidator } from './confirmPasswordValidator';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  registerForm = this.formBuilder.group({
    username: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
    confirmPassword: ['', []],
    role: ['CLIENT', []]
  }, { validators: confirmPasswordValidator });

  showPassword: boolean = false;
  showConfirmPassword: boolean = false;
  errorMessage: string | null = null;

  constructor(private formBuilder:FormBuilder, private router:Router, private registerService:RegisterService) { }

  ngOnInit(): void {

  }

  get name() {
    return this.registerForm.controls.username;
  }

  get email() {
    return this.registerForm.controls.email;
  }

  get password() {
    return this.registerForm.controls.password;
  }
  
  get confirmPassword() {
    return this.registerForm.controls.confirmPassword;
  }

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  toggleConfirmPasswordVisibility() {
    this.showConfirmPassword = !this.showConfirmPassword;
  }

  register() {
    if (this.registerForm.valid) {
      const { confirmPassword, ...registerData } = this.registerForm.value; // Eliminar confirmPassword del objeto antes de enviarlo al servicio
      this.registerService.register(registerData as RegisterRequest)
        .then(() => {
          this.router.navigateByUrl('/inicio-sesion');
          this.registerForm.reset();
        })
        .catch(error => {
          this.errorMessage = error.message;
        });
    } else {
      this.registerForm.markAllAsTouched();
    }
  }  
}