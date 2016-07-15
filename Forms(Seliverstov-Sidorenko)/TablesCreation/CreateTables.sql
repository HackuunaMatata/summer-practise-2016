CREATE TABLE Events(
idevent INT AUTO_INCREMENT,
title VARCHAR(256),
image BLOB,
UNIQUE(title),
PRIMARY KEY(idevent)
);

CREATE TABLE Questions(
idevent INT,
itemname VARCHAR(64),
iditem INT AUTO_INCREMENT,
tag VARCHAR(8),
type VARCHAR(8),
description VARCHAR(64),
isActive boolean,
UNIQUE(idevent, itemname),
PRIMARY KEY(iditem)
);

CREATE TABLE Answers(
iditem INT,
answer VARCHAR(64),
PRIMARY KEY(iditem, answer)
);

CREATE TABLE Users(
iduser INT AUTO_INCREMENT,
surname VARCHAR(64),
name VARCHAR(64),
idevent INT,
UNIQUE(idevent, surname),
PRIMARY KEY(iduser, idevent)
);

CREATE TABLE Blobs(
iduser INT,
iditem INT,
idevent INT,
value BLOB,
PRIMARY KEY(iduser, iditem, idevent)
);

CREATE TABLE Dates(
iduser INT,
iditem INT,
idevent INT,
value TIMESTAMP(6),
PRIMARY KEY(iduser, iditem, idevent)
);

CREATE TABLE Numbers(
iduser INT,
iditem INT,
idevent INT,
value INT,
PRIMARY KEY(iduser, iditem, idevent, value)
);

CREATE TABLE Strings(
iduser INT,
iditem INT,
idevent INT,
value VARCHAR(256),
PRIMARY KEY(iduser, iditem, idevent, value)
);

ALTER TABLE Users ADD CONSTRAINT fk_Users FOREIGN KEY (idevent) REFERENCES Events(idevent) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Questions ADD CONSTRAINT fk_Questions FOREIGN KEY (idevent) REFERENCES Events(idevent) ON UPDATE CASCADE ON DELETE CASCADE;
ALTER TABLE Answers ADD CONSTRAINT fk_Answers1 FOREIGN KEY (iditem) REFERENCES Questions(iditem) ON UPDATE CASCADE ON DELETE CASCADE;
/*ALTER TABLE Answers ADD CONSTRAINT fk_Answers2 FOREIGN KEY (idevent) REFERENCES Events(idevent) ON UPDATE CASCADE ON DELETE CASCADE;*/

ALTER TABLE Strings ADD CONSTRAINT fk_Strings1 FOREIGN KEY (iduser, idevent) REFERENCES Users(iduser, idevent) ON UPDATE CASCADE ON DELETE CASCADE,
ADD CONSTRAINT fk_Strings2 FOREIGN KEY (iditem) REFERENCES Questions(iditem) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE Numbers ADD CONSTRAINT fk_Numbers1 FOREIGN KEY (iduser, idevent) REFERENCES Users(iduser, idevent) ON UPDATE CASCADE ON DELETE CASCADE,
ADD CONSTRAINT fk_Numbers2 FOREIGN KEY (iditem) REFERENCES Questions(iditem) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE Dates ADD CONSTRAINT fk_Dates1 FOREIGN KEY (iduser, idevent) REFERENCES Users(iduser, idevent) ON UPDATE CASCADE ON DELETE CASCADE,
ADD CONSTRAINT fk_Dates2 FOREIGN KEY (iditem) REFERENCES Questions(iditem) ON UPDATE CASCADE ON DELETE CASCADE;

ALTER TABLE Blobs ADD CONSTRAINT fk_Blobs1 FOREIGN KEY (iduser, idevent) REFERENCES Users(iduser, idevent) ON UPDATE CASCADE ON DELETE CASCADE,
ADD CONSTRAINT fk_Blobs2 FOREIGN KEY (iditem) REFERENCES Questions(iditem) ON UPDATE CASCADE ON DELETE CASCADE;