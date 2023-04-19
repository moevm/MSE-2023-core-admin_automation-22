package core.bgroup.zoom.service;

import com.yandex.disk.rest.exceptions.ServerIOException;
import core.bgroup.zoom.dto.RecordingPayload;
import core.bgroup.zoom.dto.WebhookValidationRequestPayload;
import core.bgroup.zoom.dto.WebhookValidationResponse;

import java.io.IOException;

public interface ZoomWebhookService {
    WebhookValidationResponse createValidationResponse(WebhookValidationRequestPayload payload);
    void uploadRecording(RecordingPayload payload) throws ServerIOException, IOException;
}
