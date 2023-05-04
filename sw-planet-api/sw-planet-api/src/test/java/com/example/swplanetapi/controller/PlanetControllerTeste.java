package com.example.swplanetapi.controller;

import static com.example.swplanetapi.common.PlanetConstants.PLANET;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.swplanetapi.controllers.PlanetController;
import com.example.swplanetapi.domains.Planet;
import com.example.swplanetapi.services.PlanetService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(PlanetController.class)
public class PlanetControllerTeste {
    
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_Reurns200AndPlanet() throws Exception{
        
        when(planetService.create(PLANET)).thenReturn(PLANET);

        mockMvc
            .perform(post("/planets").content(objectMapper.writeValueAsString(PLANET))
                    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$").value(PLANET));

    }

    @Test
    public void createPlanet_WithInvalidData_ReurnsBadRequest() throws Exception{

        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet(null, "", "", "");

        mockMvc
            .perform(post("/planets").content(objectMapper.writeValueAsString(emptyPlanet))
                    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity());

        mockMvc
            .perform(post("/planets").content(objectMapper.writeValueAsString(invalidPlanet))
                    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isUnprocessableEntity());

    }

}
