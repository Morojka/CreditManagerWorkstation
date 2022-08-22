CREATE TABLE types
(
    id serial primary key,
    name varchar unique not null,
    type varchar not null
);

INSERT INTO types(name, type) VALUES ('Женат/Замужем', 'MARITAL_STATUS');
INSERT INTO types(name, type) VALUES ('Холост/Не замужем', 'MARITAL_STATUS');

CREATE TABLE users
(
    id serial primary key,
    login varchar unique not null,
    password varchar not null,
    type varchar not null default 'USER'
);

INSERT INTO users(login, password, type) VALUES ('admin', '$2a$12$WySyoao2PW6BoF9Z.01VJObcfcYqvZim60pEtSqf6w52JFUQO.kAi', 'ADMIN');

CREATE TABLE requests
(
    id serial primary key,
    user_id bigint not null,
    first_name varchar not null,
    last_name varchar not null,
    patronymic varchar,
    passport varchar not null,
    marital_status_id bigint not null,
    address varchar not null,
    phone_number varchar not null,
    employment_status text not null,
    wish_credit_amount int not null,

    CONSTRAINT fk_request_user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT fk_request_marital_status FOREIGN KEY(marital_status_id) REFERENCES types(id)
);

CREATE TABLE solutions
(
    id serial primary key,
    request_id bigint not null,
    approved boolean default null,
    days_amount int default null,
    credit_amount int default null,
    sign_date date default null,
    signed boolean default null,

    CONSTRAINT fk_solution_request FOREIGN KEY(request_id) REFERENCES requests(id)
);

