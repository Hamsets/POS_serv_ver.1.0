DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS pos CASCADE;
DROP TABLE IF EXISTS checks CASCADE;
DROP TABLE IF EXISTS sold_goods CASCADE;
DROP TABLE IF EXISTS goods CASCADE;
DROP TABLE IF EXISTS versions CASCADE;

CREATE TABLE users (
    user_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    first_name VARCHAR (20) NOT NULL,
    last_name VARCHAR (20),
    sur_name VARCHAR (20),
    email VARCHAR (30) UNIQUE NOT NULL,
    "role" INT NOT NULL,
    "password" VARCHAR (20) NOT NULL,
    rating NUMERIC NOT NULL,
    deleted BOOLEAN NOT NULL
);

INSERT INTO users (first_name, last_name, sur_name, email, role, "password", rating, deleted) VALUES ('Админ',    'Админов',    'Админович',    'a@a.com', '0', '1234', '8.2',false);
INSERT INTO users (first_name, last_name, sur_name, email, role, "password", rating, deleted) VALUES ('Мэнеджер', 'Мэнеджеров', 'Мэнеджерович', 'm@m.com', '2', '1234', '7.6',false);
INSERT INTO users (first_name, last_name, sur_name, email, role, "password", rating, deleted) VALUES ('Кассир',   'Кассиров',   'Кассирович',   'k@k.com', '1', '1234', '8.2',false);


CREATE TABLE pos (
    pos_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    public_name VARCHAR (30) NOT NULL,
    address VARCHAR (100),
    deleted BOOLEAN NOT NULL);

INSERT INTO pos (public_name, address, deleted) VALUES ('Метро Партизанская', 'Проспект Партизанский',   false);
INSERT INTO pos (public_name, address, deleted) VALUES ('Метро Уручье',       'Проспект Независимости',  false);

CREATE TABLE checks (
    check_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    pos_id INT,
    user_id INT,
    sum NUMERIC NOT NULL,
    date_stamp TIMESTAMP NOT NULL,
    deleted BOOLEAN NOT NULL,
    sold_goods_str VARCHAR

    --CONSTRAINT fk_pos FOREIGN KEY (poses_id) REFERENCES pos("pos_id"),
    --CONSTRAINT fk_cashiers FOREIGN KEY (users_id) REFERENCES users("user_id")
	--, CONSTRAINT fk_goods FOREIGN KEY (goods_id_list) REFERENCES goods("goods_id")
);

CREATE TABLE sold_goods (
    sold_goods_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    goods_id INT NOT NULL,
    quantity INT NOT NULL,
    image_name VARCHAR (50),
    public_name VARCHAR (100) NOT NULL,
    path_image VARCHAR (100),
    prize NUMERIC
    --, check_id INT
    --CONSTRAINT fk_check FOREIGN KEY (check_id) REFERENCES checks (check_id)

    );

CREATE TABLE goods (
    goods_id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    for_pos INT,
    image_name VARCHAR (50),
    public_name VARCHAR (100) NOT NULL,
    path_image VARCHAR (100),
    prize NUMERIC,
    is_active BOOLEAN NOT NULL,
    deleted BOOLEAN NOT NULL);

INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i1','1. Пышка', 'i1',            0.80,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i2','1. Пышка карт', 'i2',       1.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i3','1. Пышка твор.', 'i3',      1.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i4','1. Сет 50/50', 'i4',        4.50,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i5','1. Сет карт.', 'i5',        5.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i6','1. Сет классики', 'i6',     4.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i7','1. Сет твор.', 'i7',        5.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i8','1.1 Соус', 'i8',            1.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i9','2. Айс кофе', 'i9',         4.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i10','2. Америк', 'i10',         2.30,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i11','2. Америк Х2', 'i11',      3.10,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i12','2. Какао', 'i12',          4.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i13','2. Капуч', 'i13',          2.80,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i14','2. Капуч Х2', 'i14',       3.30,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i15','2. Латте', 'i15',          3.30,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i16','2. Мокаччино', 'i16',      4.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i17','2. Пряный', 'i17',         3.10,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i18','2. Пряный Х2', 'i18',      3.80,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i19','2. Эспресс', 'i19',        2.30,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i20','2. Эспресс Х2', 'i20',     2.80,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i21','3. Бонаква', 'i21',        1.40,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i22','3. Глинт', 'i22',          4.50,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i23','3. Коктейль', 'i23',       3.50,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i24','3. Лимонад', 'i24',        4.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i25','3. Сок', 'i25',            1.50,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i26','3. Чай', 'i26',            4.50,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i27','4. Морож. посыпка', 'i27', 1.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i28','4. Морож. Топинг', 'i28',  1.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i29','4. Мороженное', 'i29',     3.00,  true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i30','5. Серт. 4 Друг', 'i30',   16.00, true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i31','5. Серт. 8 Друг', 'i31',   32.00, true, false);
INSERT INTO goods (for_pos, image_name, public_name, path_image, prize, is_active, deleted) VALUES (1, 'i32','6. Канистра', 'i32',       1.00,  true, false);

CREATE TABLE versions (
    id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY UNIQUE NOT NULL,
    ver_goods_by_pos VARCHAR (20),
    ver_app_by_pos VARCHAR (20),
    ver_app_global VARCHAR (20)
    );