CREATE TABLE IF NOT EXISTS tbl_users (
    id              SERIAL PRIMARY KEY,
    email           VARCHAR(64) UNIQUE NOT NULL,
    first_name      VARCHAR(32) NOT NULL,
    surname         VARCHAR(32),
    date_of_birth   DATE NOT NULL,
    password        VARCHAR(255) NOT NULL,
    create_date     TIMESTAMP NOT NULL DEFAULT NOW(),
    update_date     TIMESTAMP NOT NULL DEFAULT NOW()
);