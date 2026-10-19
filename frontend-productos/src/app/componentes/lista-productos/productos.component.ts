import { Component } from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { Producto } from '../../model/producto.model';
import { AgregarComponent } from '../agregar/agregar.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

@Component({
  standalone: true,
  selector: 'app-productos',
  imports: [CommonModule, FormsModule, AgregarComponent, RouterModule], // Módulo necesarios para tablas y formularios
  templateUrl: './productos.component.html',
  styleUrl: './productos.component.css',
})
export class ProductosComponent {
  productosListar!: Producto[];

  constructor(private productoService: ProductoService) {}

  ngOnInit() {
    this.listarProductos();
  }

  listarProductos(): void {
    this.productoService.getProductos().subscribe({
      next: (respuesta) => {
        this.productosListar = respuesta; // Guardamos los datos de la API en nuestro arreglo local
      },
      error: (error) => {
        console.error('Error al conectar con la API', error);
      },
    });
  }

  eliminar(id: any) {
    this.productoService.eliminarProducto(id).subscribe({
      next: (datos) => this.listarProductos(),
      error: (errores) => console.log(errores),
    });
  }
}
