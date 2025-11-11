package com.angie.vivero.services;

import com.angie.vivero.models.FincaModel;
import com.angie.vivero.models.ProductorModel;
import com.angie.vivero.repositories.FincaRepository;
import com.angie.vivero.repositories.ProductorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Servicio para gestionar operaciones relacionadas con Productores.
 * Historia de Usuario HU-1: Registrar Productor con sus Fincas
 */
@Service
@Transactional
public class ProductorService {

    @Autowired
    private ProductorRepository productorRepository;

    @Autowired
    private FincaRepository fincaRepository;

    /**
     * Registra un nuevo productor en el sistema.
     * 
     * Criterio de Aceptación 1: El sistema debe permitir registrar un productor
     * con todos sus datos obligatorios (documento, nombre, apellido, teléfono, correo).
     * 
     * @param productor El productor a registrar
     * @return El productor registrado con su ID generado
     * @throws IllegalArgumentException si el documento ya existe
     */
    public ProductorModel registrarProductor(ProductorModel productor) {
        // Verificar que no exista un productor con el mismo documento
        Optional<ProductorModel> existente = productorRepository.findByDocumento(productor.getDocumento());
        if (existente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un productor con el documento: " + productor.getDocumento());
        }
        
        return productorRepository.save(productor);
    }

    /**
     * Registra un productor junto con sus fincas en una sola transacción.
     * 
     * Criterio de Aceptación 2: El sistema debe permitir registrar un productor
     * con una o más fincas asociadas en una sola operación.
     * 
     * @param productor El productor a registrar
     * @param fincas Lista de fincas del productor
     * @return El productor registrado con sus fincas
     */
    public ProductorModel registrarProductorConFincas(ProductorModel productor, List<FincaModel> fincas) {
        // Registrar el productor
        ProductorModel productorGuardado = registrarProductor(productor);
        
        // Asociar y guardar las fincas
        if (fincas != null && !fincas.isEmpty()) {
            for (FincaModel finca : fincas) {
                productorGuardado.addFinca(finca);
            }
            productorRepository.save(productorGuardado);
        }
        
        return productorGuardado;
    }

    /**
     * Busca un productor por su documento de identidad.
     * 
     * Criterio de Aceptación 3: El sistema debe permitir consultar un productor
     * por su documento de identidad.
     * 
     * @param documento Documento del productor
     * @return Optional con el productor si existe
     */
    public Optional<ProductorModel> buscarPorDocumento(String documento) {
        return productorRepository.findByDocumento(documento);
    }

    /**
     * Obtiene todos los productores registrados.
     * 
     * @return Lista de todos los productores
     */
    public List<ProductorModel> obtenerTodosLosProductores() {
        return productorRepository.findAll();
    }

    /**
     * Obtiene un productor por su ID con todas sus fincas.
     * 
     * Criterio de Aceptación 4: El sistema debe permitir consultar un productor
     * con todas sus fincas asociadas.
     * 
     * @param id ID del productor
     * @return Optional con el productor si existe
     */
    public Optional<ProductorModel> obtenerProductorConFincas(Long id) {
        return productorRepository.findById(id);
    }

    /**
     * Actualiza los datos de un productor existente.
     * 
     * @param id ID del productor
     * @param productorActualizado Datos actualizados del productor
     * @return El productor actualizado
     * @throws IllegalArgumentException si el productor no existe
     */
    public ProductorModel actualizarProductor(Long id, ProductorModel productorActualizado) {
        ProductorModel productor = productorRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Productor no encontrado con ID: " + id));
        
        productor.setNombre(productorActualizado.getNombre());
        productor.setApellido(productorActualizado.getApellido());
        productor.setTelefono(productorActualizado.getTelefono());
        productor.setCorreo(productorActualizado.getCorreo());
        
        return productorRepository.save(productor);
    }

    /**
     * Agrega una finca a un productor existente.
     * 
     * Criterio de Aceptación 5: El sistema debe permitir agregar nuevas fincas
     * a un productor ya registrado.
     * 
     * @param productorId ID del productor
     * @param finca Finca a agregar
     * @return La finca registrada
     * @throws IllegalArgumentException si el productor no existe
     */
    public FincaModel agregarFincaAProductor(Long productorId, FincaModel finca) {
        ProductorModel productor = productorRepository.findById(productorId)
                .orElseThrow(() -> new IllegalArgumentException("Productor no encontrado con ID: " + productorId));
        
        // Verificar que no exista una finca con el mismo número de catastro
        Optional<FincaModel> fincaExistente = fincaRepository.findByNumeroCatastro(finca.getNumeroCatastro());
        if (fincaExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe una finca con el número de catastro: " + finca.getNumeroCatastro());
        }
        
        // Establecer la relación bidireccional usando el método helper
        productor.addFinca(finca);
        
        // Guardar el productor (cascada guardará la finca)
        productorRepository.save(productor);
        
        // Retornar la finca que ahora tiene ID asignado
        return fincaRepository.findByNumeroCatastro(finca.getNumeroCatastro()).get();
    }

    /**
     * Elimina un productor del sistema.
     * 
     * @param id ID del productor a eliminar
     * @throws IllegalArgumentException si el productor no existe
     */
    public void eliminarProductor(Long id) {
        if (!productorRepository.existsById(id)) {
            throw new IllegalArgumentException("Productor no encontrado con ID: " + id);
        }
        productorRepository.deleteById(id);
    }
}
