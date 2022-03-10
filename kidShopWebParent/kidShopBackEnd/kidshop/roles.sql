create table roles
(
    id          int auto_increment
        primary key,
    description varchar(150) not null,
    name        varchar(40)  not null,
    constraint UK_ofx66keruapi6vyqpv6f2or37
        unique (name)
);

