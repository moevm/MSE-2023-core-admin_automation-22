package core.bgroup.zoom.controller;

import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.MeetingSettingsRequest;
import core.bgroup.zoom.service.ZoomMeetingService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/test")
public class TestController {

    private final ZoomMeetingService zoomMeetingService;

    public TestController(ZoomMeetingService zoomMeetingService) {
        this.zoomMeetingService = zoomMeetingService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String test() {
        String hostEmail = "tucha.989@gmail.com";
        MeetingSettingsRequest meetingSettingsRequest = MeetingSettingsRequest.builder()
                .hostVideo("true")
                .participantVideo("true")
                .joinBeforeHost("false")
                .muteUponEntry("false")
                .autoRecording("true")
                .alternativeHosts("nsstrannikova@mail.ru")
                .build();
        MeetingRequest meetingRequest = MeetingRequest.builder()
                .topic("test")
                .type("2")
                .startTime("2023-03-26T10: 16: 23")
                .duration("45")
                .timezone("Europe/Moscow")
                .agenda("test")
                .settings(meetingSettingsRequest)
                .build();

        return zoomMeetingService.createMeeting(meetingRequest);
    }
}
