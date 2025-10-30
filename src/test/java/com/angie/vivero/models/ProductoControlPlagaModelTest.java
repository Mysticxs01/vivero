package com.angie.vivero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoControlPlagaModelTest {

    private ProductoControlPlagaModel productoPlaga;

    @BeforeEach
    void setUp() {
        productoPlaga = new ProductoControlPlagaModel();
    }

    @Test
    void testProductoControlPlagaCreacionConConstructor() {
        ProductoControlPlagaModel producto = new ProductoControlPlagaModel(
            "ICA-P001", "Insecticida DEF", 10, new BigDecimal("45000"), 7
        );
        
        assertNotNull(producto);
        assertEquals("ICA-P001", producto.getRegistroICA());
        assertEquals("Insecticida DEF", producto.getNombreProducto());
        assertEquals(10, producto.getFrecuenciaAplicacion());
        assertEquals(new BigDecimal("45000"), producto.getValor());
        assertEquals(7, producto.getPeriodoCarencia());
    }

    @Test
    void testProductoControlPlagaSettersGetters() {
        productoPlaga.setRegistroICA("ICA-P002");
        productoPlaga.setNombreProducto("Pesticida GHI");
        productoPlaga.setFrecuenciaAplicacion(14);
        productoPlaga.setValor(new BigDecimal("55000"));
        productoPlaga.setPeriodoCarencia(10);

        assertEquals("ICA-P002", productoPlaga.getRegistroICA());
        assertEquals("Pesticida GHI", productoPlaga.getNombreProducto());
        assertEquals(14, productoPlaga.getFrecuenciaAplicacion());
        assertEquals(new BigDecimal("55000"), productoPlaga.getValor());
        assertEquals(10, productoPlaga.getPeriodoCarencia());
    }

    @Test
    void testProductoControlPlagaCamposObligatorios() {
        assertNull(productoPlaga.getRegistroICA());
        assertNull(productoPlaga.getNombreProducto());
        assertNull(productoPlaga.getFrecuenciaAplicacion());
        assertNull(productoPlaga.getValor());
        assertNull(productoPlaga.getPeriodoCarencia());
    }

    @Test
    void testProductoControlPlagaPeriodoCarencia() {
        productoPlaga.setPeriodoCarencia(15);
        assertEquals(15, productoPlaga.getPeriodoCarencia());
    }

    @Test
    void testProductoControlPlagaFrecuenciaAplicacion() {
        productoPlaga.setFrecuenciaAplicacion(21);
        assertEquals(21, productoPlaga.getFrecuenciaAplicacion());
    }

    @Test
    void testProductoControlPlagaValorDecimal() {
        BigDecimal valor = new BigDecimal("98765.43");
        productoPlaga.setValor(valor);
        assertEquals(valor, productoPlaga.getValor());
    }
}
