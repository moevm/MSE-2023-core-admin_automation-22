package core.bgroup.zoom.service.impl;

import com.yandex.disk.rest.exceptions.ServerIOException;
import core.bgroup.yandex.handler.impl.ConsoleOperationEventHandler;
import core.bgroup.yandex.service.YandexService;
import core.bgroup.zoom.dto.RecordingPayload;
import core.bgroup.zoom.dto.WebhookValidationRequestPayload;
import core.bgroup.zoom.dto.WebhookValidationResponse;
import core.bgroup.zoom.service.ZoomMeetingService;
import core.bgroup.zoom.service.ZoomWebhookService;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ZoomWebhookServiceImpl implements ZoomWebhookService {
    private final String zoomToken;
    private final YandexService yandexService;
    private final ZoomMeetingService zoomMeetingService;

    public ZoomWebhookServiceImpl(@Value("${zoom.token}") String zoomToken, YandexService yandexService, ZoomMeetingService zoomMeetingService) {
        this.zoomToken = zoomToken;
        this.yandexService = yandexService;
        this.zoomMeetingService = zoomMeetingService;
    }

    @Override
    public WebhookValidationResponse createValidationResponse(WebhookValidationRequestPayload payload) {
        String plainToken = payload.getPlainToken();
        return WebhookValidationResponse.builder()
                .plainToken(plainToken)
                .encryptedToken(new HmacUtils(HmacAlgorithms.HMAC_SHA_256, zoomToken).hmacHex(plainToken))
                .build();
    }

    @Override
    public void uploadRecording(RecordingPayload payload) throws ServerIOException, IOException {
        String accessToken = zoomMeetingService.getAccessTokenResponse().getAccessToken();
        String downloadUrl = payload.getRecording().getRecordingFiles().stream()
                .filter(recording -> recording.getFileType().equals("MP4") &&
                        recording.getRecordingType().equals("shared_screen_with_speaker_view") &&
                        recording.getStatus().equals("completed"))
                .findFirst().orElseThrow().getDownloadUrl();
        downloadUrl += "?access_token=" + accessToken;
        yandexService.uploadFileFromUrl(downloadUrl, payload.getRecording().getMeetingId() + "_recording.mp4", new ConsoleOperationEventHandler());
    }
}
