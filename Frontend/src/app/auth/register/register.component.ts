import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterService } from '../../services/auth/register.service';
import { RegisterRequest } from '../../services/auth/registerRequest';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {

  registerForm = this.formBuilder.group({
    name: ['', [Validators.required, Validators.minLength(3)]],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  constructor(private formBuilder:FormBuilder, private router:Router, private registerService:RegisterService) { }

  ngOnInit(): void {
    
  }

  get name() {
    return this.registerForm.controls.name;
  }

  get email() {
    return this.registerForm.controls.email;
  }

  get password() {
    return this.registerForm.controls.password;
  }

  register() {
    if (this.registerForm.valid) {
      this.registerService.register(this.registerForm.value as RegisterRequest);
      this.router.navigateByUrl('/inicio-sesion');
      this.registerForm.reset();
    } else {
      this.registerForm.markAllAsTouched();
    }
  }
  
}
