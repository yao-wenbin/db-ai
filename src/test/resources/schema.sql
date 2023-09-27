drop table if exists user;
create table user
(
    id       bigint auto_increment,
    username varchar(255) default '' not null,
    email    varchar(255) default '' not null,
    password varchar(255) default '' not null,
    address  varchar(255) default '' not null,
    constraint user_pk
        primary key (id)
);
drop table if exists product;
create table product
(
    id       bigint auto_increment,
    name varchar(255) default '' not null,
    description    varchar(255) default '' not null,
    price decimal(10, 2) default 0 not null,
    constraint product_pk
        primary key (id)
);