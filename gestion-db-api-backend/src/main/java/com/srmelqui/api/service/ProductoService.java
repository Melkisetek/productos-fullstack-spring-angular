package com.srmelqui.api.service;

import com.srmelqui.api.model.Producto;
import com.srmelqui.api.repository.ProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService {
    
    @Autowired
    private ProductoRepository productoRepository;
    
    @Override
    public List<Producto> listarProductos() {
        List<Producto> productos = this.productoRepository.findAll();
        return productos;
    }
    
    @Override
    public Producto buscarProductoPorId(Integer idProducto) {
        return this.productoRepository.findById(idProducto).orElse(null);
    }
    
    @Override
    public Producto guardarProducto(Producto producto) {
        Producto guardar = this.productoRepository.save(producto);
        return guardar;
    }
    
    @Override
    public void eliminarProductoPorId(Integer idProducto) {
        this.productoRepository.deleteById(idProducto);
    }
    
}
