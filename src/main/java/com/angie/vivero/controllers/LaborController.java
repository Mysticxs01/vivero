package com.angie.vivero.controllers;

import com.angie.vivero.models.LaborModel;
import com.angie.vivero.services.LaborService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador REST para la gestión de labores en viveros.
 * 
 * HU-2: Como productor, quiero registrar una labor realizada en un vivero 
 * con el producto de control utilizado, para llevar trazabilidad de las 
 * actividades agrícolas.
 */
@RestController
@RequestMapping("/api/labores")
public class LaborController {

    @Autowired
    private LaborService laborService;

    /**
     * Registra una nueva labor en un vivero.
     * 
     * POST /api/labores?viveroId={viveroId}
     * 
     * @param labor Labor a registrar
     * @param viveroId ID del vivero
     * @return La labor registrada
     */
    @PostMapping
    public ResponseEntity<LaborModel> registrarLabor(
            @Valid @RequestBody LaborModel labor,
            @RequestParam Long viveroId) {
        try {
            LaborModel laborGuardada = laborService.registrarLabor(labor, viveroId);
            return new ResponseEntity<>(laborGuardada, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Registra una nueva labor con producto de control.
     * 
     * POST /api/labores/con-producto?viveroId={viveroId}&productoControlId={productoControlId}
     * 
     * @param labor Labor a registrar
     * @param viveroId ID del vivero
     * @param productoControlId ID del producto de control (opcional)
     * @return La labor registrada
     */
    @PostMapping("/con-producto")
    public ResponseEntity<LaborModel> registrarLaborConProducto(
            @Valid @RequestBody LaborModel labor,
            @RequestParam Long viveroId,
            @RequestParam(required = false) Long productoControlId) {
        try {
            LaborModel laborGuardada = laborService.registrarLaborConProducto(labor, viveroId, productoControlId);
            return new ResponseEntity<>(laborGuardada, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Obtiene todas las labores de un vivero.
     * 
     * GET /api/labores/vivero/{viveroId}
     * 
     * @param viveroId ID del vivero
     * @return Lista de labores del vivero
     */
    @GetMapping("/vivero/{viveroId}")
    public ResponseEntity<List<LaborModel>> obtenerLaboresPorVivero(@PathVariable Long viveroId) {
        List<LaborModel> labores = laborService.obtenerLaboresPorVivero(viveroId);
        return new ResponseEntity<>(labores, HttpStatus.OK);
    }

    /**
     * Obtiene labores por rango de fechas.
     * 
     * GET /api/labores/rango?fechaInicio={fechaInicio}&fechaFin={fechaFin}
     * 
     * @param fechaInicio Fecha de inicio
     * @param fechaFin Fecha de fin
     * @return Lista de labores en el rango
     */
    @GetMapping("/rango")
    public ResponseEntity<List<LaborModel>> obtenerLaboresPorRango(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaInicio,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fechaFin) {
        List<LaborModel> labores = laborService.obtenerLaboresPorRangoFechas(fechaInicio, fechaFin);
        return new ResponseEntity<>(labores, HttpStatus.OK);
    }

    /**
     * Obtiene labores que utilizaron un producto de control.
     * 
     * GET /api/labores/producto/{productoControlId}
     * 
     * @param productoControlId ID del producto de control
     * @return Lista de labores
     */
    @GetMapping("/producto/{productoControlId}")
    public ResponseEntity<List<LaborModel>> obtenerLaboresPorProducto(@PathVariable Long productoControlId) {
        List<LaborModel> labores = laborService.obtenerLaboresPorProductoControl(productoControlId);
        return new ResponseEntity<>(labores, HttpStatus.OK);
    }

    /**
     * Obtiene una labor por su ID.
     * 
     * GET /api/labores/{id}
     * 
     * @param id ID de la labor
     * @return La labor encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<LaborModel> obtenerLaborPorId(@PathVariable Long id) {
        return laborService.obtenerLaborPorId(id)
                .map(labor -> new ResponseEntity<>(labor, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * Obtiene todas las labores.
     * 
     * GET /api/labores
     * 
     * @return Lista de todas las labores
     */
    @GetMapping
    public ResponseEntity<List<LaborModel>> obtenerTodasLasLabores() {
        List<LaborModel> labores = laborService.obtenerTodasLasLabores();
        return new ResponseEntity<>(labores, HttpStatus.OK);
    }

    /**
     * Actualiza una labor existente.
     * 
     * PUT /api/labores/{id}
     * 
     * @param id ID de la labor
     * @param labor Datos actualizados
     * @return La labor actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<LaborModel> actualizarLabor(
            @PathVariable Long id,
            @Valid @RequestBody LaborModel labor) {
        try {
            LaborModel laborActualizada = laborService.actualizarLabor(id, labor);
            return new ResponseEntity<>(laborActualizada, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Elimina una labor.
     * 
     * DELETE /api/labores/{id}
     * 
     * @param id ID de la labor
     * @return Respuesta sin contenido
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarLabor(@PathVariable Long id) {
        try {
            laborService.eliminarLabor(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
