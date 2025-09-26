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
    goods_type SERIAL PRIMARY KEY UNIQUE NOT NULL,
    image_name VARCHAR (50),
    public_name VARCHAR (100) NOT NULL,
    path_image VARCHAR (100) NOT NULL,
    prize NUMERIC,
    is_active BOOLEAN NOT NULL,
    for_pos VARCHAR (50),
    deleted BOOLEAN NOT NULL
);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i1','1. Пышка', 'i1', 0.80, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i2','1. Пышка карт', 'i2', 1.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i3','1. Пышка твор.', 'i3', 1.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i4','1. Сет 50/50', 'i4', 4.50, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i5','1. Сет карт.', 'i5', 5.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i6','1. Сет классики', 'i6', 4.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i7','1. Сет твор.', 'i7', 5.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i8','1.1 Соус', 'i8', 1.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i9','2. Айс кофе', 'i9', 4.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i10','2. Америк', 'i10', 2.30, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i11','2. Америк Х2', 'i11', 3.10, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i12','2. Какао', 'i12', 4.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i13','2. Капуч', 'i13', 2.80, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i14','2. Капуч Х2', 'i14', 3.30, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i15','2. Латте', 'i15', 3.30, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i16','2. Мокаччино', 'i16', 4.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i17','2. Пряный', 'i17', 3.10, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i18','2. Пряный Х2', 'i18', 3.80, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i19','2. Эспресс', 'i19', 2.30, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i20','2. Эспресс Х2', 'i20', 2.80, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i21','3. Бонаква', 'i21', 1.40, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i22','3. Глинт', 'i22', 4.50, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i23','3. Коктейль', 'i23', 3.50, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i24','3. Лимонад', 'i24', 4.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i25','3. Сок', 'i25', 1.50, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i26','3. Чай', 'i26', 4.50, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i27','4. Морож. посыпка', 'i27', 1.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i28','4. Морож. Топинг', 'i28', 1.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i29','4. Мороженное', 'i29', 3.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i30','5. Серт. 4 Друг', 'i30', 16.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i31','5. Серт. 8 Друг', 'i31', 32.00, true, 'part',false);
INSERT INTO goods (image_name, public_name, path_image, prize, is_active, for_pos, deleted) VALUES ('i32','6. Канистра', 'i32', 1.00, true, 'part',false);


DROP TABLE IF EXISTS checks;
CREATE TABLE IF NOT EXISTS checks (
    id BIGSERIAL PRIMARY KEY UNIQUE NOT NULL,
    pos VARCHAR (50) NOT NULL,
    cashier_id BIGSERIAL NOT NULL,
    check_code TEXT NOT NULL,
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