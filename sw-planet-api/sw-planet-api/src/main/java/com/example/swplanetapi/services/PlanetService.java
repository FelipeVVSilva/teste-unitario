package com.example.swplanetapi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import com.example.swplanetapi.domains.Planet;
import com.example.swplanetapi.repositories.PlanetRepository;
import com.example.swplanetapi.repositories.QueryBuilder;



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

    public Optional<Planet> findPlanetByName(String name){
        return planetRepository.findPlanetByName(name);
    }

    public List<Planet> findAllPlanets(String terrain, String climate){
        Example<Planet> query = QueryBuilder.makeQuery(new Planet(climate, terrain));
        return planetRepository.findAll(query);
    }

    public List<Planet> list(String climate, String terrain) {
        return null;
    }

    public void deletePlanet(Long id){
        planetRepository.deleteById(id);
    }
}
