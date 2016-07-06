# создание таблиц
CREATE TABLE city ( 
Name Varchar(40), 
Population INT, 
Area INT, 
Country Varchar(40), 
PRIMARY KEY(Name));

CREATE TABLE country ( 
Name Varchar(40), 
Population INT, 
Area INT, 
Capital Varchar(40), 
PRIMARY KEY(Name));

CREATE TABLE festival ( 
Name Varchar(40), 
City Varchar(40), 
Places INT, 
Country Varchar(40), 
Organizer Varchar(40), 
PRIMARY KEY(Name));

CREATE TABLE festivalOrganizer ( 
Name Varchar(40), 
AccreditationCountry Varchar(40), 
PRIMARY KEY(Name, AccreditationCountry));

# заполнение таблиц
INSERT INTO city VALUES ('Taganrog', 257681, 80, 'Russia');
INSERT INTO city VALUES ('Paris', 2240621, 105, 'France');
INSERT INTO city VALUES ('Luxembourg', 107247, 52, 'Luxembourg');
INSERT INTO city VALUES ('Canberra', 381488, 814, 'Australia');
INSERT INTO city VALUES ('Kaliningrad', 431402, 223, 'Russia');
INSERT INTO city VALUES ('Beloretsk', 68806, 41, 'Russia');
INSERT INTO city VALUES ('Saransk', 297415, 383, 'Russia');
INSERT INTO city VALUES ('Harrisburg', 49526, 27, 'United States');
INSERT INTO city VALUES ('Oslo', 65839, 481, 'Norway');
INSERT INTO city VALUES ('Helsinki', 629512, 715, 'Finland');
INSERT INTO city VALUES ('Warsaw', 1744351, 517, 'Poland');
INSERT INTO city VALUES ('Riga', 696593, 304, 'Latvia');
INSERT INTO city VALUES ('Brussels', 178552, 33, 'Belgium');
INSERT INTO city VALUES ('Washington', 672228, 177, 'United States');
INSERT INTO city VALUES ('Moscow', 11503501, 2511, 'Russia');

INSERT INTO country VALUES ('Russia', 146600000, 17125200, 'Moscow');
INSERT INTO country VALUES ('France', 66710000, 643801, 'Paris');
INSERT INTO country VALUES ('Luxembourg', 562958, 2586, 'Luxembourg');
INSERT INTO country VALUES ('Australia', 2412850, 692024, 'Canberra');
INSERT INTO country VALUES ('United States', 323625762, 9833517, 'Washington');
INSERT INTO country VALUES ('Belgium', 11250585, 30528, 'Brussels');
INSERT INTO country VALUES ('Latvia', 1973700, 64589, 'Riga');
INSERT INTO country VALUES ('Poland', 38483957, 312679, 'Warsaw');
INSERT INTO country VALUES ('Finland', 5488543, 338424, 'Helsinki');
INSERT INTO country VALUES ('Norway', 5214900, 385178, 'Oslo');

# Вывести информацию по стране, передаваемой в качестве параметра в where,
# включающую в себя все города для этой страны с их населением.
SELECT city.Name, city.Population
FROM city
WHERE Country='Russia';

# Вывести сумму площади по городам в рамках каждой страны.
SELECT city.Country, SUM(city.Area)
FROM city
GROUP BY city.Country;

# создание внешних ключей
ALTER TABLE country ADD FOREIGN KEY (Capital) REFERENCES city(Name);

ALTER TABLE city ADD FOREIGN KEY (Country) REFERENCES country(Name);

