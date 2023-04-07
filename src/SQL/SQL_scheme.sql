DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    first_name VARCHAR (20) NOT NULL,
    last_name VARCHAR (20) NOT NULL,
    sur_name VARCHAR (20) NOT NULL,
    email VARCHAR (30) UNIQUE NOT NULL,
    role VARCHAR (20) NOT NULL,
    "password" VARCHAR (40) NOT NULL,
    rating NUMERIC NOT NULL
);

DROP TABLE IF EXISTS goods;
CREATE TABLE IF NOT EXISTS goods (
    goods_type BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    image_name VARCHAR (50) NOT NULL,
    public_name VARCHAR (100) NOT NULL,
    is_active BOOLEAN NOT NULL,
    deleted BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS checks;
CREATE TABLE IF NOT EXISTS checks (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    pos VARCHAR (50) NOT NULL,
    cashier_id BIGSERIAL NOT NULL,
    check_code VARCHAR (200) NOT NULL,
    sum NUMERIC NOT NULL,
    date_stamp VARCHAR (50) NOT NULL,
);
