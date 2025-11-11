# Guía de Instalación y Ejecución

## Requisitos Previos

Antes de ejecutar el proyecto, asegúrate de tener instalado:

1. **Java Development Kit (JDK) 17 o superior**
   - Descargar desde: https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html
   - O usar OpenJDK: https://adoptium.net/

2. **MySQL Server 8.0 o superior**
   - Descargar desde: https://dev.mysql.com/downloads/mysql/

3. **Maven** (opcional, el proyecto incluye Maven Wrapper)
   - Descargar desde: https://maven.apache.org/download.cgi

## Paso 1: Configurar Variables de Entorno

### Configurar JAVA_HOME

#### En Windows:
```powershell
# Verificar si Java está instalado
java -version

# Configurar JAVA_HOME (ajusta la ruta según tu instalación)
setx JAVA_HOME "C:\Program Files\Java\jdk-17"

# Agregar Java al PATH
setx PATH "%PATH%;%JAVA_HOME%\bin"
```

#### En Linux/Mac:
```bash
# Agregar al archivo ~/.bashrc o ~/.zshrc
export JAVA_HOME=/usr/lib/jvm/java-17-openjdk
export PATH=$PATH:$JAVA_HOME/bin

# Recargar la configuración
source ~/.bashrc
```

## Paso 2: Configurar MySQL

### Crear la Base de Datos

```sql
-- Conectarse a MySQL
mysql -u root -p

-- Crear la base de datos
CREATE DATABASE vivero CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- Crear un usuario (opcional)
CREATE USER 'vivero_user'@'localhost' IDENTIFIED BY 'vivero_pass';
GRANT ALL PRIVILEGES ON vivero.* TO 'vivero_user'@'localhost';
FLUSH PRIVILEGES;

-- Salir
EXIT;
```

### Configurar Credenciales

Editar el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vivero
spring.datasource.username=root
spring.datasource.password=tu_contraseña_aqui
```

## Paso 3: Compilar el Proyecto

### Usando Maven Wrapper (recomendado):

#### En Windows (PowerShell):
```powershell
.\mvnw.cmd clean compile
```

#### En Linux/Mac:
```bash
./mvnw clean compile
```

### Usando Maven instalado:
```bash
mvn clean compile
```

## Paso 4: Ejecutar las Pruebas

### Usando Maven Wrapper:

#### En Windows (PowerShell):
```powershell
.\mvnw.cmd test
```

### Usando Maven instalado:
```bash
mvn test
```

## Paso 5: Ejecutar la Aplicación

### Usando Maven Wrapper:

#### En Windows (PowerShell):
```powershell
.\mvnw.cmd spring-boot:run
```

### Usando Maven instalado:
```bash
mvn spring-boot:run
```

## Solución de Problemas Comunes

### Error: "JAVA_HOME is not defined correctly"

**Solución:**
1. Verifica que JDK esté instalado: `java -version`
2. Configura JAVA_HOME apuntando al directorio de JDK (no JRE)
3. Reinicia la terminal o IDE

### Error: "Access denied for user 'root'@'localhost'"

**Solución:**
1. Verifica las credenciales en `application.properties`
2. Asegúrate de que MySQL esté corriendo
3. Verifica que el usuario tenga permisos en la base de datos

### Error: "Table doesn't exist"

**Solución:**
- El proyecto usa `spring.jpa.hibernate.ddl-auto=update`, las tablas se crearán automáticamente
- Asegúrate de que la base de datos `vivero` exista
- Verifica la conexión a MySQL

### Error: "Port 8080 already in use"

**Solución:**
Cambia el puerto en `application.properties`:
```properties
server.port=8081
```

## Verificar la Instalación

### Comprobar que las tablas se crearon:

```sql
USE vivero;
SHOW TABLES;
```

Deberías ver las siguientes tablas:
- productores
- fincas
- viveros
- labores
- productos_control
- productos_control_hongo
- productos_control_plaga
- productos_control_fertilizante

## Ejecución desde un IDE

### IntelliJ IDEA:
1. Abrir el proyecto (File → Open → seleccionar la carpeta del proyecto)
2. Esperar a que Maven descargue las dependencias
3. Ejecutar las pruebas: Click derecho en `src/test/java` → Run 'All Tests'
4. Ejecutar la aplicación: Click derecho en `ViveroApplication.java` → Run

### Eclipse:
1. Importar proyecto Maven (File → Import → Maven → Existing Maven Projects)
2. Seleccionar la carpeta del proyecto
3. Ejecutar las pruebas: Click derecho en el proyecto → Run As → JUnit Test
4. Ejecutar la aplicación: Click derecho en `ViveroApplication.java` → Run As → Java Application

### VS Code:
1. Instalar las extensiones:
   - Extension Pack for Java
   - Spring Boot Extension Pack
2. Abrir la carpeta del proyecto
3. Usar la vista de Testing para ejecutar las pruebas
4. Usar el comando "Spring Boot: Run" para ejecutar la aplicación

## Comandos Útiles de Maven

```bash
# Limpiar el proyecto
mvn clean

# Compilar
mvn compile

# Ejecutar pruebas
mvn test

# Empaquetar (crear JAR/WAR)
mvn package

# Saltar pruebas durante empaquetado
mvn package -DskipTests

# Ver dependencias
mvn dependency:tree

# Actualizar dependencias
mvn clean install -U
```

## Estructura de Pruebas

Las pruebas se encuentran en:
```
src/test/java/com/angie/vivero/models/
├── ProductorModelTest.java          (6 pruebas)
├── FincaModelTest.java              (6 pruebas)
├── ViveroModelTest.java             (6 pruebas)
├── LaborModelTest.java              (6 pruebas)
├── ProductoControlHongoModelTest.java       (6 pruebas)
├── ProductoControlPlagaModelTest.java       (6 pruebas)
├── ProductoControlFertilizanteModelTest.java (6 pruebas)
└── RelacionesEntidadesTest.java     (8 pruebas)

Total: 50 pruebas unitarias
```

## Modo de Desarrollo

Para desarrollo continuo con recarga automática:

```bash
mvn spring-boot:run -Dspring-boot.run.fork=false
```

O configurar en `application.properties`:
```properties
spring.devtools.restart.enabled=true
```