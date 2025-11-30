ALTER TABLE product_package
    DROP FOREIGN KEY fk_propac_on_package_entity;

ALTER TABLE product_package
    DROP FOREIGN KEY fk_propac_on_product_entity;

ALTER TABLE refresh_tokens
    DROP FOREIGN KEY fk_refresh_tokens_on_user_entity;

ALTER TABLE password_reset_tokens
    DROP FOREIGN KEY password_reset_tokens_ibfk_1;

CREATE TABLE optimization
(
    id                         BIGINT AUTO_INCREMENT NOT NULL,
    solver_status              VARCHAR(255)          NOT NULL,
    termination_condition      VARCHAR(255)          NULL,
    found_solution             BIT(1)                NOT NULL,
    containers_used            INT                   NULL,
    family_penality            DOUBLE                NULL,
    gravity_center_deviation   DOUBLE                NULL,
    created_at                 datetime              NULL,
    optimization_queue_item_id BIGINT                NULL,
    CONSTRAINT pk_optimization PRIMARY KEY (id)
);

CREATE TABLE optimization_queue_items
(
    id           BIGINT AUTO_INCREMENT NOT NULL,
    status       VARCHAR(255)          NULL,
    attempts     INT                   NULL,
    request_data TEXT                  NULL,
    created_at   datetime              NOT NULL,
    updated_at   datetime              NOT NULL,
    CONSTRAINT pk_optimization_queue_items PRIMARY KEY (id)
);

ALTER TABLE shipments
    ADD created_at datetime NULL;

ALTER TABLE shipments
    MODIFY created_at datetime NOT NULL;

ALTER TABLE packages
    ADD product_id BIGINT NULL;

ALTER TABLE packages
    ADD x_position DOUBLE NULL;

ALTER TABLE packages
    ADD y_position DOUBLE NULL;

ALTER TABLE packages
    ADD z_position DOUBLE NULL;

ALTER TABLE loads
    ADD remaining_weight DOUBLE NULL;

ALTER TABLE loads
    ADD total_allocated_volume DOUBLE NULL;

ALTER TABLE loads
    ADD total_allocated_weight DOUBLE NULL;

ALTER TABLE loads
    ADD volume_occupation_percentage DOUBLE NULL;

ALTER TABLE loads
    ADD x_position DOUBLE NULL;

ALTER TABLE loads
    ADD y_position DOUBLE NULL;

ALTER TABLE loads
    ADD z_position DOUBLE NULL;

ALTER TABLE optimization
    ADD CONSTRAINT FK_OPTIMIZATION_ON_OPTIMIZATION_QUEUE_ITEM FOREIGN KEY (optimization_queue_item_id) REFERENCES optimization_queue_items (id);

ALTER TABLE packages
    ADD CONSTRAINT FK_PACKAGES_ON_PRODUCT FOREIGN KEY (product_id) REFERENCES products (id);

DROP TABLE product_package;

ALTER TABLE password_reset_tokens
    DROP COLUMN created_at;

ALTER TABLE shipments
    DROP COLUMN date;

ALTER TABLE password_reset_tokens
    MODIFY expiration datetime NULL;

ALTER TABLE users
    ALTER status SET DEFAULT 'ACTIVE';

ALTER TABLE password_reset_tokens
    MODIFY token_hash VARCHAR(255) NULL;

ALTER TABLE password_reset_tokens
    MODIFY user_id BIGINT NULL;