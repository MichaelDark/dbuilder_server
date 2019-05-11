CREATE SEQUENCE IF NOT EXISTS sq__api_key;
CREATE TABLE IF NOT EXISTS api_keys (
  id           INT8 NOT NULL DEFAULT nextval('sq__api_key') PRIMARY KEY,
  api_key       VARCHAR(256) NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS sq__user_id;
CREATE TABLE IF NOT EXISTS users (
  id           INT8 NOT NULL DEFAULT nextval('sq__user_id') PRIMARY KEY,
  password     VARCHAR(50) NOT NULL,
  username     VARCHAR(256),
  enabled      BOOLEAN      DEFAULT TRUE,
  name         VARCHAR(255) DEFAULT NULL,
  surname      VARCHAR(255) DEFAULT NULL,
  phone        VARCHAR(50)  DEFAULT NULL,
  api_key_id    INT8 NOT NULL ,
  CONSTRAINT fk_users_api_keys FOREIGN KEY (api_key_id) REFERENCES api_keys (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS authorities (
  user_id   INT8 NOT NULL,
  authority VARCHAR(50) NOT NULL,
  CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
  CONSTRAINT ix_authority UNIQUE (user_id, authority)
);

CREATE SEQUENCE IF NOT EXISTS sq__device_id;
CREATE TABLE IF NOT EXISTS devices (
  id          INT8 NOT NULL DEFAULT nextval('sq__device_id') PRIMARY KEY,
  user_id     INT8 NOT NULL,
  device_id   VARCHAR (255) NOT NULL ,
  name        VARCHAR(255) NOT NULL,
  CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS sq__session_id;
CREATE TABLE IF NOT EXISTS sessions (
  id            INT8 NOT NULL DEFAULT nextval('sq__session_id') PRIMARY KEY,
  user_id       INT8 NOT NULL,
  CONSTRAINT fk_users FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
  title         VARCHAR(255) NOT NULL,
  description   VARCHAR(255) NOT NULL,
  start_date    INT8 NOT NULL,
  end_date      INT8 NOT NULL
);

CREATE TABLE IF NOT EXISTS device_to_session (
  device_id     INT8 REFERENCES devices (id) ON UPDATE CASCADE ON DELETE CASCADE,
  session_id    INT8 REFERENCES sessions (id) ON UPDATE CASCADE
);

CREATE SEQUENCE IF NOT EXISTS sq__measurment_id;
CREATE TABLE IF NOT EXISTS measurements (
  id                INT8 NOT NULL DEFAULT nextval('sq__measurment_id') PRIMARY KEY,
  device_id         VARCHAR(255) NOT NULL,
  time              INT8 NOT NULL,
  heartbeat         INT8 NOT NULL,
  bodyTemperature   real NOT NULL
);
