CREATE TABLE IF NOT EXISTS tbl_user_roles (
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER NOT NULL,
    role_id     INTEGER NOT NULL
);