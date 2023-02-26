CREATE TABLE IF NOT EXISTS tbl_roles (
    id      SERIAL PRIMARY KEY,
    role    VARCHAR(16) UNIQUE NOT NULL
);

INSERT INTO tbl_roles (id, role) VALUES (0, 'ROLE_ADMIN');
INSERT INTO tbl_roles (id, role) VALUES (1, 'ROLE_USER');