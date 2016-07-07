CREATE TABLE City
(
	Name VARCHAR(40),
	Population INT,
	Area INT,
	Country VARCHAR(40)
);
CREATE TABLE Country
(
	Name VARCHAR(40),
    Population INT,
    Area INT,
    Capital VARCHAR(40)
);

INSERT INTO City VALUES ('Saint-Perestburg', 5131942, 1439, 'Russia');
INSERT INTO City VALUES ('Irkutsk', 620099, 277, 'Russia');
INSERT INTO City VALUES ('Sevsk', 6847, 10, 'Russia');
INSERT INTO City VALUES ('Nevinnomyssk', 117891, 100, 'Russia');
INSERT INTO City VALUES ('Tolyatti', 719646, 314, 'Russia');
INSERT INTO City VALUES ('Berlin', 3469800, 892, 'Germany');
INSERT INTO City VALUES ('Moscow', 12330126, 2562, 'Russia');
INSERT INTO City VALUES ('Santiago', 5128590, 641, 'Chile');
INSERT INTO City VALUES ('Belmopan', 13351, 30, 'Belize');
INSERT INTO City VALUES ('Krakow', 762508, 327, 'Poland');
INSERT INTO City VALUES ('Dublin', 506211, 115, 'Ireland');
INSERT INTO City VALUES ('Beijing', 21705000, 16801, 'China');
INSERT INTO City VALUES ('Harbin', 10635971, 4640, 'China');
INSERT INTO City VALUES ('Kingston', 580000, 480, 'Jamaica');
INSERT INTO City VALUES ('Montego Bay', 110115, 100, 'Jamaica');
INSERT INTO City VALUES ('Addis Ababa', 3041002, 527, 'Ethiopia');
INSERT INTO City VALUES ('Havana', 2106146, 728, 'Cuba');

INSERT INTO Country VALUES ('Russia', 146544710, 17125191, 'Moscow');
INSERT INTO Country VALUES ('Germany', 81770900, 357168, 'Berlin');
INSERT INTO Country VALUES ('Chile', 17216945, 756950, 'Santiago');
INSERT INTO Country VALUES ('Belize', 347369, 22966, 'Belmopan');
INSERT INTO Country VALUES ('Poland', 38483957, 312679, 'Krakow');
INSERT INTO Country VALUES ('Ireland', 6378000, 84421, 'Dublin');
INSERT INTO Country VALUES ('China', 1376049000, 9596961, 'Beijing');
INSERT INTO Country VALUES ('Jamaica', 2950210, 10991, 'Kingston');
INSERT INTO Country VALUES ('Ethiopia', 99465819, 17125191, 'Addis Ababa');
INSERT INTO Country VALUES ('Cuba', 11239004, 109884, 'Havana');

# Вывод городов и их населения по Ямайке
SELECT Name, Population FROM City WHERE Country = 'Jamaica';

# Вывод суммарной городской площади по странам
SELECT Country, SUM(Area) AS 'Total city area' FROM City GROUP BY Country;

ALTER TABLE City ADD PRIMARY KEY (Name);
ALTER TABLE Country ADD PRIMARY KEY (Name);

ALTER TABLE City ADD FOREIGN KEY (Country) REFERENCES Country(Name);
ALTER TABLE Country ADD FOREIGN KEY (Capital) REFERENCES City(Name);

# HOMEWORK starts here #
CREATE TABLE Festival (
    Name VARCHAR(40),
    City VARCHAR(40),
    Places INT,
    Country VARCHAR(40),
    Organizer VARCHAR(40)
);

CREATE TABLE FestivalOrganizer (
    Name VARCHAR(40),
    AccreditationCountry VARCHAR(40)
);

