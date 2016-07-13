CREATE TABLE Questions (
	ID INT,
	Value VARCHAR(1024),
	isRequired BOOLEAN,
	isActive BOOLEAN,
	PRIMARY KEY (ID)
);

CREATE TABLE Answers (
	ID INT,
	Value VARCHAR(4096),
	PRIMARY KEY (ID)
);

CREATE TABLE DefaultAnswers (
	QuestionID INT,
	Value VARCHAR(1024),
	PRIMARY KEY (QuestionID, Value)
);

# Таблица анкет, хранит номера всех ответов и всех вопросов
CREATE TABLE Forms (
	ID INT,
	QuestionID INT,
	AnswerID INT,
	PRIMARY KEY (ID, QuestionID),
	FOREIGN KEY (QuestionID) REFERENCES Questions(ID),
	FOREIGN KEY (AnswerID) REFERENCES Answers(ID)
);

CREATE TABLE Admin (
	EMail VARCHAR(256),
	PRIMARY KEY (EMail)
);