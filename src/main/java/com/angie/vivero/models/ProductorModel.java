package com.angie.vivero.models;

import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "productores")
public class ProductorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long   id;
    private String nombre;
    private String apellido;
    private String documento;
    private String email;

    @OneToMany(mappedBy = "productor", cascade = CascadeType.ALL)
    private Set<ViveroModel> vivero;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }



}

