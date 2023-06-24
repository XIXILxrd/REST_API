CREATE TABLE IF NOT EXISTS task
(
    id              SERIAL              PRIMARY KEY,
    name            VARCHAR(200)        NOT NULL,
    description     VARCHAR             NOT NULL,
    date            DATE                NOT NULL,
    status          BOOLEAN             NOT NULL
);




