package core.bgroup.zoom.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yandex.disk.rest.exceptions.ServerIOException;
import core.bgroup.zoom.dto.RecordingPayload;
import core.bgroup.zoom.dto.WebhookRequest;
import core.bgroup.zoom.dto.WebhookValidationRequestPayload;
import core.bgroup.zoom.dto.WebhookValidationResponse;
import core.bgroup.zoom.service.ZoomWebhookService;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/wh")
public class WebhookController {
    private final ObjectMapper mapper;
    private final ZoomWebhookService zoomWebhookService;

    public WebhookController(ObjectMapper mapper, ZoomWebhookService zoomWebhookService) {
        this.mapper = mapper;
        this.zoomWebhookService = zoomWebhookService;
    }

    @PostMapping
    public WebhookValidationResponse processEvent(@RequestBody WebhookRequest request) throws IOException, ServerIOException {
        switch (request.getEvent()) {
            case "endpoint.url_validation" -> {
                WebhookValidationRequestPayload validationPayload = mapper.treeToValue(request.getPayload(), WebhookValidationRequestPayload.class);
                return zoomWebhookService.createValidationResponse(validationPayload);
            }
            case "recording.completed" -> {
                RecordingPayload recordingPayload = mapper.treeToValue(request.getPayload(), RecordingPayload.class);
                zoomWebhookService.processRecording(recordingPayload, request.getDownloadToken());
            }
        }
        return null;
    }
}
