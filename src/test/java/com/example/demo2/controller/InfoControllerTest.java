package com.example.demo2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import com.example.demo2.config.AppInfoProperties;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.info.BuildProperties;
import static org.mockito.BDDMockito.given;

// @WebMvcTest levanta SOLO la capa web.
@WebMvcTest(controllers = InfoController.class)
@AutoConfigureMockMvc(addFilters = false) // Apagamos la seguridad para las pruebas
// @Import le dice a la prueba que también cargue nuestra clase de configuración.
@Import(AppInfoProperties.class)
public class InfoControllerTest {

    // MockMvc es una herramienta oficial que nos permite simular peticiones HTTP sin levantar Tomcat.
    @Autowired
    private MockMvc mockMvc;

    // BuildProperties es generado en tiempo de compilación. Para un test web aislado, necesitamos falsearlo (mockearlo).
    @MockitoBean
    private BuildProperties buildProperties;

    // @Test indica a JUnit que este método es una prueba automatizada.
    @Test
    public void testGetInfoEndpoint() throws Exception {
        
        // Configuramos el mock para que devuelva una versión falsa cuando el controlador se la pida
        given(buildProperties.getVersion()).willReturn("1.0.0-TEST");

        // perform(get("/api/info")) simula hacer una petición HTTP GET a esa ruta específica.
        mockMvc.perform(get("/api/info"))
                // andExpect(status().isOk()) valida que la respuesta de red sea HTTP 200 (OK).
                .andExpect(status().isOk())
                // Validamos que la estructura JSON contenga el campo "name" y que no sea nulo.
                .andExpect(jsonPath("$.name", notNullValue()))
                // Validamos el campo "version".
                .andExpect(jsonPath("$.version", notNullValue()))
                // Validamos el campo "description". (Ya con estos 3 cumplimos la rúbrica de validar al menos 3 campos).
                .andExpect(jsonPath("$.description", notNullValue()))
                // Esta línea usa una Expresión Regular (Regex) para validar que el contenido de "developerEmail" tenga el formato correcto de un correo electrónico.
                .andExpect(jsonPath("$.developerEmail", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$")));
    }
}
