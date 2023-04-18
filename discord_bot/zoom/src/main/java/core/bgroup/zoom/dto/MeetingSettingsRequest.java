package core.bgroup.zoom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingSettingsRequest {

    @JsonProperty("host_video")
    private String hostVideo;

    @JsonProperty("participant_video")
    private String participantVideo;

    @JsonProperty("join_before_host")
    private String joinBeforeHost;

    @JsonProperty("mute_upon_entry")
    private String muteUponEntry;

    @JsonProperty("auto_recording")
    private String autoRecording;

    @JsonProperty("alternative_hosts")
    private String alternativeHosts;

}
