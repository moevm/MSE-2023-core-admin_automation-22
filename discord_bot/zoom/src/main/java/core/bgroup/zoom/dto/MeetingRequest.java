package core.bgroup.zoom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeetingRequest {
    @JsonProperty("topic")
    private String topic;

    @JsonProperty("type")
    private String type;

    @JsonProperty("start_time")
    private String startTime;

    @JsonProperty("duration")
    private String duration;

    @JsonProperty("timezone")
    private String timezone;

    @JsonProperty("agenda")
    private String agenda;

    @JsonProperty("settings")
    private MeetingSettingsRequest settings;
}
