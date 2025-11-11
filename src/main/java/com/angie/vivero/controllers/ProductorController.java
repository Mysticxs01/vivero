package com.angie.vivero.controllers;

import com.angie.vivero.models.FincaModel;
import com.angie.vivero.models.ProductorModel;
import com.angie.vivero.services.ProductorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Controlador REST para gestionar operaciones de Productores.
 * Historia de Usuario HU-1: Registrar Productor con sus Fincas
 */
@RestController
@RequestMapping("/api/productores")
@CrossOrigin(origins = "*")
public class ProductorController {

    @Autowired
    private ProductorService productorService;

    /**
     * Registra un nuevo productor sin fincas.
     * POST /api/productores
     * 
     * @param productor Datos del productor a registrar
     * @return El productor registrado con código 201 (CREATED)
     */
    @PostMapping
    public ResponseEntity<?> registrarProductor(@Valid @RequestBody ProductorModel productor) {
        try {
            ProductorModel productorGuardado = productorService.registrarProductor(productor);
            return ResponseEntity.status(HttpStatus.CREATED).body(productorGuardado);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Registra un productor con sus fincas en una sola operación.
     * POST /api/productores/con-fincas
     * 
     * @param request Objeto con datos del productor y sus fincas
     * @return El productor registrado con sus fincas con código 201 (CREATED)
     */
    @PostMapping("/con-fincas")
    public ResponseEntity<?> registrarProductorConFincas(@Valid @RequestBody ProductorConFincasRequest request) {
        try {
            ProductorModel productor = new ProductorModel();
            productor.setDocumento(request.getDocumento());
            productor.setNombre(request.getNombre());
            productor.setApellido(request.getApellido());
            productor.setTelefono(request.getTelefono());
            productor.setCorreo(request.getCorreo());
            
            ProductorModel productorGuardado = productorService.registrarProductorConFincas(productor, request.getFincas());
            return ResponseEntity.status(HttpStatus.CREATED).body(productorGuardado);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Obtiene todos los productores registrados.
     * GET /api/productores
     * 
     * @return Lista de todos los productores
     */
    @GetMapping
    public ResponseEntity<List<ProductorModel>> obtenerTodosLosProductores() {
        List<ProductorModel> productores = productorService.obtenerTodosLosProductores();
        return ResponseEntity.ok(productores);
    }

    /**
     * Busca un productor por su ID con todas sus fincas.
     * GET /api/productores/{id}
     * 
     * @param id ID del productor
     * @return El productor con sus fincas o 404 si no existe
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerProductorPorId(@PathVariable Long id) {
        return productorService.obtenerProductorConFincas(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca un productor por su documento de identidad.
     * GET /api/productores/documento/{documento}
     * 
     * @param documento Documento del productor
     * @return El productor o 404 si no existe
     */
    @GetMapping("/documento/{documento}")
    public ResponseEntity<?> buscarPorDocumento(@PathVariable String documento) {
        return productorService.buscarPorDocumento(documento)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Actualiza los datos de un productor existente.
     * PUT /api/productores/{id}
     * 
     * @param id ID del productor
     * @param productor Datos actualizados
     * @return El productor actualizado o 404 si no existe
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarProductor(@PathVariable Long id, @Valid @RequestBody ProductorModel productor) {
        try {
            ProductorModel productorActualizado = productorService.actualizarProductor(id, productor);
            return ResponseEntity.ok(productorActualizado);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Agrega una finca a un productor existente.
     * POST /api/productores/{id}/fincas
     * 
     * @param id ID del productor
     * @param finca Finca a agregar
     * @return La finca registrada o 404 si el productor no existe
     */
    @PostMapping("/{id}/fincas")
    public ResponseEntity<?> agregarFinca(@PathVariable Long id, @Valid @RequestBody FincaModel finca) {
        try {
            FincaModel fincaGuardada = productorService.agregarFincaAProductor(id, finca);
            return ResponseEntity.status(HttpStatus.CREATED).body(fincaGuardada);
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(error);
        }
    }

    /**
     * Elimina un productor del sistema.
     * DELETE /api/productores/{id}
     * 
     * @param id ID del productor a eliminar
     * @return 204 (NO CONTENT) si se eliminó correctamente, 404 si no existe
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProductor(@PathVariable Long id) {
        try {
            productorService.eliminarProductor(id);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    /**
     * Clase interna para recibir un productor con sus fincas en una sola petición.
     */
    public static class ProductorConFincasRequest {
        private String documento;
        private String nombre;
        private String apellido;
        private String telefono;
        private String correo;
        private List<FincaModel> fincas;

        // Getters y Setters
        public String getDocumento() {
            return documento;
        }

        public void setDocumento(String documento) {
            this.documento = documento;
        }

        public String getNombre() {
            return nombre;
        }

        public void setNombre(String nombre) {
            this.nombre = nombre;
        }

        public String getApellido() {
            return apellido;
        }

        public void setApellido(String apellido) {
            this.apellido = apellido;
        }

        public String getTelefono() {
            return telefono;
        }

        public void setTelefono(String telefono) {
            this.telefono = telefono;
        }

        public String getCorreo() {
            return correo;
        }

        public void setCorreo(String correo) {
            this.correo = correo;
        }

        public List<FincaModel> getFincas() {
            return fincas;
        }

        public void setFincas(List<FincaModel> fincas) {
            this.fincas = fincas;
        }
    }
}
