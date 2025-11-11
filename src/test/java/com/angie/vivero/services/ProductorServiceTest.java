package com.angie.vivero.services;

import com.angie.vivero.models.FincaModel;
import com.angie.vivero.models.ProductorModel;
import com.angie.vivero.repositories.FincaRepository;
import com.angie.vivero.repositories.ProductorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para ProductorService - Historia de Usuario HU-1
 * Criterios de Aceptación verificados mediante tests
 */
@SpringBootTest
@Transactional
class ProductorServiceTest {

    @Autowired
    private ProductorService productorService;

    @Autowired
    private ProductorRepository productorRepository;

    @Autowired
    private FincaRepository fincaRepository;

    @BeforeEach
    void setUp() {
        // Limpiar la base de datos antes de cada prueba
        fincaRepository.deleteAll();
        productorRepository.deleteAll();
    }

    /**
     * HU-1 Criterio de Aceptación 1: 
     * Dado que soy administrador
     * Cuando registro un productor con todos sus datos obligatorios
     * Entonces el sistema guarda el productor y retorna su ID
     */
    @Test
    void testRegistrarProductor_ConDatosValidos_DebeGuardarProductor() {
        // Arrange
        ProductorModel productor = new ProductorModel();
        productor.setDocumento("1234567890");
        productor.setNombre("Juan");
        productor.setApellido("Pérez");
        productor.setTelefono("3001234567");
        productor.setCorreo("juan.perez@email.com");

        // Act
        ProductorModel resultado = productorService.registrarProductor(productor);

        // Assert
        assertNotNull(resultado.getId(), "El productor debe tener un ID asignado");
        assertEquals("1234567890", resultado.getDocumento());
        assertEquals("Juan", resultado.getNombre());
        assertEquals("Pérez", resultado.getApellido());
        assertEquals("3001234567", resultado.getTelefono());
        assertEquals("juan.perez@email.com", resultado.getCorreo());
    }

