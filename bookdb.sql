drop database dbbook;
drop user book_user;

create user book_user with password 'password';
create database dbbook with template=template0 owner=book_user;
\connect dbbook;
alter default privileges grant all on tables to book_user;
alter default privileges grant all on sequences to book_user;

create table books(
book_id integer primary key not null,
title varchar(20) not null,
author varchar(20) not null
);

create sequence book_id_seq increment 1 start 1;