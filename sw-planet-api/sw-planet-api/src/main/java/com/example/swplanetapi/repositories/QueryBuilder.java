package com.example.swplanetapi.repositories;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;

import com.example.swplanetapi.domains.Planet;

public class QueryBuilder {
    
    public static Example<Planet> makeQuery(Planet planet){
        ExampleMatcher exampleMatcher = ExampleMatcher.matchingAll().withIgnoreCase().withIncludeNullValues();
        return Example.of(planet, exampleMatcher);
    }

}
