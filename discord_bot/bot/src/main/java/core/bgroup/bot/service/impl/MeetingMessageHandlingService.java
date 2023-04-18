package core.bgroup.bot.service.impl;

import core.bgroup.bot.service.MessageHandlingService;
import core.bgroup.bot.service.MessageWritingService;
import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.MeetingSettingsRequest;
import core.bgroup.zoom.service.ZoomMeetingService;
import discord4j.core.DiscordClient;
import discord4j.discordjson.json.MessageData;
import org.springframework.stereotype.Service;

@Service
public class MeetingMessageHandlingService implements MessageHandlingService {
    private final DiscordClient discordClient;
    private final MessageWritingService writingService;
    private final ZoomMeetingService zoomMeetingService;

    public MeetingMessageHandlingService(
            DiscordClient discordClient,
            MessageWritingService writingService,
            ZoomMeetingService zoomMeetingService) {
        this.discordClient = discordClient;
        this.writingService = writingService;
        this.zoomMeetingService = zoomMeetingService;
    }

    @Override
    public void handleMessage(MessageData message) {

        System.out.println(message.content());

        if (isValidMeetingMessage(message)) {
            answerMeetingMessage(message);
        }
    }

    private boolean isValidMeetingMessage(MessageData message) {
        //TODO remake this example implementation
        return message.content().contains("встреча")
                && message.author().id().asLong() != discordClient.getApplicationId().block();
    }

    private void answerMeetingMessage(MessageData message) {
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
                .startTime("2023-04-18T23: 10: 00") // нужно подгадать
                .duration("45")
                .timezone("Europe/Moscow")
                .agenda("test")
                .settings(meetingSettingsRequest)
                .build();
        String answer = zoomMeetingService.createMeeting(meetingRequest);
        writingService.writeMessage(answer);
    }
}
