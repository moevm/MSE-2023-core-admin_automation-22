package core.bgroup.bot.service.impl;

import core.bgroup.bot.service.MessageReadingService;
import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.discordjson.json.MessageData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ChannelMessageReadingService implements MessageReadingService {
    private final Snowflake channelSnowflake;
    private final DiscordClient discordClient;

    public ChannelMessageReadingService(@Value("${discord.meeting-channel-id}") long channelId, DiscordClient discordClient) {
        this.channelSnowflake = Snowflake.of(channelId);
        this.discordClient = discordClient;
    }

    @Override
    public List<MessageData> readMessagesAfter(Snowflake lastMessageSnowflake) {
        return discordClient.getChannelById(channelSnowflake)
                .getMessagesAfter(lastMessageSnowflake)
                .toStream()
                .toList();
    }
}
