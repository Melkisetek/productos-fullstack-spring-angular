import { Component, inject } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { Producto } from '../../model/producto.model';
import { ProductoService } from '../../services/producto.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-editar',
  imports: [CommonModule, FormsModule, ReactiveFormsModule],
  templateUrl: './editar.component.html',
  styleUrl: './editar.component.css',
})
export class EditarComponent {
  producto: Producto = {
    nombre: '',
    precio: 0,
  } as Producto;

  id!: number;

  mensaje!: string;

  private productoService = inject(ProductoService);
  private router = inject(Router);
  private route = inject(ActivatedRoute);

  onSubmit() {
    this.guardarProducto();
  }

  ngOnInit() {
    this.editarEmpleado();
  }

  editarEmpleado() {
    // obtener id de la ruta
    this.id = this.route.snapshot.params['idProducto'];
    if (!this.id) {
      console.warn('No se encontro el ID de la ruta');
      this.irProductoLista();
      return;
    }

    // cargar los datos en el formulario
    if (this.id) {
      this.productoService
        .obtenerProductoPorId(this.id)
        .subscribe((producto: Producto | null) => {
          if (producto) {
            this.producto = { ...producto };
          } else {
            this.mensaje = 'Producto no encontrado';
            this.irProductoLista();
          }
        });
    } else {
      this.mensaje = 'No se encontro el ID del producto en la ruta';
      this.irProductoLista();
    }
  }

  guardarProducto() {
    this.productoService.editarProducto(this.id, this.producto).subscribe({
      next: (datos) => this.irProductoLista(),
      error: (errores) => console.log(errores),
    });
  }

  irProductoLista() {
    this.router.navigate(['/productos']);
  }
}
