package core.bgroup.bot.impl.repositories;

import core.bgroup.bot.impl.model.DiscordBotZoomMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DiscordBotZoomMeetingRepository extends JpaRepository<DiscordBotZoomMeetingEntity, Long> {
}
