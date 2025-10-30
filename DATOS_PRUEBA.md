# Datos de Prueba del Sistema de Gestión de Vivero

Este documento describe los datos de prueba que se han cargado en el sistema.

## 📊 Resumen de Datos

- **3 Productores** registrados
- **4 Fincas** distribuidas en diferentes municipios
- **5 Viveros** con diferentes tipos de cultivo
- **6 Productos de Control** (2 hongos, 2 plagas, 2 fertilizantes)
- **8 Labores** registradas con sus respectivos productos

---

## 👥 Productores

| ID | Documento  | Nombre | Apellido   | Teléfono   | Correo                     |
|----|------------|--------|------------|------------|----------------------------|
| 1  | 1234567890 | Juan   | Pérez      | 3001234567 | juan.perez@email.com       |
| 2  | 0987654321 | María  | González   | 3109876543 | maria.gonzalez@email.com   |
| 3  | 1122334455 | Carlos | Rodríguez  | 3201122334 | carlos.rodriguez@email.com |

---

## 🏞️ Fincas

| ID | Número Catastro | Municipio | Productor          |
|----|----------------|-----------|-------------------|
| 1  | CAT-001-2024   | Medellín  | Juan Pérez        |
| 2  | CAT-002-2024   | Envigado  | Juan Pérez        |
| 3  | CAT-003-2024   | Rionegro  | María González    |
| 4  | CAT-004-2024   | La Ceja   | Carlos Rodríguez  |

---

## 🌱 Viveros

| Código  | Tipo de Cultivo | Ubicación (Finca - Municipio) |
|---------|-----------------|-------------------------------|
| VIV-001 | Café            | CAT-001-2024 - Medellín       |
| VIV-002 | Plátano         | CAT-001-2024 - Medellín       |
| VIV-003 | Aguacate        | CAT-002-2024 - Envigado       |
| VIV-004 | Cítricos        | CAT-003-2024 - Rionegro       |
| VIV-005 | Tomate          | CAT-004-2024 - La Ceja        |

---

## 🧪 Productos de Control

### Control de Hongos

| Registro ICA  | Nombre Producto     | Hongo Controlado   | Frecuencia (días) | Periodo Carencia (días) | Valor     |
|---------------|---------------------|-------------------|-------------------|-------------------------|-----------|
| ICA-HONGO-001 | Fungicida Premium   | Roya del Café     | 15                | 7                       | $45,000   |
| ICA-HONGO-002 | Antifúngico Natural | Mildiu Polvoriento| 20                | 10                      | $38,000   |

### Control de Plagas

| Registro ICA  | Nombre Producto          | Frecuencia (días) | Periodo Carencia (días) | Valor     |
|---------------|--------------------------|-------------------|-------------------------|-----------|
| ICA-PLAGA-001 | Insecticida Orgánico     | 10                | 5                       | $52,000   |
| ICA-PLAGA-002 | Control de Plagas Total  | 12                | 7                       | $48,000   |

### Fertilizantes

| Registro ICA | Nombre Producto              | Frecuencia (días) | Última Aplicación | Valor     |
|--------------|------------------------------|-------------------|-------------------|-----------|
| ICA-FERT-001 | Fertilizante NPK 10-10-10    | 30                | 2024-10-01        | $75,000   |
| ICA-FERT-002 | Abono Orgánico Completo      | 25                | 2024-10-15        | $65,000   |

---

## 📝 Labores Registradas

| Fecha      | Descripción                          | Vivero  | Producto Utilizado        |
|------------|--------------------------------------|---------|---------------------------|
| 2024-10-28 | Aplicación de fungicida preventivo   | VIV-001 | Fungicida Premium         |
| 2024-10-27 | Control de plagas en cultivo         | VIV-001 | Insecticida Orgánico      |
| 2024-10-26 | Fertilización de plantas jóvenes     | VIV-002 | Fertilizante NPK 10-10-10 |
| 2024-10-25 | Tratamiento contra roya              | VIV-003 | Antifúngico Natural       |
| 2024-10-24 | Control de insectos                  | VIV-004 | Control de Plagas Total   |
| 2024-10-23 | Aplicación de abono orgánico         | VIV-005 | Abono Orgánico Completo   |
| 2024-10-22 | Poda sanitaria                       | VIV-001 | (Sin producto)            |
| 2024-10-21 | Riego profundo                       | VIV-002 | (Sin producto)            |

