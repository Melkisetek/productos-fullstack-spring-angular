import { Routes } from '@angular/router';
import { ProductosComponent } from './componentes/lista-productos/productos.component';
import { EditarComponent } from './componentes/editar/editar.component';

export const routes: Routes = [
  {
    path: 'productos',
    component: ProductosComponent,
  },

  { path: 'editarProducto/:idProducto', component: EditarComponent },
  { path: '', redirectTo: 'productos', pathMatch: 'full' }, // Redirección por defecto
];
