package com.endava.pocakkaspring.actor;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.UntypedAbstractActor;
import com.endava.pocakkaspring.extension.SpringExtension;
import com.endava.pocakkaspring.model.Country;
import com.endava.pocakkaspring.model.WeatherInformation;
import com.endava.pocakkaspring.service.WeatherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component("weatherProviderActor")
@Scope("prototype")
public class WeatherProviderActor extends UntypedAbstractActor {
    @Autowired
    private ActorSystem actorSystem;
    @Autowired
    private SpringExtension springExtension;

    private final CompletableFuture<WeatherInformation> completableFuture;

    public WeatherProviderActor(CompletableFuture<WeatherInformation> completableFuture) {
        this.completableFuture = completableFuture;
    }

    @Override
    public void onReceive(Object message) throws Throwable {
        if (message instanceof Country country) {
            searchCountryWeather(country);
        } else if (message instanceof WeatherInformation weatherInformation){
            completableFuture.complete(weatherInformation);
            getContext().stop(self());
        } else {
            unhandled(message);
        }
    }

    private void searchCountryWeather(Country country) {
        ActorRef weatherSearcher = actorSystem.actorOf(springExtension.props(
                "weatherSearcherActor"));
        weatherSearcher.tell(country, getSelf());
    }
}
