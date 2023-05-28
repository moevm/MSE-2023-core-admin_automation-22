package core.bgroup.bot.impl;

import com.yandex.disk.rest.exceptions.ServerIOException;
import core.bgroup.bot.impl.model.DiscordBotZoomMeetingEntity;
import core.bgroup.bot.impl.repositories.DiscordBotZoomMeetingRepository;
import core.bgroup.bot.service.DiscordService;
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
import java.util.Optional;

@Service
public class ZoomWebhookServiceImpl implements ZoomWebhookService {
    private final String zoomToken;
    private final YandexService yandexService;
    private final DiscordBotZoomMeetingRepository meetingRepository;
    private final DiscordService discordService;
    private final ZoomMeetingService zoomMeetingService;

    public ZoomWebhookServiceImpl(@Value("${zoom.token}") String zoomToken, YandexService yandexService, DiscordBotZoomMeetingRepository meetingRepository, DiscordService discordService, ZoomMeetingService zoomMeetingService) {
        this.zoomToken = zoomToken;
        this.yandexService = yandexService;
        this.meetingRepository = meetingRepository;
        this.discordService = discordService;
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
    public void processRecording(RecordingPayload payload, String downloadToken) {
        Long meetingId = payload.getRecording().getMeetingId();

        Optional<DiscordBotZoomMeetingEntity> optionalMeeting = meetingRepository.findByMeetingId(meetingId);
        if (optionalMeeting.isEmpty()) return;

        DiscordBotZoomMeetingEntity meeting = optionalMeeting.get();
        if (meeting.getRecordingUrl() != null) return;

        payload.getRecording().getRecordingFiles().stream()
                .filter(recording -> recording.getFileType().equals("MP4") &&
                        recording.getRecordingType().equals("shared_screen_with_speaker_view") &&
                        recording.getStatus().equals("completed"))
                .findFirst()
                .ifPresent(request -> {
                    String downloadUrl = request.getDownloadUrl() + "?access_token=" + downloadToken;
                    String path = meetingId + "_recording.mp4";
                    try {
                        yandexService.uploadFileFromUrl(downloadUrl, path, link -> {
                            meeting.setRecordingUrl(link);
                            meetingRepository.save(meeting);
                            sendRecordingReadyMessage(meeting, link);
                            zoomMeetingService.deleteRecording(meetingId);
                        });
                    } catch (ServerIOException | IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private void sendRecordingReadyMessage(DiscordBotZoomMeetingEntity meeting, String link) {
        String message = "Запись конференции \"" + meeting.getMeetingName()  + "\" готова: " + link;
        discordService.sendMessageToUser(meeting.getUserId(), message);
        discordService.sendMessageToChannel(meeting.getChannelId(), message);
    }
}
