package core.bgroup.bot.impl.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "discord_bot_zoom_meeting")
public class DiscordBotZoomMeetingEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Long chanelId;

    private String meetingUUID;

    @Column(name = "start_url", length = 1024)
    private String startUrl;

    @Column(name = "join_url", length = 1024)
    private String joinUrl;

    @Column(name = "recording_url", length = 1024)
    private String recordingUrl;

}
