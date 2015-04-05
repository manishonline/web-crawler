create table if not exists VISITEDURL (
    DOMAIN numeric not null,
    URI numeric not null,
    primary key (DOMAIN,URI)
);

create table if not exists ROBOTSTXT_DISALLOWED (
    DOMAIN varchar(255) not null,
    URI VARCHAR(255) not null,
    primary key (DOMAIN,URI)
);

create table if not exists DOMAIN_LASTVISITED (
    DOMAIN varchar(255) not null,
    LASTVISITED DATETIME not null,
    primary key (DOMAIN)
);