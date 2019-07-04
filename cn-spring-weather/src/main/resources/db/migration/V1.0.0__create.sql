/*create sequence city_weather_sequence start with 1 increment by 1;*/

CREATE TABLE city_weather (
  id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  country     VARCHAR(255) NOT NULL,
  weather     VARCHAR(255) NOT NULL,
  name        VARCHAR(255) NOT NULL,
  postal_code VARCHAR(255) NOT NULL,
  state_code  VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);