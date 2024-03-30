package org.mizuro.aviatickets.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class AirFlight {
    @JsonProperty("depart_date")
    private String departDate;
    private String origin;
    private String destination;
    private String gate;
    @JsonProperty("return_date")
    private String returnDate;
    @JsonProperty("found_at")
    private String foundAt;
    @JsonProperty("trip_class")
    private int tripClass;
    private int value;
    @JsonProperty("number_of_changes")
    private int numberOfChanges;
    private int duration;
    private int distance;
    @JsonProperty("show_to_affiliates")
    private boolean showToAffiliates;
    private boolean actual;
}
