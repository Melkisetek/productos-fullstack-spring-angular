package com.srmelqui.api.service;

import com.srmelqui.api.entity.Producto;
import java.util.List;

public interface IProductoService {

    List<Producto> listarProductos();

    Producto buscarProductoPorId(Integer idProducto);

    Producto guardarProducto(Producto producto);

    void eliminarProductoPorId(Integer idProducto);
}
