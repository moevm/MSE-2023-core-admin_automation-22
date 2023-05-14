package core.bgroup.bot.events;


import core.bgroup.bot.api.DiscordBotZoomMeetingService;
import core.bgroup.bot.impl.model.DiscordBotZoomMeetingEntity;
import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.MeetingSettingsRequest;
import core.bgroup.zoom.service.ZoomMeetingService;
import discord4j.core.object.entity.Message;
import discord4j.core.object.entity.User;
import org.json.JSONObject;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Objects;


public abstract class MessageListener {
    private final ZoomMeetingService zoomMeetingService;

    private final DiscordBotZoomMeetingService discordBotZoomMeetingService;

    public MessageListener(ZoomMeetingService zoomMeetingService, DiscordBotZoomMeetingService discordBotZoomMeetingService) {
        this.zoomMeetingService = zoomMeetingService;
        this.discordBotZoomMeetingService = discordBotZoomMeetingService;
    }

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getContent().equalsIgnoreCase("!встреча"))
                .flatMap(Message::getChannel)
                .publishOn(Schedulers.boundedElastic())
                .flatMap(channel -> {
                    JSONObject jsonObject = new JSONObject(processData());
                    DiscordBotZoomMeetingEntity entity = discordBotZoomMeetingService.create(
                            DiscordBotZoomMeetingEntity.builder()
                            .userId(eventMessage.getAuthor().get().getId().asLong())
                            .chanelId(eventMessage.getChannelId().asLong())
                            .meetingId((Long) jsonObject.get("id"))
                            .startUrl(jsonObject.get("start_url").toString())
                            .joinUrl(jsonObject.get("join_url").toString())
                            .build());
                    User user = eventMessage.getAuthor().get();
                    Objects.requireNonNull(user.getPrivateChannel().block())
                            .createMessage("Ссылка для начала конференции: " + entity.getStartUrl()).block();
                    return channel.createMessage("Ссылка для конференции: " + entity.getJoinUrl());
                })
                .then();
    }

    private String processData() {
        //TODO remake this example implementation
        MeetingSettingsRequest meetingSettingsRequest = MeetingSettingsRequest.builder()
                .hostVideo("true")
                .participantVideo("true")
                .joinBeforeHost("false")
                .muteUponEntry("false")
                .autoRecording("true")
                .build();
        MeetingRequest meetingRequest = MeetingRequest.builder()
                .topic("test")
                .type("2")
                .startTime("2023-04-20T23: 10: 00") // нужно подгадать
                .duration("45")
                .timezone("Europe/Moscow")
                .agenda("test")
                .settings(meetingSettingsRequest)
                .build();

        return zoomMeetingService.createMeeting(meetingRequest);
    }
}
