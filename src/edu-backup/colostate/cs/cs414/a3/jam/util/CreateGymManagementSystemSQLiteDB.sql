DROP TABLE IF EXISTS "Addresses";
CREATE TABLE Addresses (
	PersonID		INTEGER					NOT NULL,
	Street			TEXT					NOT NULL,
	City			TEXT					NOT NULL,
	State			TEXT					NOT NULL,
	Zip      		TEXT					NOT NULL,	
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);
INSERT INTO "Addresses" VALUES(1,'1 ABC Street','MyCity1','MyState1','MyZip1');
INSERT INTO "Addresses" VALUES(2,'2 ABC Street','MyCity2','MyState2','MyZip2');
DROP TABLE IF EXISTS "Customers";
CREATE TABLE Customers (
	CustomerID		TEXT			PRIMARY KEY	NOT NULL,
	PersonID		INTEGER 				NOT NULL,
	memberstatus		TEXT 					NOT NULL,
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);
DROP TABLE IF EXISTS "Employees";
CREATE TABLE Employees (
	EmployeeID		TEXT			PRIMARY KEY	NOT NULL,
	PersonID		INTEGER,
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);
INSERT INTO "Employees" VALUES('E1',1);
INSERT INTO "Employees" VALUES('E2',2);
DROP TABLE IF EXISTS "Equipment";
CREATE TABLE Equipment (
	Name 			TEXT			PRIMARY KEY 	NOT NULL,
	Picture 		BLOB,
	Quantity 		INTEGER					NOT NULL
);
INSERT INTO "Equipment" VALUES('Stepper',NULL,3);
DROP TABLE IF EXISTS "Person";
CREATE TABLE Person (
	PersonID		INTEGER			PRIMARY KEY	AUTOINCREMENT NOT NULL,
	FirstName		TEXT					NOT NULL,
	LastName		TEXT					NOT NULL,
	Phone			TEXT,
	Email			TEXT,
	InsuranceProvider	TEXT
);
INSERT INTO "Person" VALUES(1,'EmployeeFirst1','EmployeeLast1','1111111111','xxx@1.com','UHC1');
INSERT INTO "Person" VALUES(2,'EmployeeFirst2','EmployeeLast2','2222222222','xxx@2.com','UHC2');
DROP TABLE IF EXISTS "Trainers";
CREATE TABLE Trainers (
	PersonID		INTEGER			PRIMARY KEY	NOT NULL, 
	Hours 			INTEGER		 			NOT NULL, 
	Qualifications 		TEXT,
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID),
	FOREIGN KEY (PersonID) REFERENCES Employees (PersonID)
);
INSERT INTO "Trainers" VALUES(1,40,'Eager to work1.');
INSERT INTO "Trainers" VALUES(2,40,'Eager to work2.');
DROP TABLE IF EXISTS "Users";
CREATE TABLE Users (
	UserName	TEXT				PRIMARY KEY	NOT NULL,
	Password	TEXT						NOT NULL,
	AccessLevel	TEXT						NOT NULL,
	EmployeeID	TEXT						NOT NULL,
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
);
DROP TABLE IF EXISTS "Users_Employees";
CREATE TABLE Users_Employees (
	UserName	TEXT				PRIMARY KEY	NOT NULL,
	EmployeeID	TEXT						NOT NULL,
	FOREIGN KEY (UserName) REFERENCES Users(UserName),
	FOREIGN KEY (EmployeeID) REFERENCES Employees (EmployeeID)
);
