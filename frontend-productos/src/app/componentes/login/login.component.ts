import { CommonModule } from '@angular/common';
import { Component, inject, ViewEncapsulation } from '@angular/core';
import {
  FormBuilder,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css',
  encapsulation: ViewEncapsulation.ShadowDom,
})
export class LoginComponent {
  showPassword: boolean = false;

  private fb = inject(FormBuilder);
  private authService = inject(AuthService);
  private router = inject(Router);

  togglePasswordVisibility() {
    this.showPassword = !this.showPassword;
  }

  // Definición del formulario con validaciones profesionales
  loginForm: FormGroup = this.fb.group({
    usuario: ['', [Validators.required, Validators.minLength(3)]],
    password: ['', [Validators.required, Validators.minLength(4)]],
  });

  errorMessage: string = '';

  onSubmit(): void {
    if (this.loginForm.invalid) {
      this.loginForm.markAllAsTouched();
      return;
    }

    const credenciales = this.loginForm.value;

    this.authService.login(credenciales).subscribe({
      next: (respuesta) => {
        console.log('Login exitoso: ', respuesta);
        // Redirigir al listado de productos del CRUD tras loguearse
        this.router.navigate(['/productos']);
      },
      error: (err) => {
        console.log(err);
        this.errorMessage = 'Credenciales incorrectas o error en el servidor';
      },
    });
  }

  ngOnInit(): void{
    if(this.authService.isAuthenticated()){
      this.router.navigate(['/productos']) // Sí ya tiene un token, mándalo directo al CRUD
    }
  }
}
