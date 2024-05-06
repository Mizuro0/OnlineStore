package org.mizuro.aviatickets.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.mizuro.aviatickets.utils.Generator;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class AirFlight {
    private String id = Generator.generateId();
    @JsonProperty("flight_number")
    public String raceNumber;
    @JsonProperty("departure_at")
    private String departDate;
    @JsonProperty("origin_airport")
    private String origin;
    @JsonProperty("destination_airport")
    private String destination;
    @JsonProperty("return_at")
    private String returnDate;
    @JsonProperty("price")
    private int price;
    @JsonProperty("return_transfers")
    private int returnTransfers;
    @JsonProperty("transfers")
    private int transfers;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("duration_to")
    private int durationTo;
    @JsonProperty("duration_back")
    private int durationBack;

}
