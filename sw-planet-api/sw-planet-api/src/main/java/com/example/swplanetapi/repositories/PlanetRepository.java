package com.example.swplanetapi.repositories;

import java.util.List;
import java.util.Optional;


import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import com.example.swplanetapi.domains.Planet;

@Repository
public interface PlanetRepository extends JpaRepository<Planet, Long>{
    
    Optional<Planet> findPlanetByName(String name);

    @Override
    <S extends Planet> List<S> findAll(Example<S> example);
}
