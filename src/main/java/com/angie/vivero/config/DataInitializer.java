package com.angie.vivero.config;

import com.angie.vivero.models.*;
import com.angie.vivero.repositories.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Inicializador de datos de prueba para el sistema de gestión de vivero.
 * Solo se ejecuta cuando el perfil "dev" está activo.
 */
@Configuration
@Profile("dev")
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(
            ProductorRepository productorRepository,
            FincaRepository fincaRepository,
            ViveroRepository viveroRepository,
            ProductoControlHongoRepository hongoRepository,
            ProductoControlPlagaRepository plagaRepository,
            ProductoControlFertilizanteRepository fertilizanteRepository,
            LaborRepository laborRepository) {

        return args -> {
            // Verificar si ya hay datos
            if (productorRepository.count() > 0) {
                System.out.println("=== Base de datos ya contiene datos. Omitiendo inicialización ===");
                return;
            }

            System.out.println("=== Iniciando carga de datos de prueba ===");

            // Crear Productores
            ProductorModel productor1 = new ProductorModel();
            productor1.setDocumento("1234567890");
            productor1.setNombre("Juan");
            productor1.setApellido("Pérez");
            productor1.setTelefono("3001234567");
            productor1.setCorreo("juan.perez@email.com");
            productor1 = productorRepository.save(productor1);

            ProductorModel productor2 = new ProductorModel();
            productor2.setDocumento("0987654321");
            productor2.setNombre("María");
            productor2.setApellido("González");
            productor2.setTelefono("3109876543");
            productor2.setCorreo("maria.gonzalez@email.com");
            productor2 = productorRepository.save(productor2);

            ProductorModel productor3 = new ProductorModel();
            productor3.setDocumento("1122334455");
            productor3.setNombre("Carlos");
            productor3.setApellido("Rodríguez");
            productor3.setTelefono("3201122334");
            productor3.setCorreo("carlos.rodriguez@email.com");
            productor3 = productorRepository.save(productor3);

            System.out.println("✓ Creados 3 productores");

            // Crear Fincas
            FincaModel finca1 = new FincaModel();
            finca1.setNumeroCatastro("CAT-001-2024");
            finca1.setMunicipio("Medellín");
            finca1.setProductor(productor1);
            finca1 = fincaRepository.save(finca1);

            FincaModel finca2 = new FincaModel();
            finca2.setNumeroCatastro("CAT-002-2024");
            finca2.setMunicipio("Envigado");
            finca2.setProductor(productor1);
            finca2 = fincaRepository.save(finca2);

            FincaModel finca3 = new FincaModel();
            finca3.setNumeroCatastro("CAT-003-2024");
            finca3.setMunicipio("Rionegro");
            finca3.setProductor(productor2);
            finca3 = fincaRepository.save(finca3);

            FincaModel finca4 = new FincaModel();
            finca4.setNumeroCatastro("CAT-004-2024");
            finca4.setMunicipio("La Ceja");
            finca4.setProductor(productor3);
            finca4 = fincaRepository.save(finca4);

            System.out.println("✓ Creadas 4 fincas");

            // Crear Viveros
            ViveroModel vivero1 = new ViveroModel();
            vivero1.setCodigo("VIV-001");
            vivero1.setTipoCultivo("Café");
            vivero1.setFinca(finca1);
            vivero1 = viveroRepository.save(vivero1);

            ViveroModel vivero2 = new ViveroModel();
            vivero2.setCodigo("VIV-002");
            vivero2.setTipoCultivo("Plátano");
            vivero2.setFinca(finca1);
            vivero2 = viveroRepository.save(vivero2);

            ViveroModel vivero3 = new ViveroModel();
            vivero3.setCodigo("VIV-003");
            vivero3.setTipoCultivo("Aguacate");
            vivero3.setFinca(finca2);
            vivero3 = viveroRepository.save(vivero3);

            ViveroModel vivero4 = new ViveroModel();
            vivero4.setCodigo("VIV-004");
            vivero4.setTipoCultivo("Cítricos");
            vivero4.setFinca(finca3);
            vivero4 = viveroRepository.save(vivero4);

            ViveroModel vivero5 = new ViveroModel();
            vivero5.setCodigo("VIV-005");
            vivero5.setTipoCultivo("Tomate");
            vivero5.setFinca(finca4);
            vivero5 = viveroRepository.save(vivero5);

            System.out.println("✓ Creados 5 viveros");

            // Crear Productos de Control - Hongos
            ProductoControlHongoModel hongo1 = new ProductoControlHongoModel();
            hongo1.setRegistroICA("ICA-HONGO-001");
            hongo1.setNombreProducto("Fungicida Premium");
            hongo1.setFrecuenciaAplicacion(15);
            hongo1.setValor(new BigDecimal("45000.00"));
            hongo1.setNombreHongo("Roya del Café");
            hongo1.setPeriodoCarencia(7);
            hongo1 = hongoRepository.save(hongo1);

            ProductoControlHongoModel hongo2 = new ProductoControlHongoModel();
            hongo2.setRegistroICA("ICA-HONGO-002");
            hongo2.setNombreProducto("Antifúngico Natural");
            hongo2.setFrecuenciaAplicacion(20);
            hongo2.setValor(new BigDecimal("38000.00"));
            hongo2.setNombreHongo("Mildiu Polvoriento");
            hongo2.setPeriodoCarencia(10);
            hongo2 = hongoRepository.save(hongo2);

            System.out.println("✓ Creados 2 productos control de hongos");

            // Crear Productos de Control - Plagas
            ProductoControlPlagaModel plaga1 = new ProductoControlPlagaModel();
            plaga1.setRegistroICA("ICA-PLAGA-001");
            plaga1.setNombreProducto("Insecticida Orgánico");
            plaga1.setFrecuenciaAplicacion(10);
            plaga1.setValor(new BigDecimal("52000.00"));
            plaga1.setPeriodoCarencia(5);
            plaga1 = plagaRepository.save(plaga1);

            ProductoControlPlagaModel plaga2 = new ProductoControlPlagaModel();
            plaga2.setRegistroICA("ICA-PLAGA-002");
            plaga2.setNombreProducto("Control de Plagas Total");
            plaga2.setFrecuenciaAplicacion(12);
            plaga2.setValor(new BigDecimal("48000.00"));
            plaga2.setPeriodoCarencia(7);
            plaga2 = plagaRepository.save(plaga2);

            System.out.println("✓ Creados 2 productos control de plagas");

            // Crear Productos de Control - Fertilizantes
            ProductoControlFertilizanteModel fert1 = new ProductoControlFertilizanteModel();
            fert1.setRegistroICA("ICA-FERT-001");
            fert1.setNombreProducto("Fertilizante NPK 10-10-10");
            fert1.setFrecuenciaAplicacion(30);
            fert1.setValor(new BigDecimal("75000.00"));
            fert1.setFechaUltimaAplicacion(LocalDate.of(2024, 10, 1));
            fert1 = fertilizanteRepository.save(fert1);

            ProductoControlFertilizanteModel fert2 = new ProductoControlFertilizanteModel();
            fert2.setRegistroICA("ICA-FERT-002");
            fert2.setNombreProducto("Abono Orgánico Completo");
            fert2.setFrecuenciaAplicacion(25);
            fert2.setValor(new BigDecimal("65000.00"));
            fert2.setFechaUltimaAplicacion(LocalDate.of(2024, 10, 15));
            fert2 = fertilizanteRepository.save(fert2);

            System.out.println("✓ Creados 2 productos fertilizantes");

            // Crear Labores
            LaborModel labor1 = new LaborModel();
            labor1.setFecha(LocalDate.of(2024, 10, 28));
            labor1.setDescripcion("Aplicación de fungicida preventivo");
            labor1.setVivero(vivero1);
            labor1.setProductoControl(hongo1);
            laborRepository.save(labor1);

            LaborModel labor2 = new LaborModel();
            labor2.setFecha(LocalDate.of(2024, 10, 27));
            labor2.setDescripcion("Control de plagas en cultivo");
            labor2.setVivero(vivero1);
            labor2.setProductoControl(plaga1);
            laborRepository.save(labor2);

            LaborModel labor3 = new LaborModel();
            labor3.setFecha(LocalDate.of(2024, 10, 26));
            labor3.setDescripcion("Fertilización de plantas jóvenes");
            labor3.setVivero(vivero2);
            labor3.setProductoControl(fert1);
            laborRepository.save(labor3);

            LaborModel labor4 = new LaborModel();
            labor4.setFecha(LocalDate.of(2024, 10, 25));
            labor4.setDescripcion("Tratamiento contra roya");
            labor4.setVivero(vivero3);
            labor4.setProductoControl(hongo2);
            laborRepository.save(labor4);

            LaborModel labor5 = new LaborModel();
            labor5.setFecha(LocalDate.of(2024, 10, 24));
            labor5.setDescripcion("Control de insectos");
            labor5.setVivero(vivero4);
            labor5.setProductoControl(plaga2);
            laborRepository.save(labor5);

            LaborModel labor6 = new LaborModel();
            labor6.setFecha(LocalDate.of(2024, 10, 23));
            labor6.setDescripcion("Aplicación de abono orgánico");
            labor6.setVivero(vivero5);
            labor6.setProductoControl(fert2);
            laborRepository.save(labor6);

            LaborModel labor7 = new LaborModel();
            labor7.setFecha(LocalDate.of(2024, 10, 22));
            labor7.setDescripcion("Poda sanitaria");
            labor7.setVivero(vivero1);
            laborRepository.save(labor7);

            LaborModel labor8 = new LaborModel();
            labor8.setFecha(LocalDate.of(2024, 10, 21));
            labor8.setDescripcion("Riego profundo");
            labor8.setVivero(vivero2);
            laborRepository.save(labor8);

            System.out.println("✓ Creadas 8 labores");

            System.out.println("\n=== ✅ Datos de prueba cargados exitosamente ===");
            System.out.println("📊 Resumen:");
            System.out.println("   - 3 productores");
            System.out.println("   - 4 fincas");
            System.out.println("   - 5 viveros");
            System.out.println("   - 6 productos de control (2 hongos, 2 plagas, 2 fertilizantes)");
            System.out.println("   - 8 labores");
        };
    }
}
