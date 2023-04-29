package core.bgroup.bot.events;

import core.bgroup.bot.api.DiscordBotZoomMeetingService;
import core.bgroup.zoom.service.ZoomMeetingService;
import discord4j.core.event.domain.message.MessageCreateEvent;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class MessageCreationListener extends MessageListener implements EventListener<MessageCreateEvent> {

    public MessageCreationListener(ZoomMeetingService zoomMeetingService, DiscordBotZoomMeetingService discordBotZoomMeetingService) {
        super(zoomMeetingService, discordBotZoomMeetingService);
    }

    @Override
    public Class<MessageCreateEvent> getEventType() {
        return MessageCreateEvent.class;
    }

    @Override
    public Mono<Void> execute(MessageCreateEvent event) {
        return processCommand(event.getMessage());
    }
}
