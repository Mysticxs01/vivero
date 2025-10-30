package com.angie.vivero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductorModelTest {

    private ProductorModel productor;

    @BeforeEach
    void setUp() {
        productor = new ProductorModel();
    }

    @Test
    void testProductorCreacionConConstructor() {
        ProductorModel prod = new ProductorModel("123456789", "Juan", "Perez", "3001234567", "juan@email.com");
        
        assertNotNull(prod);
        assertEquals("123456789", prod.getDocumento());
        assertEquals("Juan", prod.getNombre());
        assertEquals("Perez", prod.getApellido());
        assertEquals("3001234567", prod.getTelefono());
        assertEquals("juan@email.com", prod.getCorreo());
    }

    @Test
    void testProductorSettersGetters() {
        productor.setDocumento("987654321");
        productor.setNombre("Maria");
        productor.setApellido("Garcia");
        productor.setTelefono("3109876543");
        productor.setCorreo("maria@email.com");

        assertEquals("987654321", productor.getDocumento());
        assertEquals("Maria", productor.getNombre());
        assertEquals("Garcia", productor.getApellido());
        assertEquals("3109876543", productor.getTelefono());
        assertEquals("maria@email.com", productor.getCorreo());
    }

    @Test
    void testProductorInicializacionFincas() {
        assertNotNull(productor.getFincas());
        assertTrue(productor.getFincas().isEmpty());
    }

    @Test
    void testProductorAgregarFinca() {
        FincaModel finca = new FincaModel("CAT001", "Bogotá", productor);
        productor.addFinca(finca);

        assertEquals(1, productor.getFincas().size());
        assertTrue(productor.getFincas().contains(finca));
        assertEquals(productor, finca.getProductor());
    }

    @Test
    void testProductorEliminarFinca() {
        FincaModel finca = new FincaModel("CAT002", "Medellín", productor);
        productor.addFinca(finca);
        assertEquals(1, productor.getFincas().size());

        productor.removeFinca(finca);
        assertEquals(0, productor.getFincas().size());
        assertNull(finca.getProductor());
    }

    @Test
    void testProductorMultiplesFincas() {
        FincaModel finca1 = new FincaModel("CAT003", "Cali", productor);
        FincaModel finca2 = new FincaModel("CAT004", "Barranquilla", productor);
        
        productor.addFinca(finca1);
        productor.addFinca(finca2);

        assertEquals(2, productor.getFincas().size());
        assertTrue(productor.getFincas().contains(finca1));
        assertTrue(productor.getFincas().contains(finca2));
    }
}
