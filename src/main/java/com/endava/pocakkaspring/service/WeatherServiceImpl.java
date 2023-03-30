package com.endava.pocakkaspring.service;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import com.endava.pocakkaspring.extension.SpringExtension;
import com.endava.pocakkaspring.model.Country;
import com.endava.pocakkaspring.model.WeatherInformation;
import com.endava.pocakkaspring.repository.WeatherRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class WeatherServiceImpl implements WeatherService {

    private final ActorSystem actorSystem;

    private final SpringExtension springExtension;

    private final WeatherRepository weatherRepository;

    public WeatherServiceImpl(
            ActorSystem actorSystem, SpringExtension springExtension, WeatherRepository weatherRepository) {
        this.actorSystem = actorSystem;
        this.springExtension = springExtension;
        this.weatherRepository = weatherRepository;
    }

    @Override
    public CompletableFuture<WeatherInformation> getWeather(Country country) {
        CompletableFuture<WeatherInformation> futureWeatherInformation = new CompletableFuture<>();
        ActorRef weatherProviderActor = actorSystem.actorOf(springExtension.props(
                "weatherProviderActor", futureWeatherInformation));
        weatherProviderActor.tell(country, null);
        return futureWeatherInformation;
    }

    @Override
    public WeatherInformation searchCountryWeather(Country country) {
        return weatherRepository.getWeatherInformationByCountry(country);
    }
}
