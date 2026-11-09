import { Injectable, inject, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest, AuthResponse } from '../model/auth.model';
import { Observable, tap } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private router = inject(Router);
  private readonly AUTH_URL = 'http://localhost:8080/api/auth/login';
  private http = inject(HttpClient);

  currentUser = signal<string | null>(localStorage.getItem('usuario'));

  login(credenciales: LoginRequest): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.AUTH_URL, credenciales).pipe(
      tap((respuesta) => {
        // Almacenamos el Token y los datos básicos en el navegador
        localStorage.setItem('token', respuesta.token);
        localStorage.setItem('usuario', respuesta.usuario);

        // Actualizamos el Signal
        this.currentUser.set(respuesta.usuario);
      }),
    );
  }

  logout(): void {
    localStorage.clear();
    this.currentUser.set(null);
    this.router.navigate(['/login']);
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
  /**
   * Decodifica el Payload del JWT para verificar
   * la fecha de expiración ('exp').
   * @returns true
   */
  isTokenExpired(): boolean {
    const token = this.getToken();
    if (!token) return true;

    try {
      // Los JWT constan de 3 partes separados por puntos. La segunda parte (índice 1) son los Claims.
      const payloadBase64 = token.split('.')[1];
      const payloadDecodificado = JSON.parse(atob(payloadBase64));

      if (!payloadDecodificado.exp) return false;

      const fechaExpiracion = payloadDecodificado.exp * 1000; // Convertir a milisegundos
      return Date.now() >= fechaExpiracion;
    } catch (error) {
      return true; // Si el token está corrupto, lo tratamos como expirado
    }
  }

  isAuthenticated(): boolean {
    return !!this.getToken() && !this.isTokenExpired();
  }
}
