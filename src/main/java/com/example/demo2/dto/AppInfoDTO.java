package com.example.demo2.dto;

// Un 'record' en Java es una forma moderna y corta de crear una clase que solo transporta datos (es inmutable).
// Ya no necesitamos escribir constructores, getters ni setters manualmente. Todo eso se autogenera.
public record AppInfoDTO(
    String name,           // Guardará el nombre del proyecto
    String version,        // Guardará la versión
    String description,    // Guardará la descripción
    String developerEmail, // Guardará el correo del desarrollador
    String environment     // Guardará qué perfil está activo (dev o prod)
) {}
