package com.angie.vivero.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LaborModelTest {

    private ProductorModel productor;
    private FincaModel finca;
    private ViveroModel vivero;
    private LaborModel labor;

    @BeforeEach
    void setUp() {
        productor = new ProductorModel("123456789", "Juan", "Perez", "3001234567", "juan@email.com");
        finca = new FincaModel("CAT12345", "Bogotá", productor);
        vivero = new ViveroModel("V001", "Café", finca);
        labor = new LaborModel();
    }

    @Test
    void testLaborCreacionConConstructor() {
        LocalDate fecha = LocalDate.of(2025, 10, 15);
        LaborModel l = new LaborModel(fecha, "Aplicación de fertilizante", vivero);
        
        assertNotNull(l);
        assertEquals(fecha, l.getFecha());
        assertEquals("Aplicación de fertilizante", l.getDescripcion());
        assertEquals(vivero, l.getVivero());
    }

    @Test
    void testLaborSettersGetters() {
        LocalDate fecha = LocalDate.of(2025, 10, 20);
        
        labor.setFecha(fecha);
        labor.setDescripcion("Control de plagas");
        labor.setVivero(vivero);

        assertEquals(fecha, labor.getFecha());
        assertEquals("Control de plagas", labor.getDescripcion());
        assertEquals(vivero, labor.getVivero());
    }

    @Test
    void testLaborConProductoControl() {
        LocalDate fecha = LocalDate.of(2025, 10, 25);
        ProductoControlPlagaModel productoPlaga = new ProductoControlPlagaModel(
            "ICA123", "Insecticida X", 15, new java.math.BigDecimal("50000"), 7
        );
        
        labor.setFecha(fecha);
        labor.setDescripcion("Aplicación de insecticida");
        labor.setVivero(vivero);
        labor.setProductoControl(productoPlaga);

        assertNotNull(labor.getProductoControl());
        assertEquals(productoPlaga, labor.getProductoControl());
        assertEquals("ICA123", labor.getProductoControl().getRegistroICA());
    }

    @Test
    void testLaborFechaObligatoria() {
        labor.setDescripcion("Riego");
        labor.setVivero(vivero);
        
        assertNull(labor.getFecha());
    }

    @Test
    void testLaborDescripcionObligatoria() {
        labor.setFecha(LocalDate.now());
        labor.setVivero(vivero);
        
        assertNull(labor.getDescripcion());
    }

    @Test
    void testLaborViveroObligatorio() {
        labor.setFecha(LocalDate.now());
        labor.setDescripcion("Limpieza del terreno");
        
        assertNull(labor.getVivero());
    }
}
