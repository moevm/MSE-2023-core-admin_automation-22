package core.bgroup.bot.config;

import core.bgroup.yandex.config.YandexConfiguration;
import core.bgroup.zoom.config.ZoomConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {YandexConfiguration.class, ZoomConfiguration.class})
public class BotConfiguration {
}
