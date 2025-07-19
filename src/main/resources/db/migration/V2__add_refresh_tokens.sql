CREATE TABLE refresh_tokens
(
    id         BIGINT AUTO_INCREMENT NOT NULL,
    user_id    BIGINT NULL,
    token_hash VARCHAR(255) NULL,
    expiration datetime NULL,
    CONSTRAINT pk_refresh_tokens PRIMARY KEY (id)
);

ALTER TABLE refresh_tokens
    ADD CONSTRAINT fk_refresh_tokens_on_user_entity FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;