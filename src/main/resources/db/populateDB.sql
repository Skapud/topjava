DELETE
FROM meals;
DELETE
FROM user_role;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin'),
       ('Guest', 'guest@gmail.com', 'guest');

INSERT INTO user_role (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO meals (user_id, calories, date_time, description)
VALUES (100000, 333, '2020-12-05 08:00', 'Контрольный прием user'),
       (100000, 750, '2020-12-05 10:03', 'Завтрак user'),
       (100000, 900, '2020-12-05 14:30', 'Обед user'),
       (100000, 500, '2020-12-05 20:15', 'Ужин user'),
       (100001, 375, '2023-03-15 08:20', 'Завтрак admin'),
       (100001, 450, '2023-03-15 12:15', 'Обед admin'),
       (100001, 250, '2023-03-15 16:50', 'Полдник admin'),
       (100001, 600, '2023-03-16 00:35', 'Ужин admin');