INSERT INTO FestivalOrganizer VALUES ('Mr. Twister', 'Belize');
INSERT INTO FestivalOrganizer VALUES ('Some Organizer', 'Poland');
INSERT INTO FestivalOrganizer VALUES ('Saint Petersburg City Administration', 'Russia');
INSERT INTO FestivalOrganizer VALUES ('Coca-Cola', 'Germany');
INSERT INTO FestivalOrganizer VALUES ('Mr. Rudebwoy', 'Cuba');
INSERT INTO FestivalOrganizer VALUES ('Mr. Rudebwoy', 'Jamaica');
INSERT INTO FestivalOrganizer VALUES ('Some Organizer', 'Ethiopia');
INSERT INTO FestivalOrganizer VALUES ('Some Organizer', 'Ireland');
INSERT INTO FestivalOrganizer VALUES ('Some Organizer', 'Germany');
INSERT INTO FestivalOrganizer VALUES ('Teatro Municipal', 'Chile');
INSERT INTO FestivalOrganizer VALUES ('National Stadium + Disney company', 'China');

INSERT INTO Festival VALUES ('Bob Marley Celebration', 'Kingston', 1, 'Jamaica', 'Mr. Rudebwoy');
INSERT INTO Festival VALUES ('Jonkanoo', 'Kingston', 16, 'Jamaica', 'Mr. Rudebwoy');
INSERT INTO Festival VALUES ('Africa Jamfest', 'Montego Bay', 1, 'Jamaica', 'Mr. Rudebwoy');
INSERT INTO Festival VALUES ('Dr. Martin Luther King Junior Day', 'Belmopan', 1, 'Belize', 'Mr. Twister');
INSERT INTO Festival VALUES ('Prophet Mohammed\'s birthday', 'Addis Ababa', 25, 'Ethiopia', 'Some Organizer');
INSERT INTO Festival VALUES ('Water Festival', 'Havana', 1, 'Cuba', 'Mr. Rudebwoy');
INSERT INTO Festival VALUES ('International Beer Festival', 'Berlin', 1, 'Germany', 'Coca-Cola');
INSERT INTO Festival VALUES ('International Literature Festival', 'Berlin', 1, 'Germany', 'Some Organizer');
INSERT INTO Festival VALUES ('Tolerance Festival', 'Krakow', 1, 'Poland', 'Some Organizer');
INSERT INTO Festival VALUES ('White Nights Festival', 'Saint-Perestburg', 1, 'Russia', 'Saint Petersburg City Administration');
INSERT INTO Festival VALUES ('DUBLIN IRISH FESTIVAL', 'Dublin', 1, 'Ireland', 'Some Organizer');
INSERT INTO Festival VALUES ('Festival Coreógrafos', 'Santiago', 1, 'Chile', 'Teatro Municipal');
INSERT INTO Festival VALUES ('The Bird\'s Nest Ice and Snow Festival', 'Beijing', 1, 'China', 'National Stadium + Disney company');

ALTER TABLE FestivalOrganizer ADD PRIMARY KEY (Name, AccreditationCountry);
ALTER TABLE Festival ADD PRIMARY KEY (Name);

ALTER TABLE FestivalOrganizer ADD FOREIGN KEY (AccreditationCountry) REFERENCES Country(Name);
ALTER TABLE Festival ADD FOREIGN KEY (City) REFERENCES City(Name);
ALTER TABLE Festival ADD FOREIGN KEY (Organizer, Country) REFERENCES FestivalOrganizer(Name, AccreditationCountry);

# Выбор всех фестивалей из страны с наибольшим населением
SELECT Name, Country, City FROM Festival WHERE Country IN (SELECT Name FROM Country WHERE Population IN (SELECT MAX(Population) FROM COUNTRY));

# Выбор всех фестивалей из городов с населением более 1 млн. человек
SELECT Name, Country, City FROM Festival WHERE City IN (SELECT Name FROM City WHERE Population > 1000000);

# Подсчет среднего городского населения по странам, в которых проходят три самых курпных фестиваля
SELECT ANY_VALUE(Festival.Name) AS Festival, City.Country AS Country, AVG(City.Population) AS 'Average city population' 
FROM Festival, City 
WHERE Festival.City = City.Name
GROUP BY City.Country 
ORDER BY MAX(City.Population) DESC 
LIMIT 3;