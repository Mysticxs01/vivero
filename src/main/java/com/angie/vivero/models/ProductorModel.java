package com.angie.vivero.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "productores")
public class ProductorModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El documento es obligatorio")
    @Column(nullable = false, unique = true)
    private String documento;
    
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;
    
    @NotBlank(message = "El apellido es obligatorio")
    @Column(nullable = false)
    private String apellido;
    
    @NotBlank(message = "El teléfono es obligatorio")
    @Column(nullable = false)
    private String telefono;
    
    @NotBlank(message = "El correo es obligatorio")
    @Column(nullable = false)
    private String correo;

    @OneToMany(mappedBy = "productor", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FincaModel> fincas = new ArrayList<>();

    public ProductorModel() {
    }

    public ProductorModel(String documento, String nombre, String apellido, String telefono, String correo) {
        this.documento = documento;
        this.nombre = nombre;
        this.apellido = apellido;
        this.telefono = telefono;
        this.correo = correo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public List<FincaModel> getFincas() {
        return fincas;
    }

    public void setFincas(List<FincaModel> fincas) {
        this.fincas = fincas;
    }
    
    public void addFinca(FincaModel finca) {
        fincas.add(finca);
        finca.setProductor(this);
    }
    
    public void removeFinca(FincaModel finca) {
        fincas.remove(finca);
        finca.setProductor(null);
    }
}

