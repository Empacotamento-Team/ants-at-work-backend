CREATE TABLE fleets
(
    id                 BIGINT AUTO_INCREMENT NOT NULL,
    name               VARCHAR(255)          NOT NULL,
    codigo             VARCHAR(255)          NOT NULL,
    place_of_operation VARCHAR(255)          NULL,
    CONSTRAINT pk_fleets PRIMARY KEY (id)
);

CREATE TABLE maintenance_records
(
    id            BIGINT AUTO_INCREMENT NOT NULL,
    date          date                  NOT NULL,
    `description` VARCHAR(255)          NOT NULL,
    truck_id      BIGINT                NOT NULL,
    CONSTRAINT pk_maintenance_records PRIMARY KEY (id)
);

CREATE TABLE truck_types
(
    truck_id BIGINT       NOT NULL,
    type     VARCHAR(255) NULL
);

CREATE TABLE trucks
(
    id               BIGINT AUTO_INCREMENT            NOT NULL,
    plate            VARCHAR(7)                       NOT NULL,
    maximum_capacity INT                              NOT NULL,
    status           VARCHAR(255) DEFAULT 'AVAILABLE' NOT NULL,
    last_revision    date                             NOT NULL,
    current_mileage  FLOAT                            NOT NULL,
    details          VARCHAR(255)                     NOT NULL,
    fleet_id         BIGINT                           NULL,
    internal_height  DOUBLE                           NULL,
    internal_width   DOUBLE                           NULL,
    internal_length  DOUBLE                           NULL,
    CONSTRAINT pk_trucks PRIMARY KEY (id)
);

ALTER TABLE fleets
    ADD CONSTRAINT uc_fleets_codigo UNIQUE (codigo);

ALTER TABLE trucks
    ADD CONSTRAINT uc_trucks_plate UNIQUE (plate);

ALTER TABLE maintenance_records
    ADD CONSTRAINT FK_MAINTENANCE_RECORDS_ON_TRUCK FOREIGN KEY (truck_id) REFERENCES trucks (id);

ALTER TABLE trucks
    ADD CONSTRAINT FK_TRUCKS_ON_FLEET FOREIGN KEY (fleet_id) REFERENCES fleets (id);

ALTER TABLE truck_types
    ADD CONSTRAINT fk_truck_types_on_truck_entity FOREIGN KEY (truck_id) REFERENCES trucks (id);