package org.mizuro.aviatickets.config;

import org.modelmapper.ModelMapper;
import org.mizuro.aviatickets.models.AirFlight;
import org.springframework.stereotype.Component;


@Component
public class ModelMapperConfig {

    private final ModelMapper modelMapper;
    public ModelMapperConfig(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }
}

