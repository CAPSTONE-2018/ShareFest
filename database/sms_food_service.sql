-- sms_food_service.sql -- (Re)creates the sms_food_service database

DROP DATABASE IF EXISTS sms_food_service;
CREATE DATABASE sms_food_service;
USE sms_food_service;

-- USER is a keyword (albeit not reserved in MySQL), so we're calling it user_data
CREATE TABLE user_data(
	uid			INT				PRIMARY KEY NOT NULL AUTO_INCREMENT,
	username	VARCHAR(255)	UNIQUE NOT NULL,
	password	BINARY(40)		NOT NULL, -- SHA256 + 64-bit salt
	email		VARCHAR(255)	NOT NULL,
	address		VARCHAR(255)	NOT NULL,
	zip			VARCHAR(9)		NOT NULL -- Can store ZIP+4 without dash
);

CREATE TABLE session_data(
	sid		INT			PRIMARY KEY NOT NULL AUTO_INCREMENT,
	uid		INT			NOT NULL,
	created	VARCHAR(31)	NOT NULL,
	expires	DATETIME	NOT NULL,
	
	FOREIGN KEY(uid)
		REFERENCES user_data(uid)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE business(
	bid				INT				PRIMARY KEY NOT NULL AUTO_INCREMENT,
	uid				INT				NOT NULL,
	name			VARCHAR(255)	NOT NULL,
	work_phone		VARCHAR(20)		NOT NULL, -- Can store extensions
	instructions	MEDIUMTEXT,
	
	FOREIGN KEY (uid)
		REFERENCES user_data(uid)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE client(
	cid			INT				PRIMARY KEY NOT NULL AUTO_INCREMENT,
	uid			INT				NOT NULL,
	first_name	VARCHAR(255)	NOT NULL,
	last_name	VARCHAR(255)	NOT NULL,
	cell_phone	CHAR(10)		NOT NULL, -- Stores 10 digits only
	paying		BOOLEAN			NOT NULL,
	
	FOREIGN KEY (uid)
		REFERENCES user_data(uid)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

CREATE TABLE package(
	pid			INT				PRIMARY KEY NOT NULL AUTO_INCREMENT,
	owner_bid	INT				NOT NULL,
	claimer_cid	INT,
	name		VARCHAR(31)		NOT NULL,
	description	VARCHAR(255),
	quantity	VARCHAR(31),
	price		DECIMAL(9, 2)	NOT NULL, -- 9 digits, 2 of them beyond decimal point
	created		DATETIME		NOT NULL, -- YYYY-MM-DD HH:MM:SS
	expires		DATETIME,
	claimed		DATETIME,
	received	DATETIME,
	
	FOREIGN KEY(owner_bid)
		REFERENCES business(bid)
		ON DELETE RESTRICT -- Businesses cannot be deleted
		ON UPDATE CASCADE,
	
	FOREIGN KEY(claimer_cid)
		REFERENCES client(cid)
		ON DELETE CASCADE -- For now, we're not tracking package history of deleted clients
		ON UPDATE CASCADE
);

CREATE TABLE notice(
	cid	INT	NOT NULL,
	pid	INT	NOT NULL,
	
	PRIMARY KEY(cid, pid),
	
	FOREIGN KEY(cid)
		REFERENCES client(cid)
		ON DELETE CASCADE
		ON UPDATE CASCADE,
	
	FOREIGN KEY(pid)
		REFERENCES package(pid)
		ON DELETE CASCADE
		ON UPDATE CASCADE
);

/*
	EXAMPLE DATA AND TRANSACTION
*/

-- Create a business
-- password is 'dontsteal'
INSERT INTO user_data VALUES(DEFAULT, 'testbusy',
	UNHEX('3EB8214255DF787DE5481477BABE2C553C42278FD177043C10A250D398BE22C9F9A9D00A99A37558'),
	'busy@testbusy.com', '123 Try Av.', '12345');

INSERT INTO business VALUES(DEFAULT, 1, 'Test Busy', '(123)555-5555x1234', NULL);

-- Create a client
-- password is 'pa$$word'
INSERT INTO user_data VALUES(DEFAULT, 'client_test',
	UNHEX('963B974433D487F6DF15426F4A86C811A1D968FD6D781B3A7B9CF854F11559A97B17E720EA7308C1'),
	'clienttest@gmail.com', '321 Test St.', '987654321');

INSERT INTO client VALUES(DEFAULT, 2, 'Client', 'Test', '9875551234', true);

-- Create a package
INSERT INTO package VALUES(DEFAULT, 1, NULL, 'Potatoes', 'Some potatoes.', 'Lots', 123.99,
	'1999-12-31 23:59:59', NULL, NULL, NULL);

-- Notify client of package
INSERT INTO notice VALUES(1, 1);

-- Claim package
UPDATE package SET claimer_cid = 1, claimed = '2018-03-04 21:50:57' WHERE pid = 1;

-- Delete notice
DELETE FROM notice WHERE pid = 1;

-- Receive package
UPDATE package SET received = '2018-03-04 21:53:40' WHERE pid = 1;