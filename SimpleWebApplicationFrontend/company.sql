Create table Customer(
userId varchar(12),
userName varchar (20) NOT NULL,
address varchar (30),
email varchar (20) NOT NULL ,
telephoneNumber varchar (12),
password varchar(500)  NOT NULL ,
createTime varchar (100),
createUser varchar (20),
lastUpdateTime varchar (20),
lastUpdateUser varchar (20),
CONSTRAINT primary key (userId),
CONSTRAINT UNIQUE KEY (userName),
CONSTRAINT UNIQUE KEY (telephoneNumber),
CONSTRAINT UNIQUE KEY (email)
);

insert into Customer (userId, userName, address, email, telephoneNumber, password) values("C001","Amila","Gampaha","Amila123@gmail.com","0703410462","Amila@123")
