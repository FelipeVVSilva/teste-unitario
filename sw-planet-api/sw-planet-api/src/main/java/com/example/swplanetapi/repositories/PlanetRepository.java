package com.example.swplanetapi.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.swplanetapi.domains.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long>{
    
    Optional<Planet> findPlanetByName(String name);

}
