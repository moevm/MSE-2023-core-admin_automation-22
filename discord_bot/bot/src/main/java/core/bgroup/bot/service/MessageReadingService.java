package core.bgroup.bot.service;

import discord4j.common.util.Snowflake;
import discord4j.discordjson.json.MessageData;

import java.util.List;

public interface MessageReadingService {
    List<MessageData> readMessagesAfter(Snowflake lastMessageSnowflake);
}
