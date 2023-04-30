package core.bgroup.zoom.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecordingRequest {
    @JsonProperty("id")
    private Long meetingId;

    @JsonProperty("recording_files")
    private List<RecordingFileRequest> recordingFiles;
}
