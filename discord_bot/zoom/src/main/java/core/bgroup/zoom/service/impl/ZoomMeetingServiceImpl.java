package core.bgroup.zoom.service.impl;

import core.bgroup.zoom.config.ZoomPropertiesConfig;
import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.MeetingSettingsRequest;
import core.bgroup.zoom.dto.ZoomAccessTokenResponse;
import core.bgroup.zoom.service.ZoomMeetingService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@AllArgsConstructor
public class ZoomMeetingServiceImpl implements ZoomMeetingService {
    private final WebClient webClient;

    @Autowired
    private ZoomPropertiesConfig zoomPropertiesConfig;

    public ZoomAccessTokenResponse getAccessTokenResponse() {
        System.out.println(zoomPropertiesConfig.getAccountId());
        return webClient
                .post()
                .uri("oauth/token?grant_type=account_credentials&account_id=" + zoomPropertiesConfig.getAccountId())
                .headers(httpHeaders -> httpHeaders.setBasicAuth(zoomPropertiesConfig.getUsername(), zoomPropertiesConfig.getPassword()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ZoomAccessTokenResponse.class)
                .block();
    }

    @Override
    public void deleteRecording(long meetingId) {
        webClient
                .delete()
                .uri("v2/meetings/" + meetingId + "/recordings")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(getAccessTokenResponse().getAccessToken()))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    public String createMeeting(MeetingRequest meetingRequest) {
        String token = getAccessTokenResponse().getAccessToken();
        System.out.println(token);
        return webClient
                .post()
                .uri(zoomPropertiesConfig.getMeetingUri())
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .body(Mono.just(meetingRequest), MeetingSettingsRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
