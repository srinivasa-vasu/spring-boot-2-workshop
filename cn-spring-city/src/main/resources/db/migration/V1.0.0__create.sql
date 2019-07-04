CREATE TABLE city (
  id          BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
  country     VARCHAR(255) NOT NULL,
  latitude    VARCHAR(255),
  longitude   VARCHAR(255),
  name        VARCHAR(255) NOT NULL,
  postal_code VARCHAR(255) NOT NULL,
  state_code  VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);