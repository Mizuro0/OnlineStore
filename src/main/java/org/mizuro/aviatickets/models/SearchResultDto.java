package org.mizuro.aviatickets.models;

public class SearchResultDto {
    private String city;
    private String region;
    private String country;

    // Геттеры и сеттеры
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
