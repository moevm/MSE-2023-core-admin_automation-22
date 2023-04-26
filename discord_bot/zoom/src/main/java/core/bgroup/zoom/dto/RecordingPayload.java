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
public class RecordingPayload {
    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("object")
    private RecordingRequest recording;
}
