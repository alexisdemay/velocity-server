CREATE SCHEMA velocity;

CREATE TABLE velocity.stats_stations (
  id                    BIGINT    NOT NULL PRIMARY KEY,
  contract_name         TEXT      NOT NULL,
  total_stations        INTEGER   NOT NULL,
  total_open_stations   INTEGER   NOT NULL,
  total_closed_stations INTEGER   NOT NULL,
  total_bikes           INTEGER   NOT NULL,
  total_available_bike  INTEGER   NOT NULL,
  created_date          TIMESTAMP NOT NULL
);

CREATE SEQUENCE velocity.stats_stations_id_seq;