DELETE
FROM DISHES;
DELETE
FROM USER_ROLES;
DELETE
FROM USERS;
DELETE
FROM LUNCHES;
DELETE
FROM RESTAURANTS;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO RESTAURANTS(name)
VALUES ('First'),
       ('Second');

INSERT INTO LUNCHES(lunch_date, restaurant_id)
VALUES ('2020-01-30', 100000),
       ('2020-01-30', 100001);

INSERT INTO DISHES(price, lunch_id)
VALUES (100, 100002),
       (300, 100002),
       (400, 100003);

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 100007),
       ('ADMIN', 100008),
       ('USER', 100008);