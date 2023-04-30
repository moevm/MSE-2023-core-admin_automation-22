package core.bgroup.bot.events;

import core.bgroup.bot.api.DiscordBotZoomMeetingService;
import core.bgroup.zoom.service.ZoomMeetingService;
import discord4j.core.event.domain.message.MessageUpdateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageUpdateListener extends MessageListener implements EventListener<MessageUpdateEvent> {

    public MessageUpdateListener(ZoomMeetingService zoomMeetingService, DiscordBotZoomMeetingService discordBotZoomMeetingService) {
        super(zoomMeetingService, discordBotZoomMeetingService);
    }

    @Override
    public Class<MessageUpdateEvent> getEventType() {
        return MessageUpdateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageUpdateEvent event) {
        return Mono.just(event)
                .filter(MessageUpdateEvent::isContentChanged)
                .flatMap(MessageUpdateEvent::getMessage)
                .flatMap(super::processCommand);
    }
}
