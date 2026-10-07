# Gestor de Inventario - Demo 2 🚀

Este es el proyecto evolutivo del curso para construir un **Gestor de Inventario**. Esta primera fase se enfoca en hacer que la API sea identificable y autodescriptiva implementando metadatos dinámicos.

## 📌 Características Implementadas

* **Metadata de la Aplicación:** Endpoint `/api/info` que devuelve los detalles del proyecto (nombre, descripción, versión, correo del desarrollador).
* **Versión Dinámica:** La versión de la aplicación se inyecta y sincroniza automáticamente desde `build.gradle` (vía `BuildProperties`).
* **Manejo de Perfiles (Profiles):** La configuración está dividida por entornos:
  * `application.yml` (Global)
  * `application-dev.yml` (Desarrollo, base de datos H2 en memoria)
  * `application-prod.yml` (Producción)
* **Validaciones Fuertes:** Uso de `@ConfigurationProperties` y `@Validated` en las propiedades de Spring Boot para forzar el formato correcto desde el arranque (ej. validación `@Email`).
* **Base de Datos en Memoria:** Integración de **H2 Database** para que el proyecto pueda arrancar y ser probado sin necesidad de configurar servidores de base de datos externos.

## 🛠️ Tecnologías

* Java 23
* Spring Boot 3.x / 4.x
* Spring Web, Spring Security, Spring Boot Actuator
* Spring Data JPA
* H2 Database
* Gradle

## 🚀 Cómo Ejecutar el Proyecto

Asegúrate de tener Java 23 configurado en tu entorno.

Para ejecutar la aplicación localmente en el entorno de desarrollo (puerto 8080):

```bash
./gradlew bootRun
```

Para correr las pruebas unitarias:

```bash
./gradlew test
```

## 📡 Endpoints Principales

* `GET /api/info` - Devuelve la metadata dinámica del sistema (sin autenticación, abierta al cliente).
* `GET /actuator/health` - Estado de salud de la aplicación (provisto por Actuator).

## 📄 Decisiones Arquitectónicas

Todas las decisiones de diseño (como el uso de `@ConfigurationProperties` sobre `@Value`, diferencias con `/actuator/info`, y las pruebas forzadas de error) se documentan a detalle en el archivo [DECISIONES.md](./DECISIONES.md).
