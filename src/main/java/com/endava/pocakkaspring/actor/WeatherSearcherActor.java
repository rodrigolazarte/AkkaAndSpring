package com.endava.pocakkaspring.actor;

import akka.actor.UntypedAbstractActor;
import com.endava.pocakkaspring.model.Country;
import com.endava.pocakkaspring.model.WeatherInformation;
import com.endava.pocakkaspring.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("weatherSearcherActor")
@Scope("prototype")
public class WeatherSearcherActor extends UntypedAbstractActor {

    @Autowired
    private WeatherService weatherService;

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Country country) {
            WeatherInformation weatherInformation = weatherService.searchCountryWeather(country);
            getSender().tell(weatherInformation, getSelf());
        } else {
            unhandled(message);
        }

        getContext().stop(self());
    }
}
