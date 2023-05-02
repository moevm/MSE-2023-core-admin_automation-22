drop table if exists discord_bot_zoom_meeting cascade;

create table discord_bot_zoom_meeting (
      id BIGINT not null,
      userId BIGINT not null,
      chanelId BIGINT not null,
      meetingUUID varchar(255),
      startUrl varchar(1024),
      joinUrl varchar(1024),
      recordingUrl varchar(1024),
      primary key (id)
);