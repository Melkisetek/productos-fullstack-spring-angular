import {
  ApplicationConfig,
  LOCALE_ID,
  provideZoneChangeDetection,
} from '@angular/core';
import { provideRouter } from '@angular/router';
import { provideHttpClient } from '@angular/common/http';
import { routes } from './app.routes';
import localeGt from '@angular/common/locales/es-GT';
import { registerLocaleData } from '@angular/common';

registerLocaleData(localeGt);

export const appConfig: ApplicationConfig = {
  providers: [
    provideZoneChangeDetection({ eventCoalescing: true }),
    provideRouter(routes),
    provideHttpClient(), // Registrar el proveedor de peticiones HTTP
    { provide: LOCALE_ID, useValue: 'es-GT' },
  ],
};
