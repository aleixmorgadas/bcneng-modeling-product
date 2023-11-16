CREATE TABLE IF NOT EXISTS product
(
    id          SERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    category_id SERIAL       NOT NULL
);

CREATE SEQUENCE IF NOT EXISTS product_id_seq;