package com.angie.vivero.services;

import com.angie.vivero.models.LaborModel;
import com.angie.vivero.models.ProductoControlModel;
import com.angie.vivero.models.ViveroModel;
import com.angie.vivero.repositories.LaborRepository;
import com.angie.vivero.repositories.ProductoControlRepository;
import com.angie.vivero.repositories.ViveroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Servicio para la gestión de labores en viveros.
 * 
 * HU-2: Como productor, quiero registrar una labor realizada en un vivero 
 * con el producto de control utilizado, para llevar trazabilidad de las 
 * actividades agrícolas.
 */
@Service
@Transactional
public class LaborService {

    @Autowired
    private LaborRepository laborRepository;

    @Autowired
    private ViveroRepository viveroRepository;

    @Autowired
    private ProductoControlRepository productoControlRepository;

    /**
     * Registra una nueva labor en un vivero.
     * 
     * Criterio de Aceptación 1: El sistema debe permitir registrar una labor
     * con fecha, descripción y vivero asociado.
     * 
     * @param labor Labor a registrar
     * @param viveroId ID del vivero donde se realiza la labor
     * @return La labor registrada
     * @throws IllegalArgumentException si el vivero no existe
     */
    public LaborModel registrarLabor(LaborModel labor, Long viveroId) {
        ViveroModel vivero = viveroRepository.findById(viveroId)
                .orElseThrow(() -> new IllegalArgumentException("Vivero no encontrado con ID: " + viveroId));
        
        labor.setVivero(vivero);
        return laborRepository.save(labor);
    }

    /**
     * Registra una nueva labor con producto de control.
     * 
     * Criterio de Aceptación 2: El sistema debe permitir registrar una labor
     * con un producto de control opcional.
     * 
     * @param labor Labor a registrar
     * @param viveroId ID del vivero
     * @param productoControlId ID del producto de control (puede ser null)
     * @return La labor registrada
     * @throws IllegalArgumentException si el vivero o el producto no existen
     */
    public LaborModel registrarLaborConProducto(LaborModel labor, Long viveroId, Long productoControlId) {
        ViveroModel vivero = viveroRepository.findById(viveroId)
                .orElseThrow(() -> new IllegalArgumentException("Vivero no encontrado con ID: " + viveroId));
        
        labor.setVivero(vivero);
        
        if (productoControlId != null) {
            ProductoControlModel producto = productoControlRepository.findById(productoControlId)
                    .orElseThrow(() -> new IllegalArgumentException("Producto de control no encontrado con ID: " + productoControlId));
            labor.setProductoControl(producto);
        }
        
        return laborRepository.save(labor);
    }

    /**
     * Obtiene todas las labores de un vivero.
     * 
     * Criterio de Aceptación 3: El sistema debe permitir consultar todas las
     * labores realizadas en un vivero específico.
     * 
     * @param viveroId ID del vivero
     * @return Lista de labores del vivero
     */
    public List<LaborModel> obtenerLaboresPorVivero(Long viveroId) {
        return laborRepository.findByViveroId(viveroId);
    }

    /**
     * Obtiene todas las labores realizadas en un rango de fechas.
     * 
     * Criterio de Aceptación 4: El sistema debe permitir consultar labores
     * por rango de fechas.
     * 
     * @param fechaInicio Fecha de inicio del rango
     * @param fechaFin Fecha de fin del rango
     * @return Lista de labores en el rango
     */
    public List<LaborModel> obtenerLaboresPorRangoFechas(LocalDate fechaInicio, LocalDate fechaFin) {
        return laborRepository.findByFechaBetween(fechaInicio, fechaFin);
    }

    /**
     * Obtiene todas las labores que utilizaron un producto de control específico.
     * 
     * Criterio de Aceptación 5: El sistema debe permitir consultar las labores
     * donde se utilizó un producto de control específico.
     * 
     * @param productoControlId ID del producto de control
     * @return Lista de labores que usaron el producto
     */
    public List<LaborModel> obtenerLaboresPorProductoControl(Long productoControlId) {
        return laborRepository.findByProductoControlId(productoControlId);
    }

    /**
     * Obtiene una labor por su ID.
     * 
     * @param id ID de la labor
     * @return Optional con la labor si existe
     */
    public Optional<LaborModel> obtenerLaborPorId(Long id) {
        return laborRepository.findById(id);
    }

    /**
     * Actualiza una labor existente.
     * 
     * @param id ID de la labor
     * @param laborActualizada Datos actualizados
     * @return La labor actualizada
     * @throws IllegalArgumentException si la labor no existe
     */
    public LaborModel actualizarLabor(Long id, LaborModel laborActualizada) {
        LaborModel labor = laborRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Labor no encontrada con ID: " + id));
        
        labor.setFecha(laborActualizada.getFecha());
        labor.setDescripcion(laborActualizada.getDescripcion());
        
        return laborRepository.save(labor);
    }

    /**
     * Elimina una labor.
     * 
     * @param id ID de la labor a eliminar
     * @throws IllegalArgumentException si la labor no existe
     */
    public void eliminarLabor(Long id) {
        if (!laborRepository.existsById(id)) {
            throw new IllegalArgumentException("Labor no encontrada con ID: " + id);
        }
        laborRepository.deleteById(id);
    }

    /**
     * Obtiene todas las labores registradas.
     * 
     * @return Lista de todas las labores
     */
    public List<LaborModel> obtenerTodasLasLabores() {
        return laborRepository.findAll();
    }
}
