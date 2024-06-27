DROP TABLE IF EXISTS users;
CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY UNIQUE NOT NULL,
    first_name VARCHAR (20) NOT NULL,
    last_name VARCHAR (20) NOT NULL,
    sur_name VARCHAR (20) NOT NULL,
    email VARCHAR (30) UNIQUE NOT NULL,
    role VARCHAR (20) NOT NULL,
    "password" VARCHAR (40) NOT NULL,
    rating NUMERIC NOT NULL,
    deleted BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS goods;
CREATE TABLE IF NOT EXISTS goods (
    goods_type BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    image_name VARCHAR (50),
    public_name VARCHAR (100) NOT NULL,
    path_image VARCHAR (100) NOT NULL,
    prize INTEGER,
    is_active BOOLEAN NOT NULL,
    for_pos VARCHAR (50),
    deleted BOOLEAN NOT NULL
);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1. Пышка', 'i1', 80, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1. Пышка карт', 'i2', 100, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1. Пышка твор.', 'i3', 100, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1. Сет 50/50', 'i4', 450, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1. Сет карт.', 'i5', 500, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1. Сет классики', 'i6', 400, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1. Сет твор.', 'i7', 500, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('1.1 Соус', 'i8', 100, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Айс кофе', 'i9', 400, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Америк', 'i10', 230, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Америк Х2', 'i11', 310, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Какао', 'i12', 400, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Капуч', 'i13', 280, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Капуч Х2', 'i14', 330, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Латте', 'i15', 330, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Мокаччино', 'i16', 400, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Пряный', 'i17', 310, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Пряный Х2', 'i18', 380, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Эспресс', 'i19', 230, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('2. Эспресс Х2', 'i20', 280, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('3. Бонаква', 'i21', 140, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('3. Глинт', 'i22', 450, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('3. Коктейль', 'i23', 350, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('3. Лимонад', 'i24', 400, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('3. Сок', 'i25', 150, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('3. Чай', 'i26', 450, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('4. Морож. посыпка', 'i27', 100, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('4. Морож. Топинг', 'i28', 100, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('4. Мороженное', 'i29', 300, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('5. Серт. 4 Друг', 'i30', 1600, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('5. Серт. 8 Друг', 'i31', 3200, true, 'part',false);
INSERT INTO goods (public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('6. Канистра', 'i32', 100, true, 'part',false);


DROP TABLE IF EXISTS checks;
CREATE TABLE IF NOT EXISTS checks (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    pos VARCHAR (50) NOT NULL,
    cashier_id BIGSERIAL NOT NULL,
    check_code VARCHAR (200) NOT NULL,
    sum NUMERIC NOT NULL,
    date_stamp TIMESTAMP NOT NULL UNIQUE,
    deleted BOOLEAN NOT NULL
);

DROP TABLE IF EXISTS versions;
CREATE TABLE IF NOT EXISTS versions (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    ver_goods_by_pos VARCHAR (20) NOT NULL,
    ver_app_by_pos VARCHAR (20) NOT NULL,
    ver_app_global VARCHAR (20) NOT NULL
);

select * from users;