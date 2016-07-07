-- СОЗДАНИЕ БАЗЫ ДАННЫХ MySQL 5.5.25 --
CREATE DATABASE summer_practic_2016;
USE summer_practic_2016;

-- Таблица City --
CREATE TABLE `City` (
Name       VARCHAR(40) NOT NULL PRIMARY KEY,
Population INT NOT NULL,
Area       INT NOT NULL,
Country    VARCHAR(40) NOT NULL
) ENGINE=InnoDB CHARACTER SET=UTF8 ;

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

/*
SELECT * FROM City;

+------------------+------------+------+------------+
| Name             | Population | Area | Country    |
+------------------+------------+------+------------+
| Berlin           |    3490800 |  892 | Germany    |
| Helsinki         |     629512 |  715 | Finland    |
| Luxembourg       |     107247 |   52 | Luxembourg |
| Marsellie        |     850000 |  240 | France     |
| Moscow           |   12000000 |  256 | Russia     |
| Munchen          |    1500000 |  310 | Germany    |
| Napoli           |     990000 |  120 | Italy      |
| New Delhi        |     295000 |   42 | India      |
| Oslo             |      65839 |  481 | Norway     |
| Ottawa           |     883391 | 2790 | Canada     |
| Paris            |    2240621 |  105 | France     |
| Roma             |    2870493 | 1287 | Italy      |
| Saint-Petersburg |    5200000 | 1439 | Russia     |
| Tokyo            |   13051965 | 2187 | Japan      |
+------------------+------------+------+------------+
*/

-- Таблица Country --
CREATE TABLE `Country` (
Name       VARCHAR(40) NOT NULL PRIMARY KEY,
Population INT NOT NULL,
Area       INT NOT NULL,
Capital    VARCHAR(40) NOT NULL
) ENGINE=InnoDB CHARACTER SET=UTF8 ;

INSERT INTO `Country` VALUES ('Russia', 150000000, 17000000, 'Moscow');
INSERT INTO `Country` VALUES ('France', 6671000, 643801, 'Paris');
INSERT INTO `Country` VALUES ('Luxembourg', 562958, 2586, 'Luxembourg');
INSERT INTO `Country` VALUES ('Norway', 5214900, 385178, 'Oslo');
INSERT INTO `Country` VALUES ('Finland', 5488543, 338424, 'Helsinki');
INSERT INTO `Country` VALUES ('Germany', 81757600, 357022,'Berlin');
INSERT INTO `Country` VALUES ('Japan', 12738700, 377873,'Tokyo');
INSERT INTO `Country` VALUES ('Italy', 60200060, 301318,'Roma');
INSERT INTO `Country` VALUES ('Canada', 3374000, 9984670,'Ottawa');
INSERT INTO `Country` VALUES ('India', 12915940, 3287263, 'New Delhi');

/*
SELECT * FROM Country;

+------------+------------+----------+------------+
| Name       | Population | Area     | Capital    |
+------------+------------+----------+------------+
| Canada     |    3374000 |  9984670 | Ottawa     |
| Finland    |    5488543 |   338424 | Helsinki   |
| France     |    6671000 |   643801 | Paris      |
| Germany    |   81757600 |   357022 | Berlin     |
| India      |   12915940 |  3287263 | New Delhi  |
| Italy      |   60200060 |   301318 | Roma       |
| Japan      |   12738700 |   377873 | Tokyo      |
| Luxembourg |     562958 |     2586 | Luxembourg |
| Norway     |    5214900 |   385178 | Oslo       |
| Russia     |  150000000 | 17000000 | Moscow     |
+------------+------------+----------+------------+
*/

-- Практическое задание --

-- Вывести информацию по стране, передаваемой в качестве параметра в where,
-- включающую в себя все города для этой страны с их населением.
SELECT City.Name AS City, City.Population AS Population
FROM `City`
WHERE Country = 'Russia';
/*
+------------------+------------+
| City             | Population |
+------------------+------------+
| Moscow           |   12000000 |
| Saint-Petersburg |    5200000 |
+------------------+------------+
*/

