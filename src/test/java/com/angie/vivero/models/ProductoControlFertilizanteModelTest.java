package com.angie.vivero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoControlFertilizanteModelTest {

    private ProductoControlFertilizanteModel productoFertilizante;

    @BeforeEach
    void setUp() {
        productoFertilizante = new ProductoControlFertilizanteModel();
    }

    @Test
    void testProductoControlFertilizanteCreacionConConstructor() {
        LocalDate fecha = LocalDate.of(2025, 10, 1);
        ProductoControlFertilizanteModel producto = new ProductoControlFertilizanteModel(
            "ICA-F001", "Fertilizante NPK", 30, new BigDecimal("80000"), fecha
        );
        
        assertNotNull(producto);
        assertEquals("ICA-F001", producto.getRegistroICA());
        assertEquals("Fertilizante NPK", producto.getNombreProducto());
        assertEquals(30, producto.getFrecuenciaAplicacion());
        assertEquals(new BigDecimal("80000"), producto.getValor());
        assertEquals(fecha, producto.getFechaUltimaAplicacion());
    }

    @Test
    void testProductoControlFertilizanteSettersGetters() {
        LocalDate fecha = LocalDate.of(2025, 10, 15);
        
        productoFertilizante.setRegistroICA("ICA-F002");
        productoFertilizante.setNombreProducto("Urea Granulada");
        productoFertilizante.setFrecuenciaAplicacion(45);
        productoFertilizante.setValor(new BigDecimal("65000"));
        productoFertilizante.setFechaUltimaAplicacion(fecha);

        assertEquals("ICA-F002", productoFertilizante.getRegistroICA());
        assertEquals("Urea Granulada", productoFertilizante.getNombreProducto());
        assertEquals(45, productoFertilizante.getFrecuenciaAplicacion());
        assertEquals(new BigDecimal("65000"), productoFertilizante.getValor());
        assertEquals(fecha, productoFertilizante.getFechaUltimaAplicacion());
    }

    @Test
    void testProductoControlFertilizanteCamposObligatorios() {
        assertNull(productoFertilizante.getRegistroICA());
        assertNull(productoFertilizante.getNombreProducto());
        assertNull(productoFertilizante.getFrecuenciaAplicacion());
        assertNull(productoFertilizante.getValor());
        assertNull(productoFertilizante.getFechaUltimaAplicacion());
    }

    @Test
    void testProductoControlFertilizanteFechaUltimaAplicacion() {
        LocalDate fecha = LocalDate.of(2025, 9, 20);
        productoFertilizante.setFechaUltimaAplicacion(fecha);
        assertEquals(fecha, productoFertilizante.getFechaUltimaAplicacion());
    }

    @Test
    void testProductoControlFertilizanteFrecuenciaAplicacion() {
        productoFertilizante.setFrecuenciaAplicacion(60);
        assertEquals(60, productoFertilizante.getFrecuenciaAplicacion());
    }

    @Test
    void testProductoControlFertilizanteValorDecimal() {
        BigDecimal valor = new BigDecimal("150000.50");
        productoFertilizante.setValor(valor);
        assertEquals(valor, productoFertilizante.getValor());
    }
}
