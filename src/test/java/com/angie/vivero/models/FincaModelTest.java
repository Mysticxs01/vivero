package com.angie.vivero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FincaModelTest {

    private ProductorModel productor;
    private FincaModel finca;

    @BeforeEach
    void setUp() {
        productor = new ProductorModel("123456789", "Juan", "Perez", "3001234567", "juan@email.com");
        finca = new FincaModel();
    }

    @Test
    void testFincaCreacionConConstructor() {
        FincaModel f = new FincaModel("CAT12345", "Bogotá", productor);
        
        assertNotNull(f);
        assertEquals("CAT12345", f.getNumeroCatastro());
        assertEquals("Bogotá", f.getMunicipio());
        assertEquals(productor, f.getProductor());
    }

    @Test
    void testFincaSettersGetters() {
        finca.setNumeroCatastro("CAT67890");
        finca.setMunicipio("Medellín");
        finca.setProductor(productor);

        assertEquals("CAT67890", finca.getNumeroCatastro());
        assertEquals("Medellín", finca.getMunicipio());
        assertEquals(productor, finca.getProductor());
    }

    @Test
    void testFincaInicializacionViveros() {
        assertNotNull(finca.getViveros());
        assertTrue(finca.getViveros().isEmpty());
    }

    @Test
    void testFincaAgregarVivero() {
        finca.setNumeroCatastro("CAT001");
        finca.setMunicipio("Cali");
        finca.setProductor(productor);
        
        ViveroModel vivero = new ViveroModel("V001", "Café", finca);
        finca.addVivero(vivero);

        assertEquals(1, finca.getViveros().size());
        assertTrue(finca.getViveros().contains(vivero));
        assertEquals(finca, vivero.getFinca());
    }

    @Test
    void testFincaEliminarVivero() {
        finca.setNumeroCatastro("CAT002");
        finca.setMunicipio("Cartagena");
        finca.setProductor(productor);
        
        ViveroModel vivero = new ViveroModel("V002", "Tomate", finca);
        finca.addVivero(vivero);
        assertEquals(1, finca.getViveros().size());

        finca.removeVivero(vivero);
        assertEquals(0, finca.getViveros().size());
        assertNull(vivero.getFinca());
    }

    @Test
    void testFincaMultiplesViveros() {
        finca.setNumeroCatastro("CAT003");
        finca.setMunicipio("Bucaramanga");
        finca.setProductor(productor);
        
        ViveroModel vivero1 = new ViveroModel("V003", "Plátano", finca);
        ViveroModel vivero2 = new ViveroModel("V004", "Yuca", finca);
        
        finca.addVivero(vivero1);
        finca.addVivero(vivero2);

        assertEquals(2, finca.getViveros().size());
        assertTrue(finca.getViveros().contains(vivero1));
        assertTrue(finca.getViveros().contains(vivero2));
    }
}
