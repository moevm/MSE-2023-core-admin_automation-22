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

    public void sendRecordingUploadedMessage(String recordingUrl) {
        discordClient.getChannelById(Snowflake.of(1082364732553560135L))
                .ofType(MessageChannel.class)
                .flatMap(channel -> {
                    
                    return channel.createMessage("Запись готова: " + recordingUrl);
                })
                .subscribe();
    }
}
