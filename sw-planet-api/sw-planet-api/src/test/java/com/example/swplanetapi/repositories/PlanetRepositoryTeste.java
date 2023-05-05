package com.example.swplanetapi.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import com.example.swplanetapi.domains.Planet;

import static com.example.swplanetapi.common.PlanetConstants.PLANET;

@DataJpaTest
public class PlanetRepositoryTeste {
    
    @Autowired
    private PlanetRepository planetRepository;

    @Autowired
    private TestEntityManager testEntityManager;

    @AfterEach
    public void setup(){
        PLANET.setId(null);
    } 

    @Test
    public void createPlanet_WithValidData_ReturnsPlanet(){

        Planet planet = planetRepository.save(PLANET);

        Planet sut = testEntityManager.find(Planet.class, planet.getId());

        assertThat(sut).isNotNull();
        assertThat(sut.getName()).isEqualTo(planet.getName());
        assertThat(sut.getClimate()).isEqualTo(planet.getClimate());
        assertThat(sut.getTerrain()).isEqualTo(planet.getTerrain());
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){

        Planet emptyPlanet = new Planet();
        Planet invalidPlanet = new Planet(null, "", "", "");

        assertThatThrownBy(() -> planetRepository.save(emptyPlanet)).isInstanceOf(RuntimeException.class);
        assertThatThrownBy(() -> planetRepository.save(invalidPlanet)).isInstanceOf(RuntimeException.class);;
    }

    @Test
    public void createPlanet_WithExistingName_ThrowwsException(){

        Planet planet = testEntityManager.persistFlushFind(PLANET);
        testEntityManager.detach(planet);
        planet.setId(null);

        assertThatThrownBy(() -> planetRepository.save(planet)).isInstanceOf(RuntimeException.class);

    }

    @Test
    public void getPlane_ByExistingId_ReturnsPlanet(){

        Planet planet = testEntityManager.persistFlushFind(PLANET);
        Optional<Planet> planetOpt = planetRepository.findById(planet.getId());

        assertThat(planetOpt).isNotNull();
        assertThat(planetOpt.get()).isEqualTo(planet);

    }

    @Test
    public void getPlane_ByUnexistingId_NotFound(){

        Optional<Planet> planetOpt = planetRepository.findById(1L);

        assertThat(planetOpt).isEmpty();

    }

    @Test
    public void getPlane_ByExistingName_ReturnsPlanet(){

        Planet planet = testEntityManager.persistAndFlush(PLANET);
        Optional<Planet> planetOPt = planetRepository.findById(1L);

        assertThat(planetOPt).isNotEmpty();
        assertThat(planetOPt.get()).isEqualTo(planet);

    }

    @Test
    public void getPlane_ByUnexistingName_NotFound(){

        Optional<Planet> planetOpt = planetRepository.findPlanetByName("name");

        assertThat(planetOpt).isEmpty();

    }

}
