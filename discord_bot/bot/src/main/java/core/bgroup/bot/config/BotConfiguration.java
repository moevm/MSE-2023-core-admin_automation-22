package core.bgroup.bot.config;

import core.bgroup.yandex.config.YandexConfiguration;
import core.bgroup.zoom.config.ZoomConfiguration;
import discord4j.core.DiscordClient;
import discord4j.core.object.command.ApplicationCommandOption;
import discord4j.discordjson.json.ApplicationCommandOptionData;
import discord4j.discordjson.json.ApplicationCommandRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import java.util.List;

@Configuration
@Import(value = {YandexConfiguration.class, ZoomConfiguration.class})
public class BotConfiguration {
    @Bean
    public DiscordClient discordClient(@Value("${discord.bot-token}") String token) {
        DiscordClient client = DiscordClient.create(token);
//        Long applicationId = client.getApplicationId().block();
//        ApplicationCommandRequest greetCmdRequest = ApplicationCommandRequest.builder()
//                .name("meeting")
//                .description("Create zoom meeting")
//                .addOption(ApplicationCommandOptionData.builder()
//                        .name("topic")
//                        .description("Topic of meeting")
//                        .type(ApplicationCommandOption.Type.STRING.getValue())
//                        .required(true)
//                        .build())
////                .addAllOptions(List.of(
////                        ApplicationCommandOptionData.builder()
////                                .name("topic")
////                                .description("Topic of meeting")
////                                .type(ApplicationCommandOption.Type.STRING.getValue())
////                                .required(true)
////                                .build(),
////                        ApplicationCommandOptionData.builder()
////                                .name("time")
////                                .description("Time of meeting")
////                                .type(ApplicationCommandOption.Type.STRING.getValue())
////                                .required(true)
////                                .build(),
////                        ApplicationCommandOptionData.builder()
////                                .name("agenda")
////                                .description("Agenda of meeting")
////                                .type(ApplicationCommandOption.Type.STRING.getValue())
////                                .required(true)
////                                .build(),
////                        ApplicationCommandOptionData.builder()
////                                .name("host email")
////                                .description("Email of meeting host")
////                                .type(ApplicationCommandOption.Type.STRING.getValue())
////                                .required(true)
////                                .build()
////                ))
//                .build();
//        client.getApplicationService()
//                .createGlobalApplicationCommand(applicationId, greetCmdRequest)
//                .subscribe();
        System.out.println("created");
        return client;
    }
}
