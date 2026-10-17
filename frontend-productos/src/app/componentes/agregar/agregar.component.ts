import {
  Component,
  Output,
  EventEmitter,
  ElementRef,
  ViewChild,
  AfterViewInit,
} from '@angular/core';
import { ProductoService } from '../../services/producto.service';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { Producto } from '../../model/producto.model';
import { CommonModule } from '@angular/common';
import { NgForm } from '@angular/forms';
// 1. ELIMINA EL IMPORT DE BOOTSTRAP Y CAMBIALO POR ESTA LÍNEA:
declare var bootstrap: any;

@Component({
  selector: 'app-agregar',
  imports: [FormsModule, CommonModule, RouterLink],
  templateUrl: './agregar.component.html',
  styleUrl: './agregar.component.css',
})
export class AgregarComponent {
  @Output() notificarPadre = new EventEmitter<void>();

  // Objeto vinculado al formulario para crear nuevos productos
  nuevoProducto: Producto = {
    nombre: '',
    precio: 0,
  };

  constructor(
    private productoService: ProductoService,
    private router: Router,
  ) {}

  agregarProducto(productoForm: NgForm) {
    // value: Objeto con los datos actuales.
    // valid: Booleano si el formulario pasa las validaciones.

    const { value, valid } = productoForm;

    if (
      this.nuevoProducto.nombre.trim() === '' ||
      this.nuevoProducto.precio <= 0
    ) {
      alert('Por favor, llena los campos con valores válidos.');
      return;
    }

    if (valid) {
      this.productoService.postProducto(value).subscribe({
        next: (productoGuardado) => {
          productoForm.resetForm();
          this.notificarPadre.emit(); // Avisa que ya se guardó
          // Limpiamos el formulario
          this.nuevoProducto = { nombre: '', precio: 0 };
        },
        error: (error) => {
          console.error('Error al guardar el producto', error);
        },
      });
    }
  }

  cerrarModal() {
    this.router.navigate(['']);
  }
}
