package core.bgroup.bot.service.impl;

import core.bgroup.bot.service.MessageHandlingService;
import core.bgroup.bot.service.MessageWritingService;
import discord4j.core.DiscordClient;
import discord4j.discordjson.json.MessageData;
import org.springframework.stereotype.Service;

@Service
public class MeetingMessageHandlingService implements MessageHandlingService {
    private final DiscordClient discordClient;
    private final MessageWritingService writingService;

    public MeetingMessageHandlingService(DiscordClient discordClient, MessageWritingService writingService) {
        this.discordClient = discordClient;
        this.writingService = writingService;
    }

    @Override
    public void handleMessage(MessageData message) {
        if (isValidMeetingMessage(message)) {
            answerMeetingMessage(message);
        }
    }

    private boolean isValidMeetingMessage(MessageData message) {
        //TODO remake this example implementation
        return message.content().contains("встреча")
                && message.author().id().asLong() != discordClient.getApplicationId().block();
    }

    private void answerMeetingMessage(MessageData message) {
        //TODO remake this example implementation
        String answer = "<@" + message.author().id() + ">, я пока не умею создавать встречи(";
        writingService.writeMessage(answer);
    }
}
