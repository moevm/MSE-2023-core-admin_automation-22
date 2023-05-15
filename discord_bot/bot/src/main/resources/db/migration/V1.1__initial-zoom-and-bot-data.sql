drop table if exists discord_bot_zoom_meeting cascade;

create table discord_bot_zoom_meeting (
      id BIGSERIAL not null,
      user_id BIGINT not null,
      channel_id BIGINT not null,
      meeting_id BIGINT,
      start_url varchar(1024),
      join_url varchar(1024),
      recording_url varchar(1024),
      primary key (id)
);