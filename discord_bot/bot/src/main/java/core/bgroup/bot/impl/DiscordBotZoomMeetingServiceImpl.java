package core.bgroup.bot.impl;

import core.bgroup.bot.api.DiscordBotZoomMeetingService;
import core.bgroup.bot.impl.exceptions.MeetingNotFoundException;
import core.bgroup.bot.impl.model.DiscordBotZoomMeetingEntity;
import core.bgroup.bot.impl.repositories.DiscordBotZoomMeetingRepository;
import org.springframework.stereotype.Service;

@Service
public class DiscordBotZoomMeetingServiceImpl implements DiscordBotZoomMeetingService {

    private final DiscordBotZoomMeetingRepository discordBotZoomMeetingRepository;

    public DiscordBotZoomMeetingServiceImpl(DiscordBotZoomMeetingRepository discordBotZoomMeetingRepository) {
        this.discordBotZoomMeetingRepository = discordBotZoomMeetingRepository;
    }

    @Override
    public DiscordBotZoomMeetingEntity create(DiscordBotZoomMeetingEntity discordBotZoomMeeting) {
        return discordBotZoomMeetingRepository.save(discordBotZoomMeeting);
    }

    @Override
    public void deleteById(Long id) {
        discordBotZoomMeetingRepository.deleteById(id);
    }

    @Override
    public DiscordBotZoomMeetingEntity getById(Long id) throws MeetingNotFoundException {
        return discordBotZoomMeetingRepository
                .findById(id)
                .orElseThrow(() -> new MeetingNotFoundException(id.toString()));
    }
}
