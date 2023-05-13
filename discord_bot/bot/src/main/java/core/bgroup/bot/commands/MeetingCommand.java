package core.bgroup.bot.commands;

import core.bgroup.bot.api.DiscordBotZoomMeetingService;
import core.bgroup.bot.impl.model.DiscordBotZoomMeetingEntity;
import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.MeetingSettingsRequest;
import core.bgroup.zoom.service.ZoomMeetingService;
import discord4j.core.event.domain.interaction.ChatInputInteractionEvent;
import discord4j.core.object.command.ApplicationCommandInteractionOption;
import discord4j.core.object.command.ApplicationCommandInteractionOptionValue;
import discord4j.core.object.entity.User;
import org.json.JSONObject;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class MeetingCommand implements SlashCommand {

    @Override
    public String getName() {
        return "meeting";
    }

    private final ZoomMeetingService zoomMeetingService;

    private final DiscordBotZoomMeetingService discordBotZoomMeetingService;

    public MeetingCommand(ZoomMeetingService zoomMeetingService, DiscordBotZoomMeetingService discordBotZoomMeetingService) {
        this.zoomMeetingService = zoomMeetingService;
        this.discordBotZoomMeetingService = discordBotZoomMeetingService;
    }

    @Override
    public Mono<Void> handle(ChatInputInteractionEvent event) {
        String meetingName = getOptionValueByName(event, "meeting-name");

        String meetingTime = getOptionValueByName(event, "meeting-time");

        String hostName = getOptionValueByName(event, "host-name");

        String tag = getOptionValueByName(event, "tag");

        JSONObject jsonObject = new JSONObject(processData(meetingTime, meetingName));
        DiscordBotZoomMeetingEntity entity = discordBotZoomMeetingService.create(
                DiscordBotZoomMeetingEntity.builder()
                        .userId(event.getInteraction().getUser().getId().asLong())
                        .chanelId(event.getInteraction().getChannelId().asLong())
                        .meetingUUID(jsonObject.get("uuid").toString())
                        .startUrl(jsonObject.get("start_url").toString())
                        .joinUrl(jsonObject.get("join_url").toString())
                        .build());
        User user = event.getInteraction().getUser();
        Objects.requireNonNull(user.getPrivateChannel().block())
                .createMessage("Ссылка для начала конференции: " + entity.getStartUrl()).block();

        return event.reply()
                .withEphemeral(false)
                .withContent("Ссылка для конференции: " + entity.getJoinUrl());
    }

    private String processData(String meetingTime, String meetingName) {
        //TODO remake this example implementation
        MeetingSettingsRequest meetingSettingsRequest = MeetingSettingsRequest.builder()
                .hostVideo("true")
                .participantVideo("true")
                .joinBeforeHost("false")
                .muteUponEntry("false")
                .autoRecording("true")
                .build();
        MeetingRequest meetingRequest = MeetingRequest.builder()
                .topic(meetingName)
                .type("2")
                .startTime(meetingTime) // в формате UTC 2023-04-20T19:30:00Z
                .duration("45")
                .timezone("Europe/Moscow")
                .agenda("test")
                .settings(meetingSettingsRequest)
                .build();

        return zoomMeetingService.createMeeting(meetingRequest);
    }

    private String getOptionValueByName(ChatInputInteractionEvent event, String name) {
        return event.getOption(name)
                .flatMap(ApplicationCommandInteractionOption::getValue)
                .map(ApplicationCommandInteractionOptionValue::asString)
                .get();
    }
}
