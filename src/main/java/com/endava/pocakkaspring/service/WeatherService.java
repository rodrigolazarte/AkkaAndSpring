package com.endava.pocakkaspring.service;

import com.endava.pocakkaspring.model.Country;
import com.endava.pocakkaspring.model.WeatherInformation;

import java.util.concurrent.CompletableFuture;

public interface WeatherService {

    CompletableFuture<WeatherInformation> getWeather(Country country);
    WeatherInformation searchCountryWeather(Country country);
}
