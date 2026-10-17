
package com.srmelqui.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "producto")
public class Producto {
    @Id // Define la llave primaria.
    @GeneratedValue(strategy = GenerationType.IDENTITY ) //  Indica que el ID se incrementará automáticamente.
    private Integer id;
    private String nombre;
    private Double precio;

    public Producto() {
    }
    
    public Producto(Integer id, String nombre, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + this.id + ", nombre=" + this.nombre + ", precio=" + this.precio + '}';
    }
    
    
}
