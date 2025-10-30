package com.angie.vivero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductoControlHongoModelTest {

    private ProductoControlHongoModel productoHongo;

    @BeforeEach
    void setUp() {
        productoHongo = new ProductoControlHongoModel();
    }

    @Test
    void testProductoControlHongoCreacionConConstructor() {
        ProductoControlHongoModel producto = new ProductoControlHongoModel(
            "ICA-H001", "Fungicida ABC", 20, new BigDecimal("75000"), 10, "Roya del café"
        );
        
        assertNotNull(producto);
        assertEquals("ICA-H001", producto.getRegistroICA());
        assertEquals("Fungicida ABC", producto.getNombreProducto());
        assertEquals(20, producto.getFrecuenciaAplicacion());
        assertEquals(new BigDecimal("75000"), producto.getValor());
        assertEquals(10, producto.getPeriodoCarencia());
        assertEquals("Roya del café", producto.getNombreHongo());
    }

    @Test
    void testProductoControlHongoSettersGetters() {
        productoHongo.setRegistroICA("ICA-H002");
        productoHongo.setNombreProducto("Antifungal XYZ");
        productoHongo.setFrecuenciaAplicacion(15);
        productoHongo.setValor(new BigDecimal("60000"));
        productoHongo.setPeriodoCarencia(14);
        productoHongo.setNombreHongo("Mildiu polvoriento");

        assertEquals("ICA-H002", productoHongo.getRegistroICA());
        assertEquals("Antifungal XYZ", productoHongo.getNombreProducto());
        assertEquals(15, productoHongo.getFrecuenciaAplicacion());
        assertEquals(new BigDecimal("60000"), productoHongo.getValor());
        assertEquals(14, productoHongo.getPeriodoCarencia());
        assertEquals("Mildiu polvoriento", productoHongo.getNombreHongo());
    }

    @Test
    void testProductoControlHongoCamposObligatorios() {
        assertNull(productoHongo.getRegistroICA());
        assertNull(productoHongo.getNombreProducto());
        assertNull(productoHongo.getFrecuenciaAplicacion());
        assertNull(productoHongo.getValor());
        assertNull(productoHongo.getPeriodoCarencia());
        assertNull(productoHongo.getNombreHongo());
    }

    @Test
    void testProductoControlHongoPeriodoCarencia() {
        productoHongo.setPeriodoCarencia(21);
        assertEquals(21, productoHongo.getPeriodoCarencia());
    }

    @Test
    void testProductoControlHongoNombreHongo() {
        productoHongo.setNombreHongo("Fusarium oxysporum");
        assertEquals("Fusarium oxysporum", productoHongo.getNombreHongo());
    }

    @Test
    void testProductoControlHongoValorDecimal() {
        BigDecimal valor = new BigDecimal("123456.78");
        productoHongo.setValor(valor);
        assertEquals(valor, productoHongo.getValor());
    }
}
