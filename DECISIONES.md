# Decisiones Arquitectónicas - Gestor de Inventario

## 1. ¿Por qué usaste `@ConfigurationProperties` en lugar de `@Value`?
Utilizar `@ConfigurationProperties` es superior a `@Value` cuando tenemos múltiples propiedades agrupadas bajo un mismo prefijo (en nuestro caso `app.info`). Nos permite:
* **Tipado fuerte y validación REAL:** Integramos `@Validated`, `@NotBlank` y `@Email` directamente en `AppInfoProperties`. Spring Boot falla automáticamente al arrancar si el `application.yml` falta o tiene un email mal formateado. Esto es imposible de hacer limpiamente solo con `@Value`.
* **Mantenibilidad:** Si agregamos más propiedades, solo modificamos la clase, sin necesidad de tocar constructores.

## 2. ¿Cómo garantizas que la versión no se desincronice del pom.xml / build.gradle?
Agregamos el bloque `springBoot { buildInfo() }` en `build.gradle`. Esto le dice al compilador de Gradle que genere un objeto llamado `BuildProperties` en tiempo de compilación con la versión real del proyecto. Luego, inyectamos `BuildProperties` en el `InfoController`. ¡La versión leída en `/api/info` es 100% la versión real compilada!

## 3. ¿Qué pasaría si quitas el `@Component` de `AppInfoProperties`?
**Realicé la prueba real.** Al borrar `@Component`, la aplicación falla y aborta el arranque (`bootRun`) lanzando exactamente este error en la consola:
```text
Description:
Parameter 0 of constructor in com.example.demo2.controller.InfoController required a bean of type 'com.example.demo2.config.AppInfoProperties' that could not be found.
Action:
Consider defining a bean of type 'com.example.demo2.config.AppInfoProperties' in your configuration.
```
Esto ocurre porque la clase dejó de ser un Bean manejado por Spring, y cuando el controlador lo pide en su constructor, Spring no sabe de dónde sacarlo.

## 4. ¿Qué diferencia hay entre tu `/info` y el `/actuator/info` de Spring?
* **`/api/info` (Nuestro):** Es un endpoint de negocio/público. Devuelve exactamente el DTO (`AppInfoDTO`) que nosotros diseñamos, incluyendo nuestro email de desarrollador validado.
* **`/actuator/info`:** Es un endpoint estándar de infraestructura. Al agregar la dependencia `spring-boot-starter-actuator`, este se habilitó automáticamente. Devuelve variables de entorno del sistema, pero no está pensado para ser consumido por un cliente frontend como parte de la lógica.

## 5. Exploración de Propiedades (Pruebas reales)
Agregamos las dependencias reales (`spring-boot-starter-security`, `spring-boot-starter-data-jpa`, `h2`) y configuramos:

* **Servidor:** Al usar `server.port: 80` en `application-prod.yml` y arrancar el perfil `prod`, el log indica `Tomcat initialized with port 80`.
* **Bases de datos:** Configuramos `jdbc:h2:mem:demo2_db` (H2 en memoria). Al arrancar, JPA inicializa el esquema y los logs muestran `Hibernate ORM core version 7.4.5`. Si configurábamos PostgreSQL sin tenerlo instalado, la app fallaba con `Unable to determine Dialect`. H2 garantiza que podamos probar en desarrollo local sin servidores externos.
* **Seguridad:** Al estar habilitado el starter de seguridad, **todos** los endpoints requerían usuario y contraseña. Para que las pruebas pasaran, se utilizó `@AutoConfigureMockMvc(addFilters = false)` aislando el test.
* **Perfiles (Archivos separados):** Separamos la configuración en `application.yml`, `application-dev.yml` y `application-prod.yml`.

## 6. Reto de Comprensión (Prueba real)
* **Propiedad alterada:** `server.port=abc` (en un archivo temporal `application-testerror.yml`)
* **Error real lanzado por Spring Boot:**
```text
Description:
Invalid value 'abc' for configuration property 'server.port' (originating from 'class path resource [application-testerror.yml] - 2:9'). Validation failed for the following reason:
Failed to convert to type java.lang.Integer
```
* **Explicación:** Ocurre porque la propiedad `server.port` está estrictamente tipada como un número entero (`Integer`). Al recibir la cadena alfanumérica "abc", la fase de conversión (*Type Conversion*) falla lanzando una excepción de *Binding*, abortando el arranque.
