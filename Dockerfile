FROM maven:3.8.1-openjdk-17 AS builder
WORKDIR /app
COPY discord_bot/bot discord_bot/bot
COPY discord_bot/yandex discord_bot/yandex
COPY discord_bot/zoom discord_bot/zoom
COPY discord_bot/pom.xml discord_bot/pom.xml

RUN mvn -f /app/discord_bot/pom.xml clean package

FROM openjdk:17
COPY --from=builder ./app/discord_bot/bot/target/*.jar application.jar
ENTRYPOINT ["java", "-jar", "application.jar"]