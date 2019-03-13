--
-- File generated with SQLiteStudio v3.2.1 on Thu Mar 7 19:43:02 2019
--
-- Text encoding used: UTF-8
--
PRAGMA foreign_keys = off;
BEGIN TRANSACTION;

-- Table: Author
CREATE TABLE Author (AuthorID VARCHAR PRIMARY KEY, FirstName VARCHAR, LastName VARCHAR);
INSERT INTO Author (AuthorID, FirstName, LastName) VALUES ('1', 'Harvey', 'Deitel');
INSERT INTO Author (AuthorID, FirstName, LastName) VALUES ('2', 'Paul', 'Deitel');
INSERT INTO Author (AuthorID, FirstName, LastName) VALUES ('3', 'Andrew', 'Goldberg');
INSERT INTO Author (AuthorID, FirstName, LastName) VALUES ('4', 'David', 'Choffnes');

-- Table: AuthorISBN
CREATE TABLE AuthorISBN (
    AuthorID VARCHAR NOT NULL,
    ISBN VARCHAR NOT NULL,
    FOREIGN KEY (AuthorID) references Author(AuthorID),
    FOREIGN KEY (ISBN) references Title(ISBN),
    PRIMARY KEY (AuthorID, ISBN)
);
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('1', '0131869000');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('2', '0131869000');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('1', '0132222205');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('2', '0132222205');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('1', '0131450913');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('2', '0131450913');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('1', '0131828274');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('2', '0131828274');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('3', '0131450913');
INSERT INTO AuthorISBN (AuthorID, ISBN) VALUES ('4', '0131828274');

-- Table: Title
CREATE TABLE Title (ISBN VARCHAR (10) PRIMARY KEY, Title VARCHAR NOT NULL, EditionNumber VARCHAR, Copyright VARCHAR);
INSERT INTO Title (ISBN, Title, EditionNumber, Copyright) VALUES ('0131869000', 'Visual Basic 2005 How to Program', '3', '2006');
INSERT INTO Title (ISBN, Title, EditionNumber, Copyright) VALUES ('0131525239', 'Visual C# 2005 How to Program', '2', '2006');
INSERT INTO Title (ISBN, Title, EditionNumber, Copyright) VALUES ('0132222205', 'Java How to Program', '7', '2007');
INSERT INTO Title (ISBN, Title, EditionNumber, Copyright) VALUES ('0131857576', 'C++ How to Program', '5', '2005');
INSERT INTO Title (ISBN, Title, EditionNumber, Copyright) VALUES ('0132404168', 'C How to Program', '5', '2007');
INSERT INTO Title (ISBN, Title, EditionNumber, Copyright) VALUES ('0131450913', 'Internet & World Wide Web How to Program', '3', '2004');
INSERT INTO Title (ISBN, Title, EditionNumber, Copyright) VALUES ('0131828274', 'Operating Systems', '3', '2004');

COMMIT TRANSACTION;
PRAGMA foreign_keys = on;
