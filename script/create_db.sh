#!/bin/bash
psql -U postgres

CREATE DATABASE template;
CREATE USER template WITH password 'template';
GRANT ALL ON DATABASE template TO template;

GRANT CONNECT ON DATABASE dbuilder TO template;
GRANT USAGE ON SCHEMA public TO template ;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO template ;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO template ;

 \q

DROP SCHEMA public CASCADE;
CREATE SCHEMA public;
GRANT ALL ON schema public TO template;
GRANT CONNECT ON DATABASE dbuilder TO template;
GRANT USAGE ON SCHEMA public TO template;
GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO template;
GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA public TO template;
GRANT ALL PRIVILEGES ON ALL FUNCTIONS IN SCHEMA public TO template;



