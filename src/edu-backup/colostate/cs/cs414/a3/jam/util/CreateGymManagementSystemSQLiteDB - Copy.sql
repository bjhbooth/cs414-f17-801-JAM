DROP TABLE IF EXISTS Users;
DROP TABLE IF EXISTS Person;
DROP TABLE IF EXISTS Employees;
DROP TABLE IF EXISTS Trainers;
DROP TABLE IF EXISTS Customers;
DROP TABLE IF EXISTS Addresses;

CREATE TABLE Users (
	UserName	TEXT				PRIMARY KEY	NOT NULL,
	Password	TEXT						NOT NULL,
	AccessLevel	TEXT						NOT NULL,
	EmployeeID	TEXT						NOT NULL,
	FOREIGN KEY (EmployeeID) REFERENCES Employees(EmployeeID)
);

CREATE TABLE Person (
	PersonID		INTEGER			PRIMARY KEY	AUTOINCREMENT NOT NULL,
	FirstName		TEXT					NOT NULL,
	LastName		TEXT					NOT NULL,
	Phone			TEXT,
	Email			TEXT,
	InsuranceProvider	TEXT
);

CREATE TABLE Employees (
	EmployeeID		TEXT			PRIMARY KEY	NOT NULL,
	PersonID		INTEGER,
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);

CREATE TABLE Trainers (
	PersonID		INTEGER			PRIMARY KEY	NOT NULL, 
	Hours 			INTEGER		 			NOT NULL, 
	Qualifications 		TEXT,
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID),
	FOREIGN KEY (PersonID) REFERENCES Employees (PersonID)
);

CREATE TABLE Customers (
	CustomerID		TEXT			PRIMARY KEY	NOT NULL,
	PersonID		INTEGER 				NOT NULL,
	memberstatus		TEXT 					NOT NULL,
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);

CREATE TABLE Addresses (
	PersonID		INTEGER					NOT NULL,
	Street			TEXT					NOT NULL,
	City			TEXT					NOT NULL,
	State			TEXT					NOT NULL,
	Zip      		TEXT					NOT NULL,	
	FOREIGN KEY (PersonID) REFERENCES Person(PersonID)
);

CREATE TABLE Users_Employees (
	UserName	TEXT				PRIMARY KEY	NOT NULL,
	EmployeeID	TEXT						NOT NULL,
	FOREIGN KEY (UserNane) REFERENCES Users(UserName),
	FOREIGN KEY (EmployeeID) REFERENCES Employees (EmployeeID)
);

CREATE TABLE Equipment {
	Name 			TEXT			PRIMARY KEY 	NOT NULL,
	Picture 		BLOB,
	Quantity 		INTEGER					NOT NULL
};
 
INSERT INTO Person (PersonID, FirstName, LastName, Phone, Email, InsuranceProvider) VALUES (1, 'EmployeeFirst1', 'EmployeeLast1', '1111111111', 'xxx@1.com', 'UHC1');
INSERT INTO Employees (EmployeeID, PersonID) VALUES ('E1', 1);
INSERT INTO Trainers (PersonID, Hours, Qualifications) VALUES (1, 40, "Eager to work1."); 
INSERT INTO Addresses (PersonID, Street, City, State, Zip) VALUES (1, '1 ABC Street', 'MyCity1', 'MyState1', 'MyZip1');
INSERT INTO Person (PersonID, FirstName, LastName, Phone, Email, InsuranceProvider) VALUES (2, 'EmployeeFirst2', 'EmployeeLast2', '2222222222', 'xxx@2.com', 'UHC2');
INSERT INTO Employees (EmployeeID, PersonID) VALUES ('E2', 2);
INSERT INTO Trainers (PersonID, Hours, Qualifications) VALUES (2, 40, "Eager to work2."); 
INSERT INTO Addresses (PersonID, Street, City, State, Zip) VALUES (2, '2 ABC Street', 'MyCity2', 'MyState2', 'MyZip2');
INSERT INTO Equipment (Name, Quantity) VALUES ('Stepper', 3);
