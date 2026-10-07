package com.example.demo2.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

// @Component le dice a Spring que gestione esta clase y la convierta en un "Bean" (un objeto disponible en todo el proyecto).
@Component
// @ConfigurationProperties agrupa todas las propiedades de application.yml que empiecen con "app.info".
@ConfigurationProperties(prefix = "app.info")
// @Validated permite usar anotaciones de validación para asegurar que las propiedades no vengan vacías o con formatos incorrectos.
@Validated
public class AppInfoProperties {

    // Estas variables deben llamarse exactamente igual que en el YAML (en formato camelCase o kebab-case).
    @NotBlank(message = "El nombre del proyecto no puede estar vacío")
    private String name;

    // Quitamos 'version' estática porque la vamos a inyectar desde el BuildProperties generado por Gradle.

    @NotBlank(message = "La descripción no puede estar vacía")
    private String description;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un formato de email válido")
    private String developerEmail;

    // Los "Getters" y "Setters" son OBLIGATORIOS porque Spring Boot los usa por detrás para inyectar los valores del YAML.
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getDeveloperEmail() { return developerEmail; }
    public void setDeveloperEmail(String developerEmail) { this.developerEmail = developerEmail; }
}
