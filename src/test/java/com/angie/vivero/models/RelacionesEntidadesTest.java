package com.angie.vivero.models;

import com.angie.vivero.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class RelacionesEntidadesTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductorRepository productorRepository;

    @Autowired
    private FincaRepository fincaRepository;

    @Autowired
    private ViveroRepository viveroRepository;

    @Autowired
    private LaborRepository laborRepository;

    @Autowired
    private ProductoControlHongoRepository productoControlHongoRepository;

    @Autowired
    private ProductoControlPlagaRepository productoControlPlagaRepository;

    @Autowired
    private ProductoControlFertilizanteRepository productoControlFertilizanteRepository;

    @BeforeEach
    void setUp() {
        // Limpieza antes de cada test
    }

    @Test
    void testRelacionProductorFinca() {
        // Crear y guardar productor
        ProductorModel productor = new ProductorModel("123456789", "Juan", "Perez", "3001234567", "juan@email.com");
        productorRepository.save(productor);
        
        // Crear y agregar finca
        FincaModel finca = new FincaModel("CAT001", "Bogotá", productor);
        productor.addFinca(finca);
        fincaRepository.save(finca);
        
        entityManager.flush();
        entityManager.clear();
        
        // Verificar relación
        Optional<ProductorModel> productorGuardado = productorRepository.findById(productor.getId());
        assertTrue(productorGuardado.isPresent());
        assertEquals(1, productorGuardado.get().getFincas().size());
        assertEquals("CAT001", productorGuardado.get().getFincas().get(0).getNumeroCatastro());
    }

    @Test
    void testRelacionFincaVivero() {
        // Crear estructura completa
        ProductorModel productor = new ProductorModel("987654321", "Maria", "Garcia", "3109876543", "maria@email.com");
        productorRepository.save(productor);
        
        FincaModel finca = new FincaModel("CAT002", "Medellín", productor);
        fincaRepository.save(finca);
        
        ViveroModel vivero1 = new ViveroModel("V001", "Café", finca);
        ViveroModel vivero2 = new ViveroModel("V002", "Tomate", finca);
        
        finca.addVivero(vivero1);
        finca.addVivero(vivero2);
        
        viveroRepository.save(vivero1);
        viveroRepository.save(vivero2);
        
        entityManager.flush();
        entityManager.clear();
        
        // Verificar relación
        Optional<FincaModel> fincaGuardada = fincaRepository.findById(finca.getId());
        assertTrue(fincaGuardada.isPresent());
        assertEquals(2, fincaGuardada.get().getViveros().size());
    }

    @Test
    void testRelacionViveroLabor() {
        // Crear estructura
        ProductorModel productor = new ProductorModel("555555555", "Carlos", "Lopez", "3155555555", "carlos@email.com");
        productorRepository.save(productor);
        
        FincaModel finca = new FincaModel("CAT003", "Cali", productor);
        fincaRepository.save(finca);
        
        ViveroModel vivero = new ViveroModel("V003", "Plátano", finca);
        viveroRepository.save(vivero);
        
        LaborModel labor1 = new LaborModel(LocalDate.of(2025, 10, 1), "Siembra", vivero);
        LaborModel labor2 = new LaborModel(LocalDate.of(2025, 10, 15), "Fertilización", vivero);
        
        vivero.addLabor(labor1);
        vivero.addLabor(labor2);
        
        laborRepository.save(labor1);
        laborRepository.save(labor2);
        
        entityManager.flush();
        entityManager.clear();
        
        // Verificar relación
        Optional<ViveroModel> viveroGuardado = viveroRepository.findById(vivero.getId());
        assertTrue(viveroGuardado.isPresent());
        assertEquals(2, viveroGuardado.get().getLabores().size());
    }

    @Test
    void testRelacionLaborProductoControl() {
        // Crear estructura
        ProductorModel productor = new ProductorModel("666666666", "Ana", "Martinez", "3166666666", "ana@email.com");
        productorRepository.save(productor);
        
        FincaModel finca = new FincaModel("CAT004", "Cartagena", productor);
        fincaRepository.save(finca);
        
        ViveroModel vivero = new ViveroModel("V004", "Yuca", finca);
        viveroRepository.save(vivero);
        
        ProductoControlPlagaModel producto = new ProductoControlPlagaModel(
            "ICA-P001", "Insecticida X", 15, new BigDecimal("50000"), 7
        );
        productoControlPlagaRepository.save(producto);
        
        LaborModel labor = new LaborModel(LocalDate.of(2025, 10, 20), "Control de plagas", vivero);
        labor.setProductoControl(producto);
        laborRepository.save(labor);
        
        entityManager.flush();
        entityManager.clear();
        
        // Verificar relación
        Optional<LaborModel> laborGuardada = laborRepository.findById(labor.getId());
        assertTrue(laborGuardada.isPresent());
        assertNotNull(laborGuardada.get().getProductoControl());
        assertEquals("ICA-P001", laborGuardada.get().getProductoControl().getRegistroICA());
    }

    @Test
    void testCascadaProductorFinca() {
        // Crear productor con fincas
        ProductorModel productor = new ProductorModel("777777777", "Luis", "Gomez", "3177777777", "luis@email.com");
        
        FincaModel finca1 = new FincaModel("CAT005", "Bucaramanga", productor);
        FincaModel finca2 = new FincaModel("CAT006", "Pereira", productor);
        
        productor.addFinca(finca1);
        productor.addFinca(finca2);
        
        productorRepository.save(productor);
        
        entityManager.flush();
        entityManager.clear();
        
        // Verificar que las fincas se guardaron en cascada
        assertEquals(2, fincaRepository.count());
        Optional<FincaModel> finca1Guardada = fincaRepository.findByNumeroCatastro("CAT005");
        assertTrue(finca1Guardada.isPresent());
        assertEquals("Bucaramanga", finca1Guardada.get().getMunicipio());
    }

    @Test
    void testHerenciaProductosControl() {
        // Guardar diferentes tipos de productos de control
        ProductoControlHongoModel productoHongo = new ProductoControlHongoModel(
            "ICA-H001", "Fungicida ABC", 20, new BigDecimal("75000"), 10, "Roya del café"
        );
        
        ProductoControlPlagaModel productoPlaga = new ProductoControlPlagaModel(
            "ICA-P002", "Pesticida DEF", 10, new BigDecimal("45000"), 7
        );
        
        ProductoControlFertilizanteModel productoFertilizante = new ProductoControlFertilizanteModel(
            "ICA-F001", "Fertilizante NPK", 30, new BigDecimal("80000"), LocalDate.of(2025, 10, 1)
        );
        
        productoControlHongoRepository.save(productoHongo);
        productoControlPlagaRepository.save(productoPlaga);
        productoControlFertilizanteRepository.save(productoFertilizante);
        
        entityManager.flush();
        entityManager.clear();
        
        // Verificar que se guardaron correctamente
        assertEquals(1, productoControlHongoRepository.count());
        assertEquals(1, productoControlPlagaRepository.count());
        assertEquals(1, productoControlFertilizanteRepository.count());
        
        // Verificar atributos específicos
        Optional<ProductoControlHongoModel> hongoGuardado = productoControlHongoRepository.findById(productoHongo.getId());
        assertTrue(hongoGuardado.isPresent());
        assertEquals("Roya del café", hongoGuardado.get().getNombreHongo());
    }

    @Test
    void testRelacionCompletaProductorFincaViveroLabor() {
        // Crear estructura completa
        ProductorModel productor = new ProductorModel("888888888", "Sofia", "Rodriguez", "3188888888", "sofia@email.com");
        productorRepository.save(productor);
        
        FincaModel finca = new FincaModel("CAT007", "Manizales", productor);
        productor.addFinca(finca);
        fincaRepository.save(finca);
        
        ViveroModel vivero = new ViveroModel("V005", "Cacao", finca);
        finca.addVivero(vivero);
        viveroRepository.save(vivero);
        
        LaborModel labor = new LaborModel(LocalDate.of(2025, 10, 25), "Poda", vivero);
        vivero.addLabor(labor);
        laborRepository.save(labor);
        
        entityManager.flush();
        entityManager.clear();
        
        // Verificar toda la cadena de relaciones
        Optional<ProductorModel> productorGuardado = productorRepository.findById(productor.getId());
        assertTrue(productorGuardado.isPresent());
        
        FincaModel fincaObtenida = productorGuardado.get().getFincas().get(0);
        assertEquals("CAT007", fincaObtenida.getNumeroCatastro());
        
        ViveroModel viveroObtenido = fincaObtenida.getViveros().get(0);
        assertEquals("V005", viveroObtenido.getCodigo());
        
        LaborModel laborObtenida = viveroObtenido.getLabores().get(0);
        assertEquals("Poda", laborObtenida.getDescripcion());
    }
}
