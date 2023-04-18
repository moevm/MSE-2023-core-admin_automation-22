package core.bgroup.zoom.service;

import core.bgroup.zoom.dto.MeetingRequest;

public interface ZoomMeetingService {

    String createMeeting(MeetingRequest meetingRequest);

}
