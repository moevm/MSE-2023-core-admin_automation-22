package core.bgroup.zoom.service;

import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.ZoomAccessTokenResponse;

public interface ZoomMeetingService {

    String createMeeting(MeetingRequest meetingRequest);
    ZoomAccessTokenResponse getAccessTokenResponse();

    void deleteRecording(long meetingId);
}
