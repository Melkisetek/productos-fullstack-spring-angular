import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';
import { AuthService } from '../services/auth.service';

export const authGuard: CanActivateFn = (route, state) => {
  const authService = inject(AuthService);
  const router = inject(Router);

  // 1. Verifcar si el usuario tiene un token activo en el almacenamiento
  if (authService.isAuthenticated()) {
    return true; // Permitir el acceso a la ruta
  }

  // 2. Si no está autenticado, lo mandamos al login
  console.warn('Token expirado o inválido. Redirigiendo...');
  authService.logout();

  return false; // Bloquea el acceso a la ruta
};
