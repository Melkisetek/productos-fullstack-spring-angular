import { Routes } from '@angular/router';
import { ProductosComponent } from './componentes/lista-productos/productos.component';
import { EditarComponent } from './componentes/editar/editar.component';
import { LoginComponent } from './componentes/login/login.component';
import { authGuard } from './guardianes/auth.guard';
import { NavegacionComponent } from './componentes/navegacion/navegacion.component';

export const routes: Routes = [
  // Ruta pública
  { path: 'login', component: LoginComponent },
  // Rutas protegidas
  { path: 'nav', component: NavegacionComponent, canActivate: [authGuard] },
  {
    path: 'productos',
    component: ProductosComponent,
    canActivate: [authGuard],
  },

  {
    path: 'editarProducto/:idProducto',
    component: EditarComponent,
    canActivate: [authGuard],
  },

  // Ridirecciones por defecto
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: '**', redirectTo: 'login' }, // Redirige cualquier ruta inexistente al login
];
