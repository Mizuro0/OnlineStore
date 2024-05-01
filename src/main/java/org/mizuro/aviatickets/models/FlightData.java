package org.mizuro.aviatickets.models;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("data")
    private List<AirFlight> data;
    @JsonProperty("currency")
    private String currency;
    @JsonProperty("success")
    private boolean success;
}
