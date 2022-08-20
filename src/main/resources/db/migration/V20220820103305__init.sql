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
    password varchar not null
);

CREATE TABLE clients
(
    id serial primary key,
    first_name varchar not null,
    last_name varchar not null,
    patronymic varchar,
    passport varchar not null,
    marital_status_id bigint not null,
    address varchar not null,
    phone_number varchar not null,
    employment_status text not null,
    wish_credit_amount int not null,

    CONSTRAINT fk_client_marital_status FOREIGN KEY(marital_status_id) REFERENCES types(id)
);

CREATE TABLE requests
(
    id serial primary key,
    client_id bigint not null,

    CONSTRAINT fk_request_client FOREIGN KEY(client_id) REFERENCES clients(id)
);

CREATE TABLE solutions
(
    id serial primary key,
    request_id bigint not null,
    approved boolean default false,
    days_amount int not null,
    credit_amount int not null,
    sign_date date default null,
    signed boolean default false,

    CONSTRAINT fk_solution_request FOREIGN KEY(request_id) REFERENCES requests(id)
);

