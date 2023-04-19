package core.bgroup.bot.config;

import core.bgroup.bot.events.EventListener;
import discord4j.core.DiscordClientBuilder;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.Event;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
@ComponentScan(basePackages = "core.bgroup.zoom")
public class BotConfiguration {

    private static final Logger log = LoggerFactory.getLogger( BotConfiguration.class );

    @Bean
    public <T extends Event> GatewayDiscordClient gatewayDiscordClient(List<EventListener<T>> eventListeners) {
        GatewayDiscordClient client = null;

        try {
            String token = "MTA4Mjk3Njg3NjEwNjEwMDc2Ng.Gy2Pmc.99CCCc3Dsk-8NiIrpIaBTpGkIWukcYMFx1e5ts";
            client = DiscordClientBuilder.create(token)
                    .build()
                    .login()
                    .block();

            for(EventListener<T> listener : eventListeners) {
                client.on(listener.getEventType())
                        .flatMap(listener::execute)
                        .onErrorResume(listener::handleError)
                        .subscribe();
            }
        }
        catch ( Exception exception ) {
            log.error( "Be sure to use a valid bot token!", exception );
        }

        return client;
    }

//    @Bean
//    public ZoomMeetingService zoomMeetingService(WebClient webClient) {
//        return new ZoomMeetingServiceImpl(webClient);
//    }
}
