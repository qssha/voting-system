DROP TABLE dishes IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE users IF EXISTS;
DROP TABLE lunches IF EXISTS;
DROP TABLE restaurants IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE restaurants
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE lunches
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    lunch_date    TIMESTAMP NOT NULL,
    restaurant_id INTEGER   NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurants (id) ON DELETE CASCADE
);

CREATE TABLE dishes
(
    id       INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    price    INTEGER NOT NULL,
    lunch_id INTEGER NOT NULL,
    FOREIGN KEY (lunch_id) REFERENCES lunches (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX lunches_unique_restaurant_lunch_date_idx
    ON lunches (restaurant_id, lunch_date);

CREATE TABLE users
(
    id                 INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name               VARCHAR(255)            NOT NULL,
    email              VARCHAR(255)            NOT NULL,
    password           VARCHAR(255)            NOT NULL,
    registered         TIMESTAMP DEFAULT now() NOT NULL,
    enabled            BOOLEAN   DEFAULT TRUE  NOT NULL,
    last_vote_datetime TIMESTAMP,
    lunch_id           INTEGER,
    FOREIGN KEY (lunch_id) REFERENCES lunches (id) ON DELETE CASCADE
);

CREATE UNIQUE INDEX users_unique_email_idx
    ON USERS (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);