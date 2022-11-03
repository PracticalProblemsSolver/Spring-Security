DROP DATABASE IF EXISTS medicine;
CREATE DATABASE medicine;
\c medicine;

CREATE TABLE IF NOT EXISTS medicament(
    id serial NOT NULL,
    name varchar(255) NOT NULL,
    type varchar(255) NOT NULL,
    form varchar(255) NOT NULL,
    price real NOT NULL,
    count integer NOT NULL,
	PRIMARY KEY (id)
);

TRUNCATE TABLE medicament;

INSERT INTO medicament VALUES
(1, 'A', 'A', 'Pills', 150.5, 54),
(2, 'B', 'B', 'Liquid', 36, 150);

CREATE TABLE IF NOT EXISTS users(
    id serial not null,
    login varchar(255) NOT NULL,
    password varchar(255) NOT NULL,
    role varchar(255) NOT NULL,
    PRIMARY KEY (id)
);

TRUNCATE TABLE users;

INSERT INTO users VALUES
(1, 'admin', '$2y$10$FD.9oCTw1NeFdBQ6F52Tt.Ct7txTr10x/mIIs3WtiLpSGEcrpOKMS', 'ROLE_ADMIN'),
(2, 'user', '$2y$10$8Nh/xTVwSfGoMcOuWfdGYO0l8r4OOIPRl5dnOMIx/GTTNXD.7YJ3y', 'ROLE_USER');