    /**
     * HU-1 Criterio de Aceptación 1 (caso negativo): 
     * Dado que soy administrador
     * Cuando intento registrar un productor con un documento que ya existe
     * Entonces el sistema debe lanzar una excepción
     */
    @Test
    void testRegistrarProductor_ConDocumentoDuplicado_DebeLanzarExcepcion() {
        // Arrange
        ProductorModel productor1 = new ProductorModel();
        productor1.setDocumento("1234567890");
        productor1.setNombre("Juan");
        productor1.setApellido("Pérez");
        productor1.setTelefono("3001234567");
        productor1.setCorreo("juan.perez@email.com");
        productorService.registrarProductor(productor1);

        ProductorModel productor2 = new ProductorModel();
        productor2.setDocumento("1234567890");
        productor2.setNombre("María");
        productor2.setApellido("González");
        productor2.setTelefono("3009876543");
        productor2.setCorreo("maria.gonzalez@email.com");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productorService.registrarProductor(productor2);
        });

        assertTrue(exception.getMessage().contains("Ya existe un productor con el documento"));
    }

    /**
     * HU-1 Criterio de Aceptación 2: 
     * Dado que soy administrador
     * Cuando registro un productor con una o más fincas
     * Entonces el sistema guarda el productor y todas sus fincas asociadas
     */
    @Test
    void testRegistrarProductorConFincas_ConVariasFincas_DebeGuardarProductorYFincas() {
        // Arrange
        ProductorModel productor = new ProductorModel();
        productor.setDocumento("1234567890");
        productor.setNombre("Carlos");
        productor.setApellido("Rodríguez");
        productor.setTelefono("3201234567");
        productor.setCorreo("carlos.rodriguez@email.com");

        List<FincaModel> fincas = new ArrayList<>();
        
        FincaModel finca1 = new FincaModel();
        finca1.setNumeroCatastro("CAT-001-2025");
        finca1.setMunicipio("Medellín");
        fincas.add(finca1);

        FincaModel finca2 = new FincaModel();
        finca2.setNumeroCatastro("CAT-002-2025");
        finca2.setMunicipio("Envigado");
        fincas.add(finca2);

        // Act
        ProductorModel resultado = productorService.registrarProductorConFincas(productor, fincas);

        // Assert
        assertNotNull(resultado.getId(), "El productor debe tener un ID");
        assertEquals(2, resultado.getFincas().size(), "Deben haberse guardado 2 fincas");
        
        // Verificar que las fincas están en la base de datos
        List<FincaModel> fincasGuardadas = fincaRepository.findAll();
        assertEquals(2, fincasGuardadas.size());
        
        // Verificar la relación bidireccional
        for (FincaModel finca : fincasGuardadas) {
            assertEquals(resultado.getId(), finca.getProductor().getId());
        }
    }

    /**
     * HU-1 Criterio de Aceptación 3: 
     * Dado que soy administrador
     * Cuando busco un productor por su documento
     * Entonces el sistema debe retornar el productor si existe
     */
    @Test
    void testBuscarPorDocumento_ConDocumentoExistente_DebeRetornarProductor() {
        // Arrange
        ProductorModel productor = new ProductorModel();
        productor.setDocumento("9876543210");
        productor.setNombre("Ana");
        productor.setApellido("Martínez");
        productor.setTelefono("3159876543");
        productor.setCorreo("ana.martinez@email.com");
        productorService.registrarProductor(productor);

        // Act
        Optional<ProductorModel> resultado = productorService.buscarPorDocumento("9876543210");

        // Assert
        assertTrue(resultado.isPresent(), "Debe encontrar el productor");
        assertEquals("Ana", resultado.get().getNombre());
        assertEquals("Martínez", resultado.get().getApellido());
    }

    /**
     * HU-1 Criterio de Aceptación 3 (caso negativo): 
     * Dado que soy administrador
     * Cuando busco un productor por un documento que no existe
     * Entonces el sistema debe retornar vacío
     */
    @Test
    void testBuscarPorDocumento_ConDocumentoNoExistente_DebeRetornarVacio() {
        // Act
        Optional<ProductorModel> resultado = productorService.buscarPorDocumento("9999999999");

        // Assert
        assertFalse(resultado.isPresent(), "No debe encontrar ningún productor");
    }

    /**
     * HU-1 Criterio de Aceptación 4: 
     * Dado que soy administrador
     * Cuando consulto un productor por su ID
     * Entonces el sistema debe retornar el productor con todas sus fincas
     */
    @Test
    void testObtenerProductorConFincas_ConIdExistente_DebeRetornarProductorYFincas() {
        // Arrange
        ProductorModel productor = new ProductorModel();
        productor.setDocumento("5555555555");
        productor.setNombre("Luis");
        productor.setApellido("López");
        productor.setTelefono("3185555555");
        productor.setCorreo("luis.lopez@email.com");

        List<FincaModel> fincas = new ArrayList<>();
        FincaModel finca = new FincaModel();
        finca.setNumeroCatastro("CAT-003-2025");
        finca.setMunicipio("Rionegro");
        fincas.add(finca);

        ProductorModel productorGuardado = productorService.registrarProductorConFincas(productor, fincas);

        // Act
        Optional<ProductorModel> resultado = productorService.obtenerProductorConFincas(productorGuardado.getId());

        // Assert
        assertTrue(resultado.isPresent(), "Debe encontrar el productor");
        assertEquals("Luis", resultado.get().getNombre());
        assertEquals(1, resultado.get().getFincas().size(), "Debe tener 1 finca");
        assertEquals("CAT-003-2025", resultado.get().getFincas().get(0).getNumeroCatastro());
    }

    /**
     * HU-1 Criterio de Aceptación 5: 
     * Dado que soy administrador
     * Cuando agrego una nueva finca a un productor existente
     * Entonces el sistema debe guardar la finca y asociarla al productor
     */
    @Test
    void testAgregarFincaAProductor_ConProductorExistente_DebeAgregarFinca() {
        // Arrange
        ProductorModel productor = new ProductorModel();
        productor.setDocumento("1111111111");
        productor.setNombre("Pedro");
        productor.setApellido("Gómez");
        productor.setTelefono("3101111111");
        productor.setCorreo("pedro.gomez@email.com");
        ProductorModel productorGuardado = productorService.registrarProductor(productor);

        FincaModel nuevaFinca = new FincaModel();
        nuevaFinca.setNumeroCatastro("CAT-004-2025");
        nuevaFinca.setMunicipio("La Ceja");

        // Act
        FincaModel fincaGuardada = productorService.agregarFincaAProductor(productorGuardado.getId(), nuevaFinca);

        // Assert
        assertNotNull(fincaGuardada.getId(), "La finca debe tener un ID");
        assertEquals(productorGuardado.getId(), fincaGuardada.getProductor().getId(), "La finca debe estar asociada al productor");
        
        // Verificar que el productor tiene la finca
        ProductorModel productorActualizado = productorService.obtenerProductorConFincas(productorGuardado.getId()).get();
        assertEquals(1, productorActualizado.getFincas().size());
    }

    /**
     * HU-1 Criterio de Aceptación 5 (caso negativo): 
     * Dado que soy administrador
     * Cuando intento agregar una finca con un número de catastro duplicado
     * Entonces el sistema debe lanzar una excepción
     */
    @Test
    void testAgregarFincaAProductor_ConNumeroCatastroDuplicado_DebeLanzarExcepcion() {
        // Arrange
        ProductorModel productor1 = new ProductorModel();
        productor1.setDocumento("2222222222");
        productor1.setNombre("María");
        productor1.setApellido("Torres");
        productor1.setTelefono("3102222222");
        productor1.setCorreo("maria.torres@email.com");
        ProductorModel productor1Guardado = productorService.registrarProductor(productor1);

        FincaModel finca1 = new FincaModel();
        finca1.setNumeroCatastro("CAT-DUPLICADO-2025");
        finca1.setMunicipio("Medellín");
        productorService.agregarFincaAProductor(productor1Guardado.getId(), finca1);

        ProductorModel productor2 = new ProductorModel();
        productor2.setDocumento("3333333333");
        productor2.setNombre("Jorge");
        productor2.setApellido("Ramírez");
        productor2.setTelefono("3103333333");
        productor2.setCorreo("jorge.ramirez@email.com");
        ProductorModel productor2Guardado = productorService.registrarProductor(productor2);

        FincaModel finca2 = new FincaModel();
        finca2.setNumeroCatastro("CAT-DUPLICADO-2025");
        finca2.setMunicipio("Envigado");

        // Act & Assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            productorService.agregarFincaAProductor(productor2Guardado.getId(), finca2);
        });

        assertTrue(exception.getMessage().contains("Ya existe una finca con el número de catastro"));
    }

    /**
     * Prueba adicional: Verificar que se pueden obtener todos los productores
     */
    @Test
    void testObtenerTodosLosProductores_ConVariosProductores_DebeRetornarTodos() {
        // Arrange
        ProductorModel productor1 = new ProductorModel();
        productor1.setDocumento("7777777777");
        productor1.setNombre("Sofía");
        productor1.setApellido("Vargas");
        productor1.setTelefono("3107777777");
        productor1.setCorreo("sofia.vargas@email.com");
        productorService.registrarProductor(productor1);

        ProductorModel productor2 = new ProductorModel();
        productor2.setDocumento("8888888888");
        productor2.setNombre("Diego");
        productor2.setApellido("Castro");
        productor2.setTelefono("3108888888");
        productor2.setCorreo("diego.castro@email.com");
        productorService.registrarProductor(productor2);

        // Act
        List<ProductorModel> productores = productorService.obtenerTodosLosProductores();

        // Assert
        assertEquals(2, productores.size(), "Deben existir 2 productores");
    }

    /**
     * Prueba adicional: Verificar actualización de productor
     */
    @Test
    void testActualizarProductor_ConDatosValidos_DebeActualizarProductor() {
        // Arrange
        ProductorModel productor = new ProductorModel();
        productor.setDocumento("6666666666");
        productor.setNombre("Andrea");
        productor.setApellido("Morales");
        productor.setTelefono("3106666666");
        productor.setCorreo("andrea.morales@email.com");
        ProductorModel productorGuardado = productorService.registrarProductor(productor);

        ProductorModel datosActualizados = new ProductorModel();
        datosActualizados.setNombre("Andrea Carolina");
        datosActualizados.setApellido("Morales Pérez");
        datosActualizados.setTelefono("3209999999");
        datosActualizados.setCorreo("andrea.carolina@email.com");

        // Act
        ProductorModel productorActualizado = productorService.actualizarProductor(productorGuardado.getId(), datosActualizados);

        // Assert
        assertEquals("Andrea Carolina", productorActualizado.getNombre());
        assertEquals("Morales Pérez", productorActualizado.getApellido());
        assertEquals("3209999999", productorActualizado.getTelefono());
        assertEquals("andrea.carolina@email.com", productorActualizado.getCorreo());
        // El documento no debe cambiar
        assertEquals("6666666666", productorActualizado.getDocumento());
    }
}
