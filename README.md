# Sistema de Gestión de Viveros

## 📋 Descripción del Proyecto

Sistema desarrollado en Spring Boot para la gestión de viveros, permitiendo llevar un registro completo de productores, fincas, viveros, labores agrícolas y productos de control utilizados.

Este proyecto cumple con todos los requisitos solicitados en la actividad académica, incluyendo el modelo completo de entidades, pruebas unitarias exhaustivas y validación de relaciones.

## 🎯 Características Implementadas

✅ Modelo de entidades completo con relaciones JPA  
✅ Validación de campos obligatorios con Jakarta Validation  
✅ Repositorios JPA para todas las entidades  
✅ Herencia JOINED para productos de control  
✅ Cascadas de persistencia y borrado  
✅ Métodos helper para gestión de relaciones bidireccionales  
✅ **50 pruebas unitarias** (supera el mínimo de 21 requeridas)  
✅ Pruebas de relaciones entre entidades  
✅ Pruebas de herencia  
✅ Todos los campos son obligatorios según requerimientos  
✅ Documentación completa

## 📚 Documentación

- **[README.md](README.md)** - Este archivo (visión general)
- **[INSTALACION.md](INSTALACION.md)** - Guía completa de instalación y ejecución
- **[DATOS_PRUEBA.md](DATOS_PRUEBA.md)** - Documentación de los datos de prueba
- **[EJECUCION.md](EJECUCION.md)** - Guía de ejecución y solución de problemas
- **[contexto/DIAGRAMA_CLASES.md](contexto/DIAGRAMA_CLASES.md)** - Diagrama de clases detallado
- **[contexto/RESUMEN_IMPLEMENTACION.md](contexto/RESUMEN_IMPLEMENTACION.md)** - Resumen de implementación

## Modelo de Datos

El sistema implementa las siguientes entidades:

### 1. ProductorModel
Representa a los productores agrícolas.
- **Atributos obligatorios:**
  - `documento`: Identificación única del productor
  - `nombre`: Nombre del productor
  - `apellido`: Apellido del productor
  - `telefono`: Teléfono de contacto
  - `correo`: Correo electrónico
- **Relaciones:**
  - Tiene muchas `Fincas` (OneToMany)

### 2. FincaModel
Representa las fincas de los productores.
- **Atributos obligatorios:**
  - `numeroCatastro`: Número de catastro único
  - `municipio`: Ubicación de la finca
  - `productor`: Productor propietario (ManyToOne)
- **Relaciones:**
  - Pertenece a un `Productor` (ManyToOne)
  - Tiene muchos `Viveros` (OneToMany)

### 3. ViveroModel
Representa los viveros dentro de las fincas.
- **Atributos obligatorios:**
  - `codigo`: Código asignado por el productor
  - `tipoCultivo`: Tipo de cultivo del vivero
  - `finca`: Finca a la que pertenece (ManyToOne)
- **Relaciones:**
  - Pertenece a una `Finca` (ManyToOne)
  - Tiene muchas `Labores` (OneToMany)

### 4. LaborModel
Representa las actividades agrícolas realizadas en los viveros.
- **Atributos obligatorios:**
  - `fecha`: Fecha de realización de la labor
  - `descripcion`: Descripción de la labor
  - `vivero`: Vivero donde se realiza (ManyToOne)
- **Atributos opcionales:**
  - `productoControl`: Producto de control utilizado (ManyToOne)
- **Relaciones:**
  - Pertenece a un `Vivero` (ManyToOne)
  - Puede tener un `ProductoControl` (ManyToOne)

### 5. ProductoControlModel (Clase Abstracta)
Clase base para todos los productos de control.
- **Atributos obligatorios:**
  - `registroICA`: Registro ICA del producto
  - `nombreProducto`: Nombre comercial del producto
  - `frecuenciaAplicacion`: Frecuencia de aplicación en días
  - `valor`: Valor del producto
- **Herencia:** Implementa estrategia JOINED

### 6. ProductoControlHongoModel
Producto para control de hongos.
- **Hereda:** ProductoControlModel
- **Atributos adicionales:**
  - `periodoCarencia`: Período de carencia en días
  - `nombreHongo`: Nombre del hongo que controla

### 7. ProductoControlPlagaModel
Producto para control de plagas.
- **Hereda:** ProductoControlModel
- **Atributos adicionales:**
  - `periodoCarencia`: Período de carencia en días

### 8. ProductoControlFertilizanteModel
Producto fertilizante.
- **Hereda:** ProductoControlModel
- **Atributos adicionales:**
  - `fechaUltimaAplicacion`: Fecha de la última aplicación

## Diagrama de Clases (Conceptual)

