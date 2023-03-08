package core.bgroup.bot.service.impl;

import core.bgroup.bot.service.MessageWritingService;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ChannelMessageWritingService implements MessageWritingService {
    private final Snowflake channelSnowflake;
    private final DiscordClient discordClient;

    public ChannelMessageWritingService(@Value("${discord.meeting-channel-id}") long channelId, DiscordClient discordClient) {
        this.channelSnowflake = Snowflake.of(channelId);
        this.discordClient = discordClient;
    }

    @Override
    public void writeMessage(String content) {
        discordClient.getChannelById(channelSnowflake)
                .createMessage(content)
                .block();
    }
}
