package core.bgroup.bot.config;

import core.bgroup.yandex.config.YandexConfiguration;
import core.bgroup.zoom.config.ZoomConfiguration;
import discord4j.core.DiscordClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = {YandexConfiguration.class, ZoomConfiguration.class})
public class BotConfiguration {
    @Bean
    public DiscordClient discordClient(@Value("${discord.bot-token}") String token) {
        return DiscordClient.create(token);
    }
}