# заполнение таблиц
INSERT INTO festival VALUES ('Stounfest', 'Canberra', '1', 'Australia', 'University of Canberra');
INSERT INTO festival VALUES ('Ommegang', 'Brussels', '1', 'Belgium', 'Ommegang-Brussels Events');
INSERT INTO festival VALUES ('Flow Festival', 'Helsinki', '3', 'Finland', 'Flow Festival OY');
INSERT INTO festival VALUES ('Paris Folclore Festival', 'Paris', '2', 'France', 'Fiestalonia Milenio');
INSERT INTO festival VALUES ('Kubana', 'Riga', '3', 'Latvia', 'Strova-media');
INSERT INTO festival VALUES ('After Belonging', 'Oslo', '3', 'Norway', 'After Belonging Agency');
INSERT INTO festival VALUES ('Ultra Music Festival Show', 'Warsaw', '2', 'Poland', 'Discovery');
INSERT INTO festival VALUES ('Royal Canberra Show', 'Canberra', '2', 'Australia', 'Royal National Capital Society');
INSERT INTO festival VALUES ('Brussels Jazz Marathon', 'Brussels', '1', 'Belgium', ' asbl Brussels Jazz Marathon vzw');
INSERT INTO festival VALUES ('ColorFest', 'Moscow', '4', 'Russia', 'ColorFest');
INSERT INTO festival VALUES ('The Oya', 'Oslo', '1', 'Norway', 'Camp indie');
INSERT INTO festival VALUES ('Outline', 'Washington', '2', 'United States', 'Outline');

INSERT INTO festivalorganizer VALUES ('University of Canberra', 'Australia');
INSERT INTO festivalorganizer VALUES ('Royal National Capital Society', 'Australia');
INSERT INTO festivalorganizer VALUES ('Ommegang-Brussels Events', 'Belgium');
INSERT INTO festivalorganizer VALUES (' asbl Brussels Jazz Marathon vzw', 'Belgium');
INSERT INTO festivalorganizer VALUES ('Flow Festival OY', 'Finland');
INSERT INTO festivalorganizer VALUES ('Fiestalonia Milenio', 'France');
INSERT INTO festivalorganizer VALUES ('ColorFest', 'Russia');
INSERT INTO festivalorganizer VALUES ('Discovery', 'Poland');
INSERT INTO festivalorganizer VALUES ('Strova-media', 'Latvia');
INSERT INTO festivalorganizer VALUES ('Strova-media', 'Russia');
INSERT INTO festivalorganizer VALUES ('After Belonging Agency', 'Norway');
INSERT INTO festivalorganizer VALUES ('Camp indie', 'Norway');
INSERT INTO festivalorganizer VALUES ('Outline', 'United States');

# создание внешних ключей
ALTER TABLE festival ADD FOREIGN KEY (City) REFERENCES city(Name);

ALTER TABLE festival ADD FOREIGN KEY (Country) REFERENCES Country(Name);

ALTER TABLE festivalorganizer ADD FOREIGN KEY (AccreditationCountry) REFERENCES Country(Name);

ALTER TABLE festival ADD FOREIGN KEY (Country, Organizer) REFERENCES festivalorganizer(AccreditationCountry, Name);

# Вывести все фестивали, которые проводятся в стране с наибольшим населением.
SELECT festival.Name
FROM festival JOIN country ON festival.Country=country.Name
WHERE country.Population=
(SELECT MAX(Population)
FROM country);

# Вывести все фестивали, которые проводятся в городах с населением большим, 
# чем N (N выбрать самостоятельно, в зависимости от ваших данных).
SELECT festival.Name
FROM festival JOIN city ON festival.City=city.Name
WHERE city.Population>500000;

# Рассчитать среднее население городов в странах, в городах которых проходят 3 крупнейших фестиваля. 
# Крупнейшим считается фестиваль в городе с населением большим, чем во всех остальных городах.
# 3 крупнейших фестиваля
CREATE VIEW BigFestival AS
SELECT festival.Country, festival.Name
FROM festival JOIN city ON festival.City=city.Name
ORDER BY city.Population DESC
LIMIT 3;

# BigFestival
SELECT * FROM BigFestival;

# среднее население городов соотетствующих стран
SELECT AVG(Population), city.Country
FROM city JOIN BigFestival ON city.Country=BigFestival.Country
GROUP BY city.Country;
