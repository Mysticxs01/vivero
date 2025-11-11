package com.angie.vivero.services;

import com.angie.vivero.models.*;
import com.angie.vivero.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para LaborService.
 * 
 * HU-2: Como productor, quiero registrar una labor realizada en un vivero 
 * con el producto de control utilizado, para llevar trazabilidad de las 
 * actividades agrícolas.
 */
@SpringBootTest
@Transactional
class LaborServiceTest {

    @Autowired
    private LaborService laborService;

    @Autowired
    private ProductorRepository productorRepository;

    @Autowired
    private FincaRepository fincaRepository;

    @Autowired
    private ViveroRepository viveroRepository;

    @Autowired
    private ProductoControlHongoRepository productoControlHongoRepository;

    @Autowired
    private LaborRepository laborRepository;

    private ViveroModel vivero;
    private ProductoControlHongoModel productoControl;

    @BeforeEach
    void setUp() {
        // Limpiar datos
        laborRepository.deleteAll();
        viveroRepository.deleteAll();
        fincaRepository.deleteAll();
        productorRepository.deleteAll();
        productoControlHongoRepository.deleteAll();

        // Crear datos de prueba
        ProductorModel productor = new ProductorModel();
        productor.setDocumento("1234567890");
        productor.setNombre("Juan");
        productor.setApellido("Pérez");
        productor.setTelefono("3001234567");
        productor.setCorreo("juan@email.com");
        productor = productorRepository.save(productor);

        FincaModel finca = new FincaModel();
        finca.setNumeroCatastro("CAT-001");
        finca.setMunicipio("Medellín");
        finca.setProductor(productor);
        finca = fincaRepository.save(finca);

        vivero = new ViveroModel();
        vivero.setCodigo("VIV-001");
        vivero.setTipoCultivo("Café");
        vivero.setFinca(finca);
        vivero = viveroRepository.save(vivero);

        productoControl = new ProductoControlHongoModel();
        productoControl.setRegistroICA("ICA-001");
        productoControl.setNombreProducto("Fungicida Test");
        productoControl.setFrecuenciaAplicacion(15);
        productoControl.setValor(new BigDecimal("50000"));
        productoControl.setNombreHongo("Roya");
        productoControl.setPeriodoCarencia(7);
        productoControl = productoControlHongoRepository.save(productoControl);
    }

    /**
     * HU-2 Criterio de Aceptación 1:
     * Dado que soy productor
     * Cuando registro una labor con fecha, descripción y vivero
     * Entonces el sistema debe guardar la labor correctamente
     */
    @Test
    void testRegistrarLabor_ConDatosValidos_DebeGuardarLabor() {
        // Arrange
        LaborModel labor = new LaborModel();
        labor.setFecha(LocalDate.of(2025, 11, 11));
        labor.setDescripcion("Aplicación de fertilizante");

        // Act
        LaborModel laborGuardada = laborService.registrarLabor(labor, vivero.getId());

        // Assert
        assertNotNull(laborGuardada.getId(), "La labor debe tener un ID");
        assertEquals(LocalDate.of(2025, 11, 11), laborGuardada.getFecha());
        assertEquals("Aplicación de fertilizante", laborGuardada.getDescripcion());
        assertEquals(vivero.getId(), laborGuardada.getVivero().getId());
        assertNull(laborGuardada.getProductoControl(), "No debe tener producto de control");
    }

