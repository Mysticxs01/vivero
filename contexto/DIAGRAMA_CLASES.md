# Diagrama de Clases - Sistema de Gestión de Viveros

## Relaciones entre Entidades

### 1. Relación Productor → Finca → Vivero → Labor

```
ProductorModel (1) ──────── (*) FincaModel (1) ──────── (*) ViveroModel (1) ──────── (*) LaborModel
```

**Descripción de relaciones:**
- Un `Productor` puede tener **muchas** `Fincas` (OneToMany)
- Una `Finca` pertenece a **un** `Productor` (ManyToOne)
- Una `Finca` puede tener **muchos** `Viveros` (OneToMany)
- Un `Vivero` pertenece a **una** `Finca` (ManyToOne)
- Un `Vivero` puede tener **muchas** `Labores` (OneToMany)
- Una `Labor` pertenece a **un** `Vivero` (ManyToOne)

### 2. Relación Labor → ProductoControl

```
LaborModel (1) ──────── (0..1) ProductoControlModel
```

**Descripción:**
- Una `Labor` puede usar **cero o un** `ProductoControl` (ManyToOne opcional)
- Un `ProductoControl` puede ser usado en **muchas** `Labores`

### 3. Herencia de Productos de Control

```
                    ProductoControlModel (Abstract)
                             │
          ┌──────────────────┼──────────────────┐
          │                  │                  │
ProductoControlHongoModel  ProductoControlPlagaModel  ProductoControlFertilizanteModel
```

**Estrategia de herencia:** JOINED
- Cada subclase tiene su propia tabla
- La tabla base contiene los campos comunes
- Las tablas derivadas contienen los campos específicos

## Detalle de Entidades

### ProductorModel
```
┌─────────────────────────────────┐
│      ProductorModel             │
├─────────────────────────────────┤
│ - id: Long (PK)                 │
│ - documento: String (UNIQUE)    │
│ - nombre: String                │
│ - apellido: String              │
│ - telefono: String              │
│ - correo: String                │
├─────────────────────────────────┤
│ - fincas: List<FincaModel>      │
└─────────────────────────────────┘
```

### FincaModel
```
┌─────────────────────────────────┐
│        FincaModel               │
├─────────────────────────────────┤
│ - id: Long (PK)                 │
│ - numeroCatastro: String (UNQ)  │
│ - municipio: String             │
├─────────────────────────────────┤
│ - productor: ProductorModel (FK)│
│ - viveros: List<ViveroModel>    │
└─────────────────────────────────┘
```

### ViveroModel
```
┌─────────────────────────────────┐
│        ViveroModel              │
├─────────────────────────────────┤
│ - id: Long (PK)                 │
│ - codigo: String                │
│ - tipoCultivo: String           │
├─────────────────────────────────┤
│ - finca: FincaModel (FK)        │
│ - labores: List<LaborModel>     │
└─────────────────────────────────┘
```

### LaborModel
```
┌─────────────────────────────────────┐
│          LaborModel                 │
├─────────────────────────────────────┤
│ - id: Long (PK)                     │
│ - fecha: LocalDate                  │
│ - descripcion: String               │
├─────────────────────────────────────┤
│ - vivero: ViveroModel (FK)          │
│ - productoControl:                  │
│   ProductoControlModel (FK opcional)│
└─────────────────────────────────────┘
```

### ProductoControlModel (Abstract)
```
┌─────────────────────────────────────┐
│    ProductoControlModel             │
│         (Abstract)                  │
├─────────────────────────────────────┤
│ - id: Long (PK)                     │
│ - registroICA: String               │
│ - nombreProducto: String            │
│ - frecuenciaAplicacion: Integer     │
│ - valor: BigDecimal                 │
└─────────────────────────────────────┘
```

### ProductoControlHongoModel
```
┌─────────────────────────────────────┐
│  ProductoControlHongoModel          │
│  extends ProductoControlModel       │
├─────────────────────────────────────┤
│ (hereda todos los campos de base)   │
│ + periodoCarencia: Integer          │
│ + nombreHongo: String               │
└─────────────────────────────────────┘
```

### ProductoControlPlagaModel
```
┌─────────────────────────────────────┐
│  ProductoControlPlagaModel          │
│  extends ProductoControlModel       │
├─────────────────────────────────────┤
│ (hereda todos los campos de base)   │
│ + periodoCarencia: Integer          │
└─────────────────────────────────────┘
```

### ProductoControlFertilizanteModel
```
┌─────────────────────────────────────┐
│ ProductoControlFertilizanteModel    │
│  extends ProductoControlModel       │
├─────────────────────────────────────┤
│ (hereda todos los campos de base)   │
│ + fechaUltimaAplicacion: LocalDate  │
└─────────────────────────────────────┘
```

## Anotaciones JPA Principales

- **@Entity**: Marca la clase como entidad JPA
- **@Table**: Especifica el nombre de la tabla
- **@Id**: Marca el campo como clave primaria
- **@GeneratedValue**: Generación automática de valores
- **@Column**: Configuración de columnas (nullable, unique, etc.)
- **@ManyToOne**: Relación muchos a uno
- **@OneToMany**: Relación uno a muchos
- **@JoinColumn**: Especifica la columna de unión (FK)
- **@Inheritance**: Define la estrategia de herencia
- **@NotNull / @NotBlank**: Validaciones de Jakarta Validation

## Validaciones

Todos los campos de las entidades son **obligatorios** según los requerimientos:

- Se utiliza `@NotBlank` para Strings (no pueden ser null ni vacíos)
- Se utiliza `@NotNull` para otros tipos (no pueden ser null)
- Se utiliza `@Column(nullable = false)` a nivel de base de datos
- Las validaciones se aplican tanto en el modelo como en la base de datos

## Cascadas

Se implementan las siguientes cascadas:

- **ProductorModel → FincaModel**: `CascadeType.ALL` + `orphanRemoval = true`
- **FincaModel → ViveroModel**: `CascadeType.ALL` + `orphanRemoval = true`
- **ViveroModel → LaborModel**: `CascadeType.ALL` + `orphanRemoval = true`

Esto significa que:
- Al guardar un Productor, se guardan automáticamente sus Fincas
- Al eliminar un Productor, se eliminan automáticamente sus Fincas
- Al eliminar una Finca de un Productor, la Finca se elimina de la base de datos
- Lo mismo aplica para toda la cadena de relaciones
