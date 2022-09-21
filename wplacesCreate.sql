CREATE TABLE workplaces (
wplaceID varchar (8) not null,
floor int (1) not null,
room int (2) not null,
deskID int (3) not null,
occupied varchar (1),
dateFrom varchar (10),
dateTo varchar (10),
userID int REFERENCES users(userID),
PRIMARY KEY (wplaceID)
);

INSERT INTO workplaces (wplaceID, floor, room, deskID)
VALUES (122124, 1,22,123);
INSERT INTO workplaces (wplaceID, floor, room, deskID)
VALUES (111333, 1,11,333);
INSERT INTO workplaces (wplaceID, floor, room, deskID)
VALUES (111555, 1,11,555);
INSERT INTO workplaces (wplaceID, floor, room, deskID)
VALUES (111666, 1,11,666);
INSERT INTO workplaces (wplaceID, floor, room, deskID)
VALUES (111777, 1,11,777);

SELECT * FROM workplaces;