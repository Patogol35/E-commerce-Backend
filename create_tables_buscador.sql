-- create table products
create table products
(
    id               bigserial primary key,
    active           boolean not null,
    created_at       timestamp(6),
    discount         integer,
    long_description varchar(255),
    name             varchar(255),
    price            numeric(38, 2),
    rating           double precision,
    sort_description varchar(255),
    stock            integer,
    updated_at       timestamp(6)
);
alter table products owner to postgres;

-- create table categories
create table categories
(
    id          bigserial primary key,
    created_at  timestamp(6),
    description varchar(255),
    name        varchar(255),
    updated_at  timestamp(6)
);
alter table categories owner to postgres;

-- create table images
create table images
(
    id         bigserial primary key,
    file_name  varchar(255),
    name       varchar(255),
    image      bytea not null,
    type       varchar(255),
    product_id bigint constraint fk_product_id references products
);
alter table images owner to postgres;

-- create table products_categories
create table products_categories
(
    product_id  bigint not null constraint fk_product_id references products,
    category_id bigint not null constraint fk_category_id references categories
);
alter table products_categories owner to postgres;