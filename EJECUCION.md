# Guía de Ejecución - Sistema de Gestión de Vivero

## 🚀 Cómo ejecutar la aplicación

### Opción 1: Usando Maven Wrapper (Recomendado)

```powershell
# Ejecutar la aplicación
.\mvnw.cmd spring-boot:run
```

**IMPORTANTE:** 
- **NO cierres la ventana de PowerShell**
- **NO presiones Ctrl+C**
- Deja la terminal ejecutándose

Verás estos mensajes cuando esté lista:
```
Tomcat started on port 8080 (http) with context path '/'
Started ViveroApplication in X.XXX seconds
```

### Opción 2: Ejecutar el JAR/WAR empaquetado

```powershell
# 1. Compilar el proyecto
.\mvnw.cmd clean package -DskipTests

# 2. Ejecutar el archivo WAR generado
java -jar target\vivero-0.0.1-SNAPSHOT.war
```

## 🌐 Acceder a la aplicación

Una vez iniciada, la aplicación está disponible en:

- **URL Principal:** http://localhost:8080
- **Health Check:** http://localhost:8080/health
- **API Base:** http://localhost:8080/api

## 🔍 Verificar que está funcionando

### Desde el navegador:

1. Abre tu navegador
2. Ve a: http://localhost:8080
3. Deberías ver un mensaje JSON:
```json
{
  "message": "Bienvenido al Sistema de Gestión de Vivero",
  "version": "1.0.0",
  "status": "✅ Aplicación funcionando correctamente",
  "endpoints": {
    "api": "/api",
    "health": "/actuator/health"
  }
}
```

### Desde PowerShell (sin cerrar el servidor):

Abre una NUEVA ventana de PowerShell y ejecuta:

```powershell
# Verificar que el servidor responde
curl http://localhost:8080/health

# O usando Invoke-WebRequest
Invoke-WebRequest -Uri http://localhost:8080 -UseBasicParsing
```

## ⏹️ Detener la aplicación

Para detener la aplicación correctamente:

1. Ve a la terminal donde está ejecutándose
2. Presiona `Ctrl + C`
3. Espera a que muestre "Graceful shutdown complete"

## 🔧 Solución de problemas

### La aplicación se cierra inmediatamente

**Problema:** Si ves que se cierra después de mostrar "Started ViveroApplication", puede ser porque:

1. **Ejecutaste desde el IDE:** Algunos IDEs detienen la aplicación después del `DataInitializer`
2. **Presionaste Ctrl+C accidentalmente**
3. **La terminal se cerró**

**Solución:** 
- Ejecuta desde una terminal de PowerShell dedicada
- No cierres la terminal
- No presiones Ctrl+C hasta que quieras detener el servidor

### Puerto 8080 ya está en uso

```
Web server failed to start. Port 8080 was already in use.
```

**Solución 1:** Detener el proceso que usa el puerto

```powershell
# Ver qué proceso usa el puerto 8080
netstat -ano | findstr :8080

# Detener el proceso (reemplaza PID con el número mostrado)
taskkill /PID <PID> /F
```

**Solución 2:** Cambiar el puerto en `application.properties`

```properties
server.port=8081
```

### Error de conexión a MySQL

```
Communications link failure
```

**Solución:**
1. Verifica que MySQL esté ejecutándose
2. Verifica las credenciales en `application.properties`
3. Asegúrate de que la base de datos `vivero` existe

```powershell
mysql -u root -proot -e "SHOW DATABASES LIKE 'vivero';"
```

## 📊 Logs de la aplicación

Los logs se muestran en la consola donde ejecutaste la aplicación. Busca estos mensajes importantes:

```
✅ Inicio exitoso:
- "Tomcat started on port 8080"
- "Started ViveroApplication"

✅ Base de datos:
- "HikariPool-1 - Start completed"
- "Database version: 8.0.41"

✅ Datos de prueba:
- "=== Base de datos ya contiene datos. Omitiendo inicialización ===" 
  (Si ya existen datos)
- "=== ✅ Datos de prueba cargados exitosamente ===" 
  (Si se cargaron nuevos datos)
```

## 🧪 Ejecutar pruebas

```powershell
# Ejecutar todas las pruebas
.\mvnw.cmd test

# Ejecutar pruebas de una clase específica
.\mvnw.cmd test -Dtest=ProductorModelTest

# Ejecutar con verbose
.\mvnw.cmd test -X
```

## 📦 Compilar sin ejecutar

```powershell
# Solo compilar
.\mvnw.cmd compile

# Compilar y empaquetar
.\mvnw.cmd package

# Compilar sin ejecutar pruebas
.\mvnw.cmd package -DskipTests
```

## 🔄 Recargar datos de prueba

Si quieres recargar los datos de prueba:

```powershell
# 1. Limpiar la base de datos
mysql -u root -proot vivero < scripts/clean_database.sql

# 2. Reiniciar la aplicación
.\mvnw.cmd spring-boot:run
```

O ejecutar el script SQL directamente:

```powershell
mysql -u root -proot vivero < src\main\resources\data.sql
```

---
