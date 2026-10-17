import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Producto } from '../model/producto.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class ProductoService {
  // URL de tu API de Spring Boot en Azure (usa localhost si estás probando dentro de la VM)
  private apiUrl = 'http://localhost:8080/api/productos';
  // Si pruebas desde tu laptop personal, cambia por: 'http://<IP_PUBLICA_DE_AZURE>:8080/api/productos'

  private http = inject(HttpClient);

  // Petición GET
  getProductos(): Observable<Producto[]> {
    return this.http.get<Producto[]>(this.apiUrl);
  }

  // Petición POST
  postProducto(producto: Producto): Observable<Producto> {
    return this.http.post<Producto>(this.apiUrl, producto);
  }

  obtenerProductoPorId(idProducto: number) {
    let productoObtenido = this.http.get<Producto>(
      `${this.apiUrl}/${idProducto}`,
    );
    return productoObtenido;
  }

  editarProducto(id: number, producto: Producto): Observable<Producto> {
    let editarProductoExistente = this.http.put<Producto>(
      `${this.apiUrl}/${id}`,
      producto,
    );
    return editarProductoExistente;
  }

  eliminarProducto(id: number): Observable<Producto> {
    let eliminarProductoExistente = this.http.delete<Producto>(
      `${this.apiUrl}/${id}`,
    );

    return eliminarProductoExistente;
  }
}
