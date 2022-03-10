create table users_roles
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint FK2o0jvgh89lemvvo17cbqvdxaa
        foreign key (user_id) references users (id),
    constraint FKj6m8fwv7oqv74fcehir1a9ffy
        foreign key (role_id) references roles (id)
);

