package com.example.swplanetapi.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.swplanetapi.domains.Planet;
import com.example.swplanetapi.repositories.PlanetRepository;



@Service
public class PlanetService {
    
    private PlanetRepository planetRepository;

    public PlanetService(PlanetRepository planetRepository){
        this.planetRepository = planetRepository;
    }

    public Planet create(Planet planet){
        //Planet newPlanet = new Planet(null, planet.getName(), planet.getClimate(), planet.getTerrain());
        planet = planetRepository.save(planet);
        return planet;
    }

    public Optional<Planet> findPlanetById(Long id){
        return planetRepository.findById(id);
    }
}
