package core.bgroup.bot.events;

import discord4j.core.object.entity.Message;
import reactor.core.publisher.Mono;

public abstract class MessageListener {

    public Mono<Void> processCommand(Message eventMessage) {
        return Mono.just(eventMessage)
                .filter(message -> message.getContent().equalsIgnoreCase("!Привет"))
                .flatMap(Message::getChannel)
                .flatMap(channel -> channel.createMessage("Привет!"))
                .then();
    }
}
