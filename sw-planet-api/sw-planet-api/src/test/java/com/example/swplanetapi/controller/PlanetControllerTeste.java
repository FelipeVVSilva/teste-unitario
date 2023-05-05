package com.example.swplanetapi.controller;

import static com.example.swplanetapi.common.PlanetConstants.PLANET;
import static com.example.swplanetapi.common.PlanetConstants.EXISTING_ID;
import static com.example.swplanetapi.common.PlanetConstants.NON_EXISTING_ID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Test
    public void createPlanet_WithExistingPlanetName_ThrowsConflict() throws Exception{

        when(planetService.create(any())).thenThrow(DataIntegrityViolationException.class);

        mockMvc
            .perform(post("/planets").content(objectMapper.writeValueAsString(PLANET))
                    .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isConflict());


    }

    @Test
    public void getPlane_ByExistingId_ReturnsPlanet() throws Exception{

        when(planetService.findPlanetById(EXISTING_ID)).thenReturn(Optional.of(PLANET));

        mockMvc
            .perform(get("/planets/" + EXISTING_ID))
        .andExpect(jsonPath("$").value(PLANET))
        .andExpect(status().isOk());

    }

    @Test
    public void getPlane_ByUnexistingId_ReturnsPlanet() throws Exception{

        mockMvc
            .perform(get("/planets/1"))
        .andExpect(status().isNotFound());

    }

}
