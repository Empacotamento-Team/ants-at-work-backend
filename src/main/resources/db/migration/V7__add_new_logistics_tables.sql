CREATE TABLE loads
(
    id          BIGINT AUTO_INCREMENT NOT NULL,
    truck_id    BIGINT                NULL,
    shipment_id BIGINT                NULL,
    CONSTRAINT pk_loads PRIMARY KEY (id)
);

CREATE TABLE packages
(
    id               BIGINT AUTO_INCREMENT NOT NULL,
    packaging_id     BIGINT                NULL,
    load_id          BIGINT                NULL,
    supported_weight DOUBLE                NULL,
    CONSTRAINT pk_packages PRIMARY KEY (id)
);

CREATE TABLE packagings
(
    id              BIGINT AUTO_INCREMENT NOT NULL,
    name            VARCHAR(255)          NOT NULL,
    `description`   VARCHAR(255)          NULL,
    internal_height DOUBLE                NULL,
    internal_width  DOUBLE                NULL,
    internal_length DOUBLE                NULL,
    CONSTRAINT pk_packagings PRIMARY KEY (id)
);

CREATE TABLE product_families
(
    id                           BIGINT AUTO_INCREMENT NOT NULL,
    name                         VARCHAR(255)          NOT NULL,
    default_max_supported_weight DOUBLE                NOT NULL,
    `description`                VARCHAR(255)          NULL,
    CONSTRAINT pk_product_families PRIMARY KEY (id)
);

CREATE TABLE product_package
(
    package_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL
);

CREATE TABLE products
(
    id                   BIGINT AUTO_INCREMENT NOT NULL,
    name                 VARCHAR(255)          NOT NULL,
    family_id            BIGINT                NOT NULL,
    weight               DOUBLE                NOT NULL,
    fragile              BIT(1)                NOT NULL,
    max_supported_weight DOUBLE                NULL,
    batch                VARCHAR(255)          NULL,
    height               DOUBLE                NULL,
    width                DOUBLE                NULL,
    length               DOUBLE                NULL,
    CONSTRAINT pk_products PRIMARY KEY (id)
);

CREATE TABLE shipments
(
    id   BIGINT AUTO_INCREMENT NOT NULL,
    date datetime              NOT NULL,
    CONSTRAINT pk_shipments PRIMARY KEY (id)
);

ALTER TABLE loads
    ADD CONSTRAINT FK_LOADS_ON_SHIPMENT FOREIGN KEY (shipment_id) REFERENCES shipments (id);

ALTER TABLE loads
    ADD CONSTRAINT FK_LOADS_ON_TRUCK FOREIGN KEY (truck_id) REFERENCES trucks (id);

ALTER TABLE packages
    ADD CONSTRAINT FK_PACKAGES_ON_LOAD FOREIGN KEY (load_id) REFERENCES loads (id);

ALTER TABLE packages
    ADD CONSTRAINT FK_PACKAGES_ON_PACKAGING FOREIGN KEY (packaging_id) REFERENCES packagings (id);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_FAMILY FOREIGN KEY (family_id) REFERENCES product_families (id);

ALTER TABLE product_package
    ADD CONSTRAINT fk_propac_on_package_entity FOREIGN KEY (package_id) REFERENCES packages (id);

ALTER TABLE product_package
    ADD CONSTRAINT fk_propac_on_product_entity FOREIGN KEY (product_id) REFERENCES products (id);