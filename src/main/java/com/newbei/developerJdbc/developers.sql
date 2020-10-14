drop database if exists developers;

create database if not exists developers;
use developers;

create table if not exists developer (
	id int primary key auto_increment,
	name varchar(255),
	accountId int
);

create table if not exists skills(
id int primary key auto_increment,
name varchar(255)
);
    
create table if not exists skillsDeveloper(
	id int primary key auto_increment,
	developerId int,
    skillId int
    );

create table if not exists account(
	id int primary key auto_increment,
    login varchar(255),
    password varchar(255),
    account_status varchar(255)
);

/* Creation date Skills*/
insert into skills(name) values ('JavaCore');
insert into skills(name) values ('JavaEE');
insert into skills(name) values ('Php');
insert into skills(name) values ('C++');
insert into skills(name) values ('Python');
insert into skills(name) values ('C#');
insert into skills(name) values ('HTML5');
insert into skills(name) values ('CSS3');
insert into skills(name) values ('WordPress');
insert into skills(name) values ('Android');
insert into skills(name) values ('Vue');
insert into skills(name) values ('Angular');
insert into skills(name) values ('React');

/* Creation date Account*/
insert into account(login, password, account_status) values ('leon','killer','ACTIVE');

/* Creation date Developer*/
insert into developer(name, accountId) values ('LeonKiller', 1);

insert into skillsDeveloper(developerId, skillId) value (1,1);
insert into skillsDeveloper(developerId, skillId) value (1,2);
insert into skillsDeveloper(developerId, skillId) value (1,7);