package com.example.swplanetapi.controllers;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.swplanetapi.domains.Planet;
import com.example.swplanetapi.services.PlanetService;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/planets")
public class PlanetController {
    
    @Autowired
    private PlanetService planetService;

    @PostMapping
    public ResponseEntity<Planet> create(@Valid @RequestBody Planet planet){
        planet = planetService.create(planet);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
				.buildAndExpand(planet.getId()).toUri();
        return ResponseEntity.created(uri).body(planet);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Planet> findPlanetById(@PathVariable Long id) {
        return planetService.findPlanetById(id).map(planet -> ResponseEntity.ok(planet))
          .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Planet> findPlanetById(@PathVariable String name) {
        return planetService.findPlanetByName(name).map(planet -> ResponseEntity.ok(planet))
          .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Planet>> findAllPlanets(
              @RequestParam(required = false) String terrain,
              @RequestParam(required = false) String climate){
      List<Planet> list = planetService.findAllPlanets(terrain, climate);
      return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanetById(@PathVariable Long id){
      planetService.deletePlanet(id);
      return ResponseEntity.noContent().build();
    }

}
