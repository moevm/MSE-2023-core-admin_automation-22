package core.bgroup.bot.service;

import discord4j.discordjson.json.MessageData;

public interface MessageHandlingService {
    void handleMessage(MessageData message);
}
