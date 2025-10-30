package com.angie.vivero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ViveroModelTest {

    private ProductorModel productor;
    private FincaModel finca;
    private ViveroModel vivero;

    @BeforeEach
    void setUp() {
        productor = new ProductorModel("123456789", "Juan", "Perez", "3001234567", "juan@email.com");
        finca = new FincaModel("CAT12345", "Bogotá", productor);
        vivero = new ViveroModel();
    }

    @Test
    void testViveroCreacionConConstructor() {
        ViveroModel v = new ViveroModel("V001", "Café", finca);
        
        assertNotNull(v);
        assertEquals("V001", v.getCodigo());
        assertEquals("Café", v.getTipoCultivo());
        assertEquals(finca, v.getFinca());
    }

    @Test
    void testViveroSettersGetters() {
        vivero.setCodigo("V002");
        vivero.setTipoCultivo("Tomate");
        vivero.setFinca(finca);

        assertEquals("V002", vivero.getCodigo());
        assertEquals("Tomate", vivero.getTipoCultivo());
        assertEquals(finca, vivero.getFinca());
    }

    @Test
    void testViveroInicializacionLabores() {
        assertNotNull(vivero.getLabores());
        assertTrue(vivero.getLabores().isEmpty());
    }

    @Test
    void testViveroAgregarLabor() {
        vivero.setCodigo("V003");
        vivero.setTipoCultivo("Plátano");
        vivero.setFinca(finca);
        
        LaborModel labor = new LaborModel(java.time.LocalDate.now(), "Fertilización", vivero);
        vivero.addLabor(labor);

        assertEquals(1, vivero.getLabores().size());
        assertTrue(vivero.getLabores().contains(labor));
        assertEquals(vivero, labor.getVivero());
    }

    @Test
    void testViveroEliminarLabor() {
        vivero.setCodigo("V004");
        vivero.setTipoCultivo("Yuca");
        vivero.setFinca(finca);
        
        LaborModel labor = new LaborModel(java.time.LocalDate.now(), "Riego", vivero);
        vivero.addLabor(labor);
        assertEquals(1, vivero.getLabores().size());

        vivero.removeLabor(labor);
        assertEquals(0, vivero.getLabores().size());
        assertNull(labor.getVivero());
    }

    @Test
    void testViveroMultiplesLabores() {
        vivero.setCodigo("V005");
        vivero.setTipoCultivo("Maíz");
        vivero.setFinca(finca);
        
        LaborModel labor1 = new LaborModel(java.time.LocalDate.now(), "Siembra", vivero);
        LaborModel labor2 = new LaborModel(java.time.LocalDate.now().plusDays(7), "Control de plagas", vivero);
        
        vivero.addLabor(labor1);
        vivero.addLabor(labor2);

        assertEquals(2, vivero.getLabores().size());
        assertTrue(vivero.getLabores().contains(labor1));
        assertTrue(vivero.getLabores().contains(labor2));
    }
}
