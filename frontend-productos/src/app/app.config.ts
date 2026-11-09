import {
  ApplicationConfig,
  LOCALE_ID,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient, withInterceptors } from '@angular/common/http';
import { routes } from './app.routes';
import localeGt from '@angular/common/locales/es-GT';
import { registerLocaleData } from '@angular/common';
import { authInterceptor } from './interceptores/auth.interceptor';

registerLocaleData(localeGt);

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    // Registrar el interceptor aquí
    provideHttpClient(withInterceptors([authInterceptor])), // Registrar el proveedor de peticiones HTTP
    { provide: LOCALE_ID, useValue: 'es-GT' },
  ],
};
