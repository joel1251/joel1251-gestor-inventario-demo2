package com.example.demo2.controller;

import com.example.demo2.config.AppInfoProperties;
import com.example.demo2.dto.AppInfoDTO;
import org.springframework.boot.info.BuildProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// @RestController indica que esta clase va a responder a peticiones web y devolverá JSON directamente (no páginas HTML).
@RestController
// @RequestMapping("/api") hace que TODAS las rutas de este controlador empiecen por "/api".
@RequestMapping("/api")
public class InfoController {

    // Declaramos nuestra clase de propiedades como inyectable.
    private final AppInfoProperties appInfoProperties;
    
    // Objeto proporcionado por Spring Boot cuando configuramos springBoot { buildInfo() } en Gradle.
    // Garantiza que la versión sea exactamente la del proyecto compilado.
    private final BuildProperties buildProperties;
    
    // @Value inyecta dinámicamente el perfil activo (dev, prod). Si no hay ninguno configurado, pone "default".
    @Value("${spring.profiles.active:default}")
    private String activeProfile;

    // Inyección de dependencias por constructor.
    public InfoController(AppInfoProperties appInfoProperties, BuildProperties buildProperties) {
        this.appInfoProperties = appInfoProperties;
        this.buildProperties = buildProperties;
    }

    // @GetMapping("/info") indica que este método se ejecuta al visitar la ruta GET /api/info
    @GetMapping("/info")
    public AppInfoDTO getInfo() {
        // Creamos una nueva instancia de nuestro Record (DTO) pasando los valores obtenidos.
        return new AppInfoDTO(
                appInfoProperties.getName(),
                buildProperties.getVersion(),
                appInfoProperties.getDescription(),
                appInfoProperties.getDeveloperEmail(),
                activeProfile
        );
    }
}