```
┌─────────────────────┐
│  ProductorModel     │
├─────────────────────┤
│ - id: Long          │
│ - documento: String │
│ - nombre: String    │
│ - apellido: String  │
│ - telefono: String  │
│ - correo: String    │
└──────────┬──────────┘
           │ 1
           │
           │ *
┌──────────▼──────────┐
│   FincaModel        │
├─────────────────────┤
│ - id: Long          │
│ - numeroCatastro    │
│ - municipio: String │
└──────────┬──────────┘
           │ 1
           │
           │ *
┌──────────▼──────────┐
│   ViveroModel       │
├─────────────────────┤
│ - id: Long          │
│ - codigo: String    │
│ - tipoCultivo       │
└──────────┬──────────┘
           │ 1
           │
           │ *
┌──────────▼──────────┐          ┌─────────────────────────┐
│   LaborModel        │ *     0..1│  ProductoControlModel   │
├─────────────────────┤◄──────────┤  (Abstract)             │
│ - id: Long          │          ├─────────────────────────┤
│ - fecha: LocalDate  │          │ - registroICA: String   │
│ - descripcion       │          │ - nombreProducto        │
└─────────────────────┘          │ - frecuenciaAplicacion  │
                                 │ - valor: BigDecimal     │
                                 └────────────┬────────────┘
                                              │
                    ┌─────────────────────────┼─────────────────────────┐
                    │                         │                         │
        ┌───────────▼────────────┐ ┌─────────▼─────────┐ ┌────────────▼──────────────┐
        │ProductoControlHongoModel│ │ProductoControl    │ │ProductoControl            │
        │                         │ │PlagaModel         │ │FertilizanteModel          │
        ├─────────────────────────┤ ├───────────────────┤ ├───────────────────────────┤
        │+ periodoCarencia: Int   │ │+ periodoCarencia  │ │+ fechaUltimaAplicacion    │
        │+ nombreHongo: String    │ │                   │ │                           │
        └─────────────────────────┘ └───────────────────┘ └───────────────────────────┘
```

## Repositorios

Se han implementado los siguientes repositorios JPA:

1. **ProductorRepository** - Gestión de productores
2. **FincaRepository** - Gestión de fincas
3. **ViveroRepository** - Gestión de viveros
4. **LaborRepository** - Gestión de labores
5. **ProductoControlRepository** - Gestión de productos de control (base)
6. **ProductoControlHongoRepository** - Gestión de productos para hongos
7. **ProductoControlPlagaRepository** - Gestión de productos para plagas
8. **ProductoControlFertilizanteRepository** - Gestión de fertilizantes

## Pruebas Unitarias

El proyecto incluye pruebas unitarias completas para:

### Pruebas por Entidad (mínimo 3 por entidad):
- **ProductorModelTest**: 6 pruebas
- **FincaModelTest**: 6 pruebas
- **ViveroModelTest**: 6 pruebas
- **LaborModelTest**: 6 pruebas
- **ProductoControlHongoModelTest**: 6 pruebas
- **ProductoControlPlagaModelTest**: 6 pruebas
- **ProductoControlFertilizanteModelTest**: 6 pruebas

### Pruebas de Relaciones:
- **RelacionesEntidadesTest**: 8 pruebas que verifican:
  - Relación Productor-Finca
  - Relación Finca-Vivero
  - Relación Vivero-Labor
  - Relación Labor-ProductoControl
  - Cascadas de persistencia
  - Herencia de productos de control
  - Relaciones completas de toda la cadena

## Tecnologías Utilizadas

- **Java 17**
- **Spring Boot 3.5.5**
- **Spring Data JPA**
- **MySQL Connector**
- **JUnit 5** (para pruebas)
- **Maven** (gestión de dependencias)

## Configuración de la Base de Datos

Configurar las credenciales de la base de datos en `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vivero_db
spring.datasource.username=tu_usuario
spring.datasource.password=tu_contraseña
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
```

## Ejecución del Proyecto

### Compilar el proyecto:
```bash
mvn clean compile
```

### Ejecutar las pruebas:
```bash
mvn test
```

### Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

## Características Implementadas

✅ Modelo de entidades completo con relaciones JPA  
✅ Validación de campos obligatorios con Jakarta Validation  
✅ Repositorios JPA para todas las entidades  
✅ Herencia JOINED para productos de control  
✅ Cascadas de persistencia y borrado  
✅ Métodos helper para gestión de relaciones bidireccionales  
✅ Más de 44 pruebas unitarias  
✅ Pruebas de relaciones entre entidades  
✅ Pruebas de herencia  
✅ Todos los campos son obligatorios según requerimientos  

## Estructura del Proyecto

```
vivero/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/angie/vivero/
│   │   │       ├── models/           # Entidades JPA
│   │   │       ├── repositories/     # Repositorios JPA
│   │   │       ├── services/         # Servicios de negocio
│   │   │       └── controllers/      # Controladores REST
│   │   └── resources/
│   │       └── application.properties
│   └── test/
│       └── java/
│           └── com/angie/vivero/
│               └── models/           # Pruebas unitarias
├── pom.xml
└── README.md
```

## Autora

Angie - Sistema de Gestión de Viveros

## Repositorio

GitHub: Mysticxs01/vivero
