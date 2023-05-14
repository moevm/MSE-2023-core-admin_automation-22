package core.bgroup.bot.impl.repositories;

import core.bgroup.bot.impl.model.DiscordBotZoomMeetingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DiscordBotZoomMeetingRepository extends JpaRepository<DiscordBotZoomMeetingEntity, Long> {
    Optional<DiscordBotZoomMeetingEntity> findByMeetingId(Long id);
}
