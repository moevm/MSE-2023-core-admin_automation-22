package core.bgroup.zoom.service;

import core.bgroup.zoom.dto.RecordingPayload;
import core.bgroup.zoom.dto.WebhookValidationRequestPayload;
import core.bgroup.zoom.dto.WebhookValidationResponse;


public interface ZoomWebhookService {
    WebhookValidationResponse createValidationResponse(WebhookValidationRequestPayload payload);
    void processRecording(RecordingPayload payload, String downloadToken);
}
