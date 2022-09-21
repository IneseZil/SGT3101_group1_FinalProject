CREATE TABLE users (
userId int (11) not null auto_increment,
userName varchar (45) not null,
password varchar (45) not null,
fullName varchar (45) not null,
userEmail varchar (45) not null,
userRole varchar (1) not null,
PRIMARY KEY (userId)
);

SELECT *FROM users;