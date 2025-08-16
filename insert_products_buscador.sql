select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (1, true, current_timestamp, 10, 'Smartphone de última generación con cámara de alta resolución.', 'iPhone 13 Pro',
        1299.99, 4.5, 'iPhone 13 Pro', 50, current_timestamp);
select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (2, true, current_timestamp, 10, 'Portátil ligero y potente con procesador Intel i7.', 'Dell XPS 15', 1799.99, 4.2,
        'Dell XPS 15', 100, current_timestamp);
select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (3, true, current_timestamp, 0, 'Televisor 4K con tecnología HDR y pantalla de 55 pulgadas.',
        'Samsung QLED Q60A', 999.99, 4.0, 'Samsung QLED Q60A', 25, current_timestamp);
select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (4, false, current_timestamp, 10,
        'Consola de videojuegos con gráficos de alta calidad y amplia biblioteca de juegos.', 'PlayStation 5', 499.99,
        4.7, 'PlayStation 5', 75, current_timestamp);
select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (5, true, current_timestamp, 0, 'Cámara réflex con sensor de 24 megapíxeles y grabación de video en Full HD.',
        'Canon EOS 90D', 1199.99, 4.5, 'Canon EOS 90D', 10, current_timestamp);
select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (6, true, current_timestamp, 10, 'Auriculares inalámbricos con cancelación de ruido y batería de larga duración.',
        'Bose QuietComfort 35 II', 349.99, 4.8, 'Bose QuietComfort 35 II', 200, current_timestamp);
select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (7, true, current_timestamp, 0, 'Smartwatch con monitor de frecuencia cardíaca y seguimiento de actividades.',
        'Apple Watch Series 6', 399.99, 4.6, 'Apple Watch Series 6', 30, current_timestamp);
select nextval('products_id_seq');
INSERT INTO products (id, active, created_at, discount, long_description, name, price, rating, sort_description, stock,
                      updated_at)
VALUES (8, true, current_timestamp, 10, 'Altavoz portátil resistente al agua con sonido de alta calidad.', 'JBL Flip 5',
        99.99, 4.2, 'JBL Flip 5', 150, current_timestamp);
select nextval('products_id_seq');