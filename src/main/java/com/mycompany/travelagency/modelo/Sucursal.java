package com.mycompany.travelagency.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Sucursal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "codigo_sucursal", nullable = false, unique = true)
    private String codigoSucursal;
    
    @Column (nullable = false)
    private String ciudad;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String telefono;

    // Constructor vacío (requerido por JPA)
    public Sucursal() {}

    // Constructor con parámetros
    public Sucursal(String codigoSucursal, String direccion, String telefono, String ciudad) {
        this.codigoSucursal = codigoSucursal;
        this.direccion = direccion;
        this.telefono = telefono;
        this.ciudad = ciudad;
    }

    // Getters y Setters

    public Long getId() {
        return id;
    }

    public String getCodigoSucursal() {
        return codigoSucursal;
    }

    public String getDireccion() {
        return direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setCodigoSucursal(String codigoSucursal) {
        this.codigoSucursal = codigoSucursal;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Sucursal " + codigoSucursal + " - Dirección: " + direccion + ", Teléfono: " + telefono + ", Ciudad: " + ciudad;
    }
}
