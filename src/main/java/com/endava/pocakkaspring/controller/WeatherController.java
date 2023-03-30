package com.endava.pocakkaspring.controller;

import com.endava.pocakkaspring.model.Country;
import com.endava.pocakkaspring.model.WeatherInformation;
import com.endava.pocakkaspring.service.WeatherService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.CompletableFuture;

@RestController
public class WeatherController {

    private static final Long DEFERRED_RESULT_TIMEOUT = 1000L;
    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @GetMapping("/weather")
    public DeferredResult<WeatherInformation> getWeatherInformation(@RequestParam Country country) {
        DeferredResult<WeatherInformation> deferredResult = new DeferredResult<>(DEFERRED_RESULT_TIMEOUT);
        CompletableFuture<WeatherInformation> completableFuture = weatherService.getWeather(country);
        completableFuture.whenComplete((result, error) -> {
            if (error != null) {
                deferredResult.setErrorResult(error);
            } else {
                deferredResult.setResult(result);
            }
        });

        return deferredResult;
    }
}
