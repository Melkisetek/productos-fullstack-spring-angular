package com.srmelqui.api.controller;

import com.srmelqui.api.exception.RecursoNoEncontradoException;
import com.srmelqui.api.model.Producto;
import com.srmelqui.api.service.ProductoService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*") // Permite que cualquier aplicación externa consuma esta API
@RestController // Marcar clase como controlador de API que devolverá datos
// http://localhost:8080/api
@RequestMapping("/api") // Define la URL base para este recurso.
public class ProductoController {

    private static final Logger logger
            = LoggerFactory.getLogger(ProductoController.class);
    
    @Autowired // Inyección de dependencia
    private ProductoService productoService;

    // http://localhost:8080/api/productos
    @GetMapping("/productos") // Difine este método responde a peticiones de lectura
    public List<Producto> listarTodos() {
        List<Producto> productos =  this.productoService.listarProductos();
        logger.info("Listando Productos");
         productos.forEach((producto -> logger.info(producto.toString())));
        return productos;
    }

    // @RequestBody: Toma el JSON que envías desde el cliente y lo convierte automáticamente en un objeto Java.
    @PostMapping("/producto")
    public Producto guardar(@RequestBody Producto nuevo) {
        return this.productoService.guardarProducto(nuevo);
    }

    // http://localhost:8080/api/productos/<idProducto>
    @GetMapping("/productos/{id}")
    public ResponseEntity<Producto> obtenerProductoPorId(@PathVariable int id) {
        Producto producto = this.productoService.buscarProductoPorId(id);
        if (producto != null) {
            return ResponseEntity.ok(producto);
        } else {
            throw new RecursoNoEncontradoException("No se encontro el id: " + id);
        }
    }

// http://localhost:8080/api/productos/<id>
    @PutMapping("/productos/{id}")
    public ResponseEntity<Producto> actualizarProducto(
            @PathVariable int id, @RequestBody Producto productoRecibido) {
        Producto producto = this.productoService.buscarProductoPorId(id);
        producto.setNombre(productoRecibido.getNombre());
        producto.setPrecio(productoRecibido.getPrecio());
        // Guardar la información
        this.productoService.guardarProducto(producto);
        return ResponseEntity.ok(producto);
    }

    @DeleteMapping("productos/{id}")
    public ResponseEntity<Map<String, Boolean>> eliminarProducto(@PathVariable int id) {
        Producto producto = this.productoService.buscarProductoPorId(id);
        if (producto == null) {
            throw new RecursoNoEncontradoException("No se encontro el id: " + id);
        }
        this.productoService.eliminarProductoPorId(producto.getId());
        // <clave, valor>
        Map<String, Boolean> respuesta = new HashMap<>();
        respuesta.put("elimindo", Boolean.TRUE);
        return ResponseEntity.ok(respuesta);
    }
}