    /**
     * HU-2 Criterio de Aceptación 1 (caso negativo):
     * Dado que soy productor
     * Cuando intento registrar una labor con un vivero inexistente
     * Entonces el sistema debe lanzar una excepción
     */
    @Test
    void testRegistrarLabor_ConViveroInexistente_DebeLanzarExcepcion() {
        // Arrange
        LaborModel labor = new LaborModel();
        labor.setFecha(LocalDate.now());
        labor.setDescripcion("Labor de prueba");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            laborService.registrarLabor(labor, 999L);
        });
    }

    /**
     * HU-2 Criterio de Aceptación 2:
     * Dado que soy productor
     * Cuando registro una labor con un producto de control
     * Entonces el sistema debe asociar el producto a la labor
     */
    @Test
    void testRegistrarLaborConProducto_ConProductoValido_DebeAsociarProducto() {
        // Arrange
        LaborModel labor = new LaborModel();
        labor.setFecha(LocalDate.of(2025, 11, 10));
        labor.setDescripcion("Aplicación de fungicida");

        // Act
        LaborModel laborGuardada = laborService.registrarLaborConProducto(
                labor, vivero.getId(), productoControl.getId());

        // Assert
        assertNotNull(laborGuardada.getId());
        assertEquals("Aplicación de fungicida", laborGuardada.getDescripcion());
        assertNotNull(laborGuardada.getProductoControl(), "Debe tener producto de control");
        assertEquals(productoControl.getId(), laborGuardada.getProductoControl().getId());
    }

    /**
     * HU-2 Criterio de Aceptación 2 (caso sin producto):
     * Dado que soy productor
     * Cuando registro una labor sin producto de control
     * Entonces el sistema debe guardar la labor sin producto
     */
    @Test
    void testRegistrarLaborConProducto_SinProducto_DebeGuardarSinProducto() {
        // Arrange
        LaborModel labor = new LaborModel();
        labor.setFecha(LocalDate.now());
        labor.setDescripcion("Poda de plantas");

        // Act
        LaborModel laborGuardada = laborService.registrarLaborConProducto(
                labor, vivero.getId(), null);

        // Assert
        assertNotNull(laborGuardada.getId());
        assertNull(laborGuardada.getProductoControl());
    }

    /**
     * HU-2 Criterio de Aceptación 2 (caso negativo):
     * Dado que soy productor
     * Cuando intento registrar una labor con un producto inexistente
     * Entonces el sistema debe lanzar una excepción
     */
    @Test
    void testRegistrarLaborConProducto_ConProductoInexistente_DebeLanzarExcepcion() {
        // Arrange
        LaborModel labor = new LaborModel();
        labor.setFecha(LocalDate.now());
        labor.setDescripcion("Labor de prueba");

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            laborService.registrarLaborConProducto(labor, vivero.getId(), 999L);
        });
    }

    /**
     * HU-2 Criterio de Aceptación 3:
     * Dado que soy productor
     * Cuando consulto las labores de un vivero específico
     * Entonces el sistema debe retornar solo las labores de ese vivero
     */
    @Test
    void testObtenerLaboresPorVivero_ConLaboresExistentes_DebeRetornarLaboresDelVivero() {
        // Arrange
        LaborModel labor1 = new LaborModel();
        labor1.setFecha(LocalDate.now());
        labor1.setDescripcion("Labor 1");
        laborService.registrarLabor(labor1, vivero.getId());

        LaborModel labor2 = new LaborModel();
        labor2.setFecha(LocalDate.now().minusDays(1));
        labor2.setDescripcion("Labor 2");
        laborService.registrarLabor(labor2, vivero.getId());

        // Act
        List<LaborModel> labores = laborService.obtenerLaboresPorVivero(vivero.getId());

        // Assert
        assertEquals(2, labores.size());
        assertTrue(labores.stream().allMatch(l -> l.getVivero().getId().equals(vivero.getId())));
    }

    /**
     * HU-2 Criterio de Aceptación 4:
     * Dado que soy productor
     * Cuando consulto labores por rango de fechas
     * Entonces el sistema debe retornar solo las labores en ese rango
     */
    @Test
    void testObtenerLaboresPorRangoFechas_ConLaboresEnRango_DebeRetornarSoloLasDelRango() {
        // Arrange
        LaborModel labor1 = new LaborModel();
        labor1.setFecha(LocalDate.of(2025, 11, 5));
        labor1.setDescripcion("Labor del 5");
        laborService.registrarLabor(labor1, vivero.getId());

        LaborModel labor2 = new LaborModel();
        labor2.setFecha(LocalDate.of(2025, 11, 10));
        labor2.setDescripcion("Labor del 10");
        laborService.registrarLabor(labor2, vivero.getId());

        LaborModel labor3 = new LaborModel();
        labor3.setFecha(LocalDate.of(2025, 11, 15));
        labor3.setDescripcion("Labor del 15");
        laborService.registrarLabor(labor3, vivero.getId());

        // Act
        List<LaborModel> labores = laborService.obtenerLaboresPorRangoFechas(
                LocalDate.of(2025, 11, 7),
                LocalDate.of(2025, 11, 12));

        // Assert
        assertEquals(1, labores.size());
        assertEquals("Labor del 10", labores.get(0).getDescripcion());
    }

    /**
     * HU-2 Criterio de Aceptación 5:
     * Dado que soy productor
     * Cuando consulto labores por producto de control
     * Entonces el sistema debe retornar solo las labores que usaron ese producto
     */
    @Test
    void testObtenerLaboresPorProductoControl_ConLaboresQueUsaronProducto_DebeRetornarEsasLabores() {
        // Arrange
        LaborModel labor1 = new LaborModel();
        labor1.setFecha(LocalDate.now());
        labor1.setDescripcion("Aplicación de fungicida 1");
        laborService.registrarLaborConProducto(labor1, vivero.getId(), productoControl.getId());

        LaborModel labor2 = new LaborModel();
        labor2.setFecha(LocalDate.now().minusDays(1));
        labor2.setDescripcion("Poda sin producto");
        laborService.registrarLaborConProducto(labor2, vivero.getId(), null);

        LaborModel labor3 = new LaborModel();
        labor3.setFecha(LocalDate.now().minusDays(2));
        labor3.setDescripcion("Aplicación de fungicida 2");
        laborService.registrarLaborConProducto(labor3, vivero.getId(), productoControl.getId());

        // Act
        List<LaborModel> labores = laborService.obtenerLaboresPorProductoControl(productoControl.getId());

        // Assert
        assertEquals(2, labores.size());
        assertTrue(labores.stream()
                .allMatch(l -> l.getProductoControl() != null && 
                          l.getProductoControl().getId().equals(productoControl.getId())));
    }

    /**
     * Test adicional: Actualizar labor
     */
    @Test
    void testActualizarLabor_ConDatosValidos_DebeActualizarLabor() {
        // Arrange
        LaborModel labor = new LaborModel();
        labor.setFecha(LocalDate.now());
        labor.setDescripcion("Descripción original");
        LaborModel laborGuardada = laborService.registrarLabor(labor, vivero.getId());

        LaborModel laborActualizada = new LaborModel();
        laborActualizada.setFecha(LocalDate.now().plusDays(1));
        laborActualizada.setDescripcion("Descripción actualizada");

        // Act
        LaborModel resultado = laborService.actualizarLabor(laborGuardada.getId(), laborActualizada);

        // Assert
        assertEquals("Descripción actualizada", resultado.getDescripcion());
        assertEquals(LocalDate.now().plusDays(1), resultado.getFecha());
    }

    /**
     * Test adicional: Eliminar labor
     */
    @Test
    void testEliminarLabor_ConLaborExistente_DebeEliminarLabor() {
        // Arrange
        LaborModel labor = new LaborModel();
        labor.setFecha(LocalDate.now());
        labor.setDescripcion("Labor a eliminar");
        LaborModel laborGuardada = laborService.registrarLabor(labor, vivero.getId());

        // Act
        laborService.eliminarLabor(laborGuardada.getId());

        // Assert
        assertTrue(laborService.obtenerLaborPorId(laborGuardada.getId()).isEmpty());
    }
}
