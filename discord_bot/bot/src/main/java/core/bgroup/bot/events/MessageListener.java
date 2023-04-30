package core.bgroup.bot.events;


import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.MeetingSettingsRequest;
import core.bgroup.zoom.service.ZoomMeetingService;
import discord4j.core.object.entity.Message;
import org.json.JSONObject;
import reactor.core.publisher.Mono;


public abstract class MessageListener {
    private final ZoomMeetingService zoomMeetingService;

    public MessageListener(ZoomMeetingService zoomMeetingService) {
        this.zoomMeetingService = zoomMeetingService;
    }

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getContent().equalsIgnoreCase("!встреча"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage(processLink()))
                .then();
    }

    private String processLink() {
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

        String answer = zoomMeetingService.createMeeting(meetingRequest);
        JSONObject jsonObject = new JSONObject(answer);

        return jsonObject.get("join_url").toString();
    }
}
