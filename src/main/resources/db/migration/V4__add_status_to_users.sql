ALTER TABLE users
    ADD status VARCHAR(255) DEFAULT 'ACTIVE' NULL;

UPDATE users
    SET users.status = 'ACTIVE'
    WHERE users.status IS NULL;

ALTER TABLE users
    MODIFY status VARCHAR (255) NOT NULL;
