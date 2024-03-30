package org.mizuro.aviatickets.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AirportDTO {
    private String iataCode;
    private String name;
    private String city;
    private String country;

}
