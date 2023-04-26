package core.bgroup.zoom.dto;

import lombok.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebhookValidationResponse {
    private String plainToken;
    private String encryptedToken;
}
