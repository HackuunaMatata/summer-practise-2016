-- СОЗДАНИЕ БАЗЫ ДАННЫХ --
-- Таблица City --
CREATE TABLE `City` (
Name       VARCHAR(40) NOT NULL PRIMARY KEY,
Population INT NOT NULL,
Area       INT NOT NULL,
Country    VARCHAR(40) NOT NULL
);

INSERT INTO `City` VALUES ('Moscow', 12000000, 256, 'Russia');
INSERT INTO `City` VALUES ('Saint-Petersburg', 5200000, 1439, 'Russia');
INSERT INTO `City` VALUES ('Paris', 2240621, 105, 'France');
INSERT INTO `City` VALUES ('Marsellie', 850000, 240, 'France');
INSERT INTO `City` VALUES ('Luxembourg', 107247, 52, 'Luxembourg');
INSERT INTO `City` VALUES ('Oslo', 65839, 481, 'Norway');
INSERT INTO `City` VALUES ('Helsinki', 629512, 715, 'Finland');
INSERT INTO `City` VALUES ('Berlin', 3490800, 892,'Germany');
INSERT INTO `City` VALUES ('Munchen', 1500000, 310,'Germany');
INSERT INTO `City` VALUES ('Tokyo', 13051965, 2187,'Japan');
INSERT INTO `City` VALUES ('Roma', 2870493, 1287,'Italy');
INSERT INTO `City` VALUES ('Napoli', 990000, 120,'Italy');
INSERT INTO `City` VALUES ('Ottawa', 883391, 2790,'Canada');
INSERT INTO `City` VALUES ('New Delhi', 295000, 42, 'India');


-- Таблица Country --
CREATE TABLE `Country` (
Name       VARCHAR(40) NOT NULL PRIMARY KEY,
Population INT NOT NULL,
Area       INT NOT NULL,
Capital    VARCHAR(40) NOT NULL
);

INSERT INTO `Country` VALUES ('Russia', 150000000, 17000000, 'Moscow');
INSERT INTO `Country` VALUES ('France', 66710000, 643801, 'Paris');
INSERT INTO `Country` VALUES ('Luxembourg', 562958, 2586, 'Luxembourg');
INSERT INTO `Country` VALUES ('Norway', 5214900, 385178, 'Oslo');
INSERT INTO `Country` VALUES ('Finland', 5488543, 338424, 'Helsinki');
INSERT INTO `Country` VALUES ('Germany', 81757600, 357022,'Berlin');
INSERT INTO `Country` VALUES ('Japan', 127387000, 377873,'Tokyo');
INSERT INTO `Country` VALUES ('Italy', 60200060, 301318,'Roma');
INSERT INTO `Country` VALUES ('Canada', 33740000, 9984670,'Ottawa');
INSERT INTO `Country` VALUES ('India', 1291594000, 3287263, 'New Delhi');
