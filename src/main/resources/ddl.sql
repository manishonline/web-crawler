create table if not exists tuser (
    userId varchar(255) not null,
    admin bit not null,
    primary key (userId)
);