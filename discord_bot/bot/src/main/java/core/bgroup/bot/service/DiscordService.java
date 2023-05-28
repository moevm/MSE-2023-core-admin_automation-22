package core.bgroup.bot.service;

import discord4j.common.util.Snowflake;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.object.entity.channel.MessageChannel;
import org.springframework.stereotype.Service;

@Service
public class DiscordService {
    private final GatewayDiscordClient discordClient;

    public DiscordService(GatewayDiscordClient discordClient) {
        this.discordClient = discordClient;
    }

    public void sendMessageToChannel(Long channelId, String message) {
        discordClient.getChannelById(Snowflake.of(channelId))
                .ofType(MessageChannel.class)
                .flatMap(channel -> channel.createMessage(message))
                .subscribe();
    }

    public void sendMessageToUser(Long userId, String message) {
        discordClient
                .getUserById(Snowflake.of(userId))
                .flatMap(user -> user.getPrivateChannel()
                        .flatMap(dm -> dm.createMessage(message)))
                .subscribe();
    }
}
