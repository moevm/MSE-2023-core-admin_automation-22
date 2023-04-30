package core.bgroup.zoom.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookRequest {
    private ObjectNode payload;
    @JsonProperty("event_ts")
    private Long eventTs;
    private String event;

    @JsonProperty("download_token")
    private String downloadToken;
}
