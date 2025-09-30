package com.angie.vivero.models;

import jakarta.persistence.*;

@Entity
@Table(name = "viveros", )
public class ViveroModel {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private Long   id;

    @Column(nullable=false)
    private String codigoPropietario;
    @Column(nullable=false)
    private String nombre;
    @Column(nullable=false)
    private String departamento;
    @Column(nullable=false)
    private String municipio;
    @Column(nullable=false)
    private String email;

//    @OneToMany(mappedBy = "vivero", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<LaborModel> labores;
}
