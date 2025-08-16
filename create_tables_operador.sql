-- create table shopping_carts
create table shopping_carts
(
    id                 bigserial primary key,
    client             varchar(255),
    created_at         timestamp(6),
    description        varchar(255),
    status             integer,
    status_description varchar(255),
    total              numeric(38, 2)
);

alter table shopping_carts owner to postgres;

-- create table items
create table items
(
    id         bigserial primary key,
    id_product bigint,
    product    varchar(255),
    created_at timestamp(6),
    quantity   integer,
    subtotal   numeric(38, 2)
);

alter table items owner to postgres;

-- create table shopping_cart_items
create table shopping_carts_items
(
    shopping_cart_id bigint not null constraint fk_shopping_cart_id references shopping_carts,
    items_id         bigint not null constraint uk_items_id_unique unique constraint fk_item_id references items
);

alter table shopping_carts_items owner to postgres;