---

## 🔄 Cómo Recargar los Datos

### Opción 1: Usando la Aplicación Spring Boot

Los datos se cargan automáticamente al iniciar la aplicación con el perfil `dev` activo:

```bash
.\mvnw.cmd spring-boot:run
```

El inicializador verifica si ya existen datos y solo los carga si la base de datos está vacía.

### Opción 2: Usando el Script SQL

Si prefieres usar SQL directamente:

```bash
mysql -u root -proot vivero < src/main/resources/data.sql
```

---

## 🗑️ Limpiar los Datos de Prueba

Para eliminar todos los datos de prueba:

```sql
-- Eliminar en orden inverso por las relaciones de clave foránea
DELETE FROM labores;
DELETE FROM productos_control_hongo;
DELETE FROM productos_control_plaga;
DELETE FROM productos_control_fertilizante;
DELETE FROM productos_control;
DELETE FROM viveros;
DELETE FROM fincas;
DELETE FROM productores;
```

O simplemente:

```bash
.\mvnw.cmd spring-boot:run
```

El sistema detectará que ya hay datos y no los duplicará.

---

## 📋 Consultas Útiles

### Ver todos los viveros con sus productores

```sql
SELECT 
    v.codigo AS 'Código Vivero',
    v.tipo_cultivo AS 'Cultivo',
    f.municipio AS 'Municipio',
    CONCAT(p.nombre, ' ', p.apellido) AS 'Productor'
FROM viveros v
JOIN fincas f ON v.finca_id = f.id
JOIN productores p ON f.productor_id = p.id;
```

### Ver todas las labores de un vivero específico

```sql
SELECT 
    l.fecha,
    l.descripcion,
    pc.nombre_producto
FROM labores l
JOIN viveros v ON l.vivero_id = v.id
LEFT JOIN productos_control pc ON l.producto_control_id = pc.id
WHERE v.codigo = 'VIV-001'
ORDER BY l.fecha DESC;
```

### Ver productos de control por tipo

```sql
-- Productos control de hongos
SELECT 
    pc.nombre_producto,
    pch.nombre_hongo,
    pch.periodo_carencia,
    pc.valor
FROM productos_control pc
JOIN productos_control_hongo pch ON pc.id = pch.id;

-- Productos control de plagas
SELECT 
    pc.nombre_producto,
    pcp.periodo_carencia,
    pc.valor
FROM productos_control pc
JOIN productos_control_plaga pcp ON pc.id = pcp.id;

-- Fertilizantes
SELECT 
    pc.nombre_producto,
    pcf.fecha_ultima_aplicacion,
    pc.valor
FROM productos_control pc
JOIN productos_control_fertilizante pcf ON pc.id = pcf.id;
```

---

## ✅ Verificación

Para verificar que los datos se cargaron correctamente:

```bash
mysql -u root -proot -e "USE vivero; SELECT 
    (SELECT COUNT(*) FROM productores) as Productores,
    (SELECT COUNT(*) FROM fincas) as Fincas,
    (SELECT COUNT(*) FROM viveros) as Viveros,
    (SELECT COUNT(*) FROM productos_control) as Productos,
    (SELECT COUNT(*) FROM labores) as Labores;"
```

Resultado esperado:
- Productores: 3
- Fincas: 4
- Viveros: 5
- Productos: 6
- Labores: 8

---

**Fecha de creación:** 30 de octubre de 2025  
**Sistema:** Vivero Management System v0.0.1
