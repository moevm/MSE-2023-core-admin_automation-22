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
                .startTime("2023-04-18T23: 10: 00")
                .duration("45")
                .timezone("Europe/Moscow")
                .agenda("test")
                .settings(meetingSettingsRequest)
                .build();

        return zoomMeetingService.createMeeting(meetingRequest);
    }
}
