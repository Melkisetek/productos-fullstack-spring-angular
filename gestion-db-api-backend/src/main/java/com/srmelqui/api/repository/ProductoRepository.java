package com.srmelqui.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.srmelqui.api.model.Producto;
import org.springframework.stereotype.Repository;
// Capa DAO
@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>{
    // No necesita escribir código aquí, spring implementa los métodos por ti.
    
    /*
    *  JpaRepository<Producto, Long>: Al heredar de aquí, ganas automáticamente métodos como 
    *.findAll(), .save(), y .deleteById().
    **/
}
