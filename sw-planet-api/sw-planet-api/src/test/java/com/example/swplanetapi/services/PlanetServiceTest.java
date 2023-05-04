package com.example.swplanetapi.services;

import static com.example.swplanetapi.common.PlanetConstants.PLANET;
import static com.example.swplanetapi.common.PlanetConstants.INVALID_PLANET;
import static com.example.swplanetapi.common.PlanetConstants.EXISTING_ID;
import static com.example.swplanetapi.common.PlanetConstants.NON_EXISTING_ID;
import static com.example.swplanetapi.common.PlanetConstants.NON_EXISTING_NAME;
import static com.example.swplanetapi.common.PlanetConstants.EXISTING_NAME;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.swplanetapi.domains.Planet;
import com.example.swplanetapi.repositories.PlanetRepository;

@ExtendWith(MockitoExtension.class)
public class PlanetServiceTest {
    
    @Mock
    private PlanetRepository planetRepository;

    @InjectMocks
    private PlanetService planetService;

    @Test
    public void createPlanet_WithValidData_Returns201AndPlanet(){

        when(planetRepository.save(PLANET)).thenReturn(PLANET);

        Planet sut = planetService.create(PLANET);

        assertThat(sut).isEqualTo(PLANET);
    }

    @Test
    public void createPlanet_WithInvalidData_ThrowsException(){

        when(planetRepository.save(INVALID_PLANET)).thenThrow(RuntimeException.class);

        assertThatThrownBy(() -> planetService.create(INVALID_PLANET)).isInstanceOf(RuntimeException.class);
    }

    @Test
    public void findPlanetById_ByExitingId_Returns200AndPlanet(){

        when(planetRepository.findById(EXISTING_ID)).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.findPlanetById(EXISTING_ID);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void findPlanetById_ByUnexistingId_Returns404AndEmpty(){

        when(planetRepository.findById(NON_EXISTING_ID)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.findPlanetById(NON_EXISTING_ID);

        assertThat(sut).isEmpty();
    }

    @Test
    public void findPlanetById_ByExitingName_Returns404AndEmpty(){

        when(planetRepository.findPlanetByName(EXISTING_NAME)).thenReturn(Optional.of(PLANET));

        Optional<Planet> sut = planetService.findPlanetByName(EXISTING_NAME);

        assertThat(sut).isNotEmpty();
        assertThat(sut.get()).isEqualTo(PLANET);
    }

    @Test
    public void findPlanetById_ByUnexistingName_Returns404AndEmpty(){

        when(planetRepository.findPlanetByName(NON_EXISTING_NAME)).thenReturn(Optional.empty());

        Optional<Planet> sut = planetService.findPlanetByName(NON_EXISTING_NAME);

        assertThat(sut).isEmpty();
    }

    @Test
    public void removePlanet_WithExistingId_doesNotThrowAnyException(){

        assertThatCode(() -> planetRepository.deleteById(EXISTING_ID)).doesNotThrowAnyException();
    }

    @Test
    public void removePlanet_WithUnexistingId_throwAnyException(){
        
        doThrow(new RuntimeException()).when(planetRepository).deleteById(NON_EXISTING_ID);

        assertThatThrownBy(() -> planetService.deletePlanet(NON_EXISTING_ID)).isInstanceOf(RuntimeException.class);

    }
}
