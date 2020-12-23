CREATE DATABASE taxbook;

CREATE TYPE tax_group_enum as enum ('1', '2', '3');

CREATE TABLE acl_user (
    user_id SERIAL PRIMARY KEY,
    name CHARACTER VARYING(100) NOT NULL,
    email CHARACTER VARYING(60) NOT NULL,
    phone CHARACTER VARYING(20) NOT NULL,
    tax_group tax_group_enum NOT NULL,
    password VARCHAR(200) NOT NULL,
    salt TEXT NOT NULL,
    create_date TIMESTAMP DEFAULT NOW(),
    last_login_date TIMESTAMP,
    is_active CHAR NOT NULL /* Y or N */
);

CREATE TABLE client (
    client_id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES acl_user (user_id) NOT NULL,
    create_date TIMESTAMP DEFAULT NOW(),
    name CHARACTER VARYING(100) NOT NULL,
    contacts CHARACTER VARYING(200), /* JSON */
    edrpou CHARACTER VARYING(20),
    notes  CHARACTER VARYING(1000)
);

CREATE TABLE income_book (
    record_id BIGSERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES acl_user(user_id) NOT NULL,
    date_time TIMESTAMP NOT NULL, /* (1) дата и время поступления дохода */
    income NUMERIC(20, 2) NOT NULL, /* (2) доход в гривнах */
    refund NUMERIC(20, 2), /* (3) сумма возвращенных средств за товар/услугу */
    revised NUMERIC(20, 2), /* (4) = (2 - 3) скоректированая сумма дохода */
    free_received NUMERIC(20, 2), /* (5) стоимость безоплатно полученых услуг/работ */
    total_income NUMERIC(20, 2) NOT NULL, /* (6) = (4 + 5) */
    notes CHARACTER VARYING(1000),
    client_id INTEGER REFERENCES client(client_id),
    another_profit_type CHARACTER VARYING(100), /* (7) вид дохода что облагается в 15%  */
    another_profit_income NUMERIC(20, 2) /* (8) сумма дохода что облагается налогом в 15% */
);