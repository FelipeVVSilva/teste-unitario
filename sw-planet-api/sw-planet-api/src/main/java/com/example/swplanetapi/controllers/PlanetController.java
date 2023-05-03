package com.example.swplanetapi.controllers;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.swplanetapi.domains.Planet;
import com.example.swplanetapi.services.PlanetService;

@RestController
@RequestMapping("/planets")
public class PlanetController {
    
    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@RequestBody Planet planet){
        planet = planetService.create(planet);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(planet.getId()).toUri();
        return ResponseEntity.created(uri).body(planet);
    }

}