package core.bgroup.yandex.config;

import com.yandex.disk.rest.Credentials;
import com.yandex.disk.rest.RestClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "core.bgroup.yandex")
public class YandexConfiguration {
    @Bean
    public RestClient yandexClient(@Value("${yandex.token}") String token) {
        return new RestClient(new Credentials(null, token));
    }
}
