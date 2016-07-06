-- СОЗДАНИЕ БАЗЫ ДАННЫХ --
-- Таблица City --
CREATE TABLE `City` (
Name       VARCHAR(40) NOT NULL PRIMARY KEY,
Population INT NOT NULL,
Area       INT NOT NULL,
Country    VARCHAR(40) NOT NULL
);

INSERT INTO `City` (Name,Population,Area,Country) VALUES ('Moscow', 12000000, 256, 'Russia');


-- Таблица Country --
CREATE TABLE `Country` (
Name       VARCHAR(40) NOT NULL PRIMARY KEY,
Population INT NOT NULL,
Area       INT NOT NULL,
Capital    VARCHAR(40) NOT NULL
);

INSERT INTO `Country` (Name,Population,Area,Capital) VALUES ('Russia', 150000000, 17000000, 'Moscow');