package com.example.demo2.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = HelloController.class)
@AutoConfigureMockMvc(addFilters = false)
public class HelloControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testDefaultGreeting() throws Exception {
        // Prueba por defecto: sin parámetros, espera "Hola World!"
        mockMvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hola World!"));
    }

    @Test
    public void testGreetingWithParameters() throws Exception {
        // Prueba con parámetros: lang=en, name=Mike, espera "Hello Mike!"
        mockMvc.perform(get("/hello")
                .param("name", "Mike")
                .param("lang", "en"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello Mike!"));
    }
}
