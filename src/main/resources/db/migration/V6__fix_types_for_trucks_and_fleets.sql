ALTER TABLE truck_types
    DROP FOREIGN KEY fk_truck_types_on_truck_entity;

ALTER TABLE trucks
    ADD type VARCHAR(255) NULL;

DROP TABLE truck_types;

ALTER TABLE trucks
    DROP COLUMN current_mileage;

ALTER TABLE trucks
    ADD current_mileage DOUBLE NOT NULL;

ALTER TABLE trucks
    MODIFY details VARCHAR(255) NULL;

ALTER TABLE trucks
    MODIFY last_revision date NULL;