package org.mizuro.aviatickets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FlightData {
    private List<AirFlight> data;
    private String currency;
    private boolean success;
}
