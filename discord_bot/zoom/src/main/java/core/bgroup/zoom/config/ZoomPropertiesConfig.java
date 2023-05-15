package core.bgroup.zoom.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix="zoom")
@Getter
@Setter
public class ZoomPropertiesConfig {
    private String url;
    private String accountId;
    private String username;
    private String password;
    private String meetingUri;
}
