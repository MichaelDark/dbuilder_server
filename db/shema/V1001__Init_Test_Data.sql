
INSERT INTO api_keys (id, api_key)
VALUES (1, 'qwertyqwertyqwertyqwertyqwerty'); --admin
INSERT INTO users (id,username, password, enabled, api_key_id)
VALUES (1,'admin', 'JKmRaNF6', TRUE, 1); --admin

INSERT INTO authorities (user_id, authority) VALUES (1, 'ROLE_ADMIN');
--INSERT INTO authorities (user_id, authority) VALUES (2, 'ROLE_USER');
