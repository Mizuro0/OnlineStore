package org.mizuro.aviatickets.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightDataDTO {
    private List<AirFlightDTO> data;
    private String currency;
    private boolean success;
}
