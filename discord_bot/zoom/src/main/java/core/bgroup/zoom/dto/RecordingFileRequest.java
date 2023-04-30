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
public class RecordingFileRequest {
    @JsonProperty("file_type")
    private String fileType;
    @JsonProperty("file_extension")
    private String fileExtension;
    @JsonProperty("file_size")
    private Long fileSize;
    @JsonProperty("download_url")
    private String downloadUrl;
    private String status;
    @JsonProperty("recording_type")
    private String recordingType;
}
