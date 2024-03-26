package org.mizuro.aviatickets.config;

import org.modelmapper.ModelMapper;
import org.mizuro.aviatickets.dto.AirFlightDTO;
import org.mizuro.aviatickets.entity.AirFlightEntity;
import org.springframework.stereotype.Component;


@Component
public class ModelMapperConfig {

    private final ModelMapper modelMapper;
    public ModelMapperConfig(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        configureMapper();
    }

    private void configureMapper() {
        modelMapper.createTypeMap(AirFlightDTO.class, AirFlightEntity.class)
                .addMapping(AirFlightDTO::getDepartDate, AirFlightEntity::setDepartureDate)
                .addMapping(AirFlightDTO::getReturnDate, AirFlightEntity::setReturnDate)
                .addMapping(AirFlightDTO::getTripClass, AirFlightEntity::setTripClass);
    }
}

