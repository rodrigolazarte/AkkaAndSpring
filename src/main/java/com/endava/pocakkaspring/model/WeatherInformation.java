package com.endava.pocakkaspring.model;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class WeatherInformation {
    private Country countryName;
    private String cityName;
    private double temperature;
    private double humidity;
}
