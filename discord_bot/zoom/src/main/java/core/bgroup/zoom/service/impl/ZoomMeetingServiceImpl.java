package core.bgroup.zoom.service.impl;

import core.bgroup.zoom.dto.MeetingRequest;
import core.bgroup.zoom.dto.MeetingSettingsRequest;
import core.bgroup.zoom.dto.ZoomAccessTokenResponse;
import core.bgroup.zoom.service.ZoomMeetingService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;

@Service
@AllArgsConstructor
public class ZoomMeetingServiceImpl implements ZoomMeetingService {

    private final WebClient webClient;

    private ZoomAccessTokenResponse getAccessTokenResponse() {
        return webClient
                .post()
                .uri("oauth/token?grant_type=account_credentials&account_id=XRBP_W_1TA2PW7JZDD62JA")
                .headers(httpHeaders -> httpHeaders.setBasicAuth("EhTrmNAsR3CKR_wDCmvOzg", "8iDeTZjE0kNQ0Vb0Ewyuna5FI6nheoeq"))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(ZoomAccessTokenResponse.class)
                .block();
    }

    @Override
    public String createMeeting(MeetingRequest meetingRequest) {
        String token = getAccessTokenResponse().getAccessToken();
        return webClient
                .post()
                .uri("v2/users/me/meetings")
                .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                .body(Mono.just(meetingRequest), MeetingSettingsRequest.class)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}
