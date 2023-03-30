package com.endava.pocakkaspring.repository;

import com.endava.pocakkaspring.model.Country;
import com.endava.pocakkaspring.model.WeatherInformation;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class WeatherRepository {
    private Map<Country, WeatherInformation> weatherInformationMap;

    public WeatherRepository() {
        weatherInformationMap = new HashMap<>();
        weatherInformationMap.put(Country.AR, new WeatherInformation(
                Country.AR,
                "Buenos Aires",
                30.5,
                71
        ));
        weatherInformationMap.put(Country.BR, new WeatherInformation(
                Country.BR,
                "Brasilia",
                32.7,
                50
        ));
        weatherInformationMap.put(Country.UY, new WeatherInformation(
                Country.UY,
                "Montevideo",
                31.0,
                60
        ));
        weatherInformationMap.put(Country.US, new WeatherInformation(
                Country.US,
                "Washington",
                10,
                15
        ));
    }

    public WeatherInformation getWeatherInformationByCountry(Country country) {
        return weatherInformationMap.get(country);
    }
}
