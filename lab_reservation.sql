/**
实验室管理系统SQL文件
**/
CREATE SCHEMA `lab_reservation` ;
use `2018214201`;

create table appointment
(
    id       bigint auto_increment
        primary key,
    uid      bigint      null,
    uname    varchar(20) null,
    cid      bigint      null,
    cname    varchar(20) null,
    lab_name varchar(20) null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

create table course
(
    id             bigint auto_increment
        primary key,
    uid            bigint      null,
    name           varchar(20) null,
    periods        int         null,
    student_number int         null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

insert into course(uid,name,periods,student_number) values(2,'JAVA',8,60);
insert into course(uid,name,periods,student_number) values(2,'Android',8,60);

create table lab
(
    id     bigint auto_increment
        primary key,
    name   varchar(20)   not null,
    number bigint        not null,
    detail varchar(1000) null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

insert into lab(name,number,detail) VALUES ('901',80,'901实验室是软件工程专业专用的实验室');
insert into lab(name,number,detail) VALUES ('902',60,'902实验室是计算机与科学技术专业专用的实验室');
insert into lab(name,number,detail) VALUES ('903',100,'903实验室是信息管理专业专用的实验室');

create table reservation_time
(
    id       bigint auto_increment
        primary key,
    week     int         not null,
    day      int         not null,
    section  int         not null,
    uid      bigint      not null,
    cid      bigint      not null,
    lab_name varchar(20) not null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

create table role
(
    id   bigint auto_increment
        primary key,
    name varchar(20) null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

insert into role(id,name) values (1,'ADMIN');
insert into role(id,name) values (2,'TEACHER');

create table user
(
    id       bigint auto_increment
        primary key,
    name     varchar(20)  null,
    username varchar(20)  not null,
    password varchar(200) not null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

insert into user(username, password, name) values ('admin','$2a$10$tkHo/pzxCSzQKcCyDln4je7FPubJSlmf2Poviier6TeQEva2KRO0G','NEFU_CHAO');
insert into user(username, password, name) values ('teacher','$2a$10$tkHo/pzxCSzQKcCyDln4je7FPubJSlmf2Poviier6TeQEva2KRO0G','NEFU_QI');
insert into user(username, password, name) values ('admin2','$2a$10$tkHo/pzxCSzQKcCyDln4je7FPubJSlmf2Poviier6TeQEva2KRO0G','NEFU_ZHANG');

create table user_role
(
    id      bigint auto_increment
        primary key,
    user_id bigint null,
    rid     bigint null,
    create_time datetime not null default current_timestamp,
    update_time datetime not null default current_timestamp on update current_timestamp
);

insert into user_role(user_id, rid) VALUES (1,1);
insert into user_role(user_id, rid) VALUES (1,2);
insert into user_role(user_id, rid) VALUES (2,2);
insert into user_role(user_id, rid) VALUES (3,2);