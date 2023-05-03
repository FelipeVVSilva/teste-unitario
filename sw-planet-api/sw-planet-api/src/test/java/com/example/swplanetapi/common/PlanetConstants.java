package com.example.swplanetapi.common;

import com.example.swplanetapi.domains.Planet;

public class PlanetConstants {
    
    public static final Planet PLANET = new Planet(null, "name", "climate", "terrain");
    public static final Planet INVALID_PLANET = new Planet(null, "", "","");

}