-- Вывести сумму площади по городам в рамках каждой страны.
SELECT City.Country AS Country, SUM(City.Area) AS Cities_Area
FROM `City`
GROUP BY Country;
/*
+------------+-------------+
| Country    | Cities_Area |
+------------+-------------+
| Canada     |        2790 |
| Finland    |         715 |
| France     |         345 |
| Germany    |        1202 |
| India      |          42 |
| Italy      |        1407 |
| Japan      |        2187 |
| Luxembourg |          52 |
| Norway     |         481 |
| Russia     |        1695 |
+------------+-------------+
*/

-- Сделать Foreign key Country.Capital -> City.Name
ALTER TABLE Country ADD FOREIGN KEY (Capital) REFERENCES City(Name);
/* Query OK, 10 rows affected (0.28 sec) */
-- Test: INSERT INTO `City` VALUES ('city-17', 666, 777, 'HL2');
--       INSERT INTO `Country` VALUES ('HL2', 777, 666, 'HL2'); (Error #1452)
--       INSERT INTO `Country` VALUES ('HL2', 777, 666, 'city-17'); (success)



-- Домашнее задание --

-- Таблица FestivalOrganizer --
CREATE TABLE `FestivalOrganizer` (
Name                  VARCHAR(40) NOT NULL,
AccreditationCountry  VARCHAR(40) NOT NULL,
PRIMARY KEY (Name),
FOREIGN KEY (AccreditationCountry) REFERENCES `Country`(Name)
) ENGINE=InnoDB CHARACTER SET=UTF8 ;

INSERT INTO `FestivalOrganizer` VALUES ('Strova-media', 'Russia');
INSERT INTO `FestivalOrganizer` VALUES ('ColorFest', 'Russia');
INSERT INTO `FestivalOrganizer` VALUES ('Camp indie', 'Norway');
INSERT INTO `FestivalOrganizer` VALUES ('After Belonging Agency', 'Norway');
INSERT INTO `FestivalOrganizer` VALUES ('Fiestalonia Milenio', 'France');
INSERT INTO `FestivalOrganizer` VALUES ('YoungHome', 'Italy');
INSERT INTO `FestivalOrganizer` VALUES ('YoungHome', 'Finland');
INSERT INTO `FestivalOrganizer` VALUES ('JapOrganize', 'Japan');
INSERT INTO `FestivalOrganizer` VALUES ('MadBird', 'Germany');
INSERT INTO `FestivalOrganizer` VALUES ('MadBird', 'Italy');
/*
SELECT * FROM FestivalOrganizer;

+------------------------+----------------------+
| Name                   | AccreditationCountry |
+------------------------+----------------------+
| Fiestalonia Milenio    | France               |
| MadBird                | Germany              |
| YoungHome              | Italy                |
| JapOrganize            | Japan                |
| After Belonging Agency | Norway               |
| Camp indie             | Norway               |
| ColorFest              | Russia               |
| Strova-media           | Russia               |
+------------------------+----------------------+
*/

-- Таблица Festival --
CREATE TABLE `Festival` (
Name       VARCHAR(40) NOT NULL PRIMARY KEY,
City       VARCHAR(40) NOT NULL,
Places     INT         NOT NULL,
Country    VARCHAR(40) NOT NULL,
Organizer  VARCHAR(40) NOT NULL,
FOREIGN KEY (Organizer) REFERENCES `FestivalOrganizer`(Name),
FOREIGN KEY (City)      REFERENCES `City`(Name),
FOREIGN KEY (Country)   REFERENCES `Country`(Name)
) ENGINE=InnoDB CHARACTER SET=UTF8 ;

INSERT INTO `Festival` VALUES ('The Oya', 'Oslo', 200000, 'Norway', 'Camp indie');
INSERT INTO `Festival` VALUES ('ColorFest', 'Moscow', 300000, 'Russia', 'ColorFest');
INSERT INTO `Festival` VALUES ('Paris Folclore Festival', 'Paris', 400000, 'France', 'Fiestalonia Milenio');
INSERT INTO `Festival` VALUES ('Super puper fest', 'Saint-Petersburg', 150000000, 'Russia', 'Strova-media');
INSERT INTO `Festival` VALUES ('Donaunselfest', 'Roma', 97631, 'Italy', 'YoungHome');
INSERT INTO `Festival` VALUES ('Maazine', 'Helsinki', 451217, 'Finland', 'YoungHome');
INSERT INTO `Festival` VALUES ('RockTokyo', 'Tokyo', 411499, 'Japan','JapOrganize');
INSERT INTO `Festival` VALUES ('NewRockTokyo', 'Tokyo', 1, 'Japan','JapOrganize');
INSERT INTO `Festival` VALUES ('ElectDaisy', 'Berlin', 299999, 'Germany', 'MadBird');
INSERT INTO `Festival` VALUES ('Strange Fest', 'Saint-Petersburg', 150000000, 'Russia', 'ColorFest');
INSERT INTO `Festival` VALUES ('Lazy fest', 'Oslo', 666666, 'Norway', 'After Belonging Agency');
/*
SELECT * FROM Festival;

+-------------------------+------------------+-----------+---------+------------------------+
| Name                    | City             | Places    | Country | Organizer              |
+-------------------------+------------------+-----------+---------+------------------------+
| ColorFest               | Moscow           |    300000 | Russia  | ColorFest              |
| Donaunselfest           | Roma             |     97631 | Italy   | YoungHome              |
| ElectDaisy              | Berlin           |    299999 | Germany | MadBird                |
| Lazy fest               | Oslo             |    666666 | Norway  | After Belonging Agency |
| Maazine                 | Helsinki         |    451217 | Finland | YoungHome              |
| NewRockTokyo            | Tokyo            |         1 | Japan   | JapOrganize            |
| Paris Folclore Festival | Paris            |    400000 | France  | Fiestalonia Milenio    |
| RockTokyo               | Tokyo            |    411499 | Japan   | JapOrganize            |
| Strange Fest            | Saint-Petersburg | 150000000 | Russia  | ColorFest              |
| Super puper fest        | Saint-Petersburg | 150000000 | Russia  | Strova-media           |
| The Oya                 | Oslo             |    200000 | Norway  | Camp indie             |
+-------------------------+------------------+-----------+---------+------------------------+
*/

-- Вывести все фестивали, которые проводятся в стране с наибольшим населением.
SELECT Festival.Name AS Fest
FROM Festival JOIN Country ON Festival.Country = Country.Name
WHERE Country.Population = (
   SELECT MAX(Country.Population)
   FROM Country
);
/*
+------------------+
| Fest             |
+------------------+
| ColorFest        |
| Strange Fest     |
| Super puper fest |
+------------------+
*/

-- Вывести все фестивали, которые проводятся в городах с населением большим,
-- чем N (N выбрать самостоятельно, в зависимости от ваших данных).
--    Пусть N = 5000000
SELECT Festival.Name AS Fest
FROM Festival JOIN City ON Festival.City = City.Name
WHERE City.Population > 5000000;
/*
+------------------+
| Fest             |
+------------------+
| ColorFest        |
| Strange Fest     |
| Super puper fest |
| NewRockTokyo     |
| RockTokyo        |
+------------------+
*/

-- Рассчитать среднее население городов в странах, в городах которых проходят
-- 3 крупнейших фестиваля. Крупнейшим считается фестиваль в городе с населением
-- большим, чем во всех остальных городах.
SELECT Country.Name AS Country, AVG(City.Population) AS Avg_Population
FROM City JOIN Country ON City.Country = Country.Name
WHERE Country.Name IN (
   SELECT DISTINCT *
   FROM (
      SELECT City.Country
      FROM Festival JOIN City ON Festival.City = City.Name
      ORDER BY City.Population DESC
      LIMIT 3
   ) tt
)
GROUP BY Country.Name;
/*
+---------+----------------+
| Country | Avg_Population |
+---------+----------------+
| Japan   |  13051965.0000 |
| Russia  |   8600000.0000 |
+---------+----------------+
*/
