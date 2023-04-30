package core.bgroup.bot.api;

import core.bgroup.bot.impl.exceptions.MeetingNotFoundException;
import core.bgroup.bot.impl.model.DiscordBotZoomMeetingEntity;

public interface DiscordBotZoomMeetingService {
    DiscordBotZoomMeetingEntity create(DiscordBotZoomMeetingEntity discordBotZoomMeeting);

    void deleteById(Long id);

    DiscordBotZoomMeetingEntity getById(Long id) throws MeetingNotFoundException;
}
