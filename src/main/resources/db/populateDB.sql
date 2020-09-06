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
VALUES ('First restaurant'),
       ('Second restaurant'),
       ('Third restaurant');

INSERT INTO LUNCHES(lunch_date, restaurant_id)
VALUES ('2020-08-30', 100000),
       ('2020-08-31', 100001),
       ('2020-08-31', 100002);

INSERT INTO DISHES(name, price)
VALUES ('Eggs', 100),
       ('Steak', 1500),
       ('Cheesecake', 700),
       ('Borscht', 700),
       ('Spaghetti', 300),
       ('Apple pie', 700),
       ('Solyanka', 900),
       ('Pelmeni', 600),
       ('Pancakes', 500);

INSERT INTO DISHES_LUNCHES(dish_id, lunch_id)
VALUES (100006, 100003),
       (100007, 100003),
       (100008, 100003),
       (100009, 100004),
       (100010, 100004),
       (100011, 100004),
       (100012, 100005),
       (100013, 100005),
       (100014, 100005);

INSERT INTO USERS (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO USER_ROLES (role, user_id)
VALUES ('USER', 100015),
       ('USER', 100016),
       ('ADMIN', 100016);

INSERT INTO VOTES (USER_ID, RESTAURANT_ID, VOTE_DATE)
VALUES (100015, 100000, '2020-08-30'),
       (100016, 100001, '2020-08-31');