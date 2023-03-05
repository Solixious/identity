CREATE TABLE IF NOT EXISTS tbl_refresh_token (
    id          SERIAL PRIMARY KEY,
    user_id     INTEGER NOT NULL,
    token       VARCHAR UNIQUE NOT NULL,
    expiry_date DATE NOT NULL default current_date + interval '1 year',
    create_date TIMESTAMP NOT NULL DEFAULT NOW()
);