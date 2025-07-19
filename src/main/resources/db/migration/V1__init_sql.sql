CREATE TABLE user_roles
(
    user_id BIGINT NOT NULL,
    `role`  VARCHAR(255) NULL
);

CREATE TABLE users
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    name          VARCHAR(255) NOT NULL,
    password_hash VARCHAR(255) NULL,
    email         VARCHAR(255) NOT NULL,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE user_roles
    ADD CONSTRAINT fk_user_roles_on_user_entity FOREIGN KEY (user_id) REFERENCES users (id);