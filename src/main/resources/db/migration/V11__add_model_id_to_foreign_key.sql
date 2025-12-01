ALTER TABLE trucks
    ADD model_id BIGINT NULL;

ALTER TABLE trucks
    ADD CONSTRAINT FK_TRUCKS_ON_MODEL FOREIGN KEY (model_id) REFERENCES truck_models (id);

UPDATE trucks
SET maximum_capacity = '0'
WHERE maximum_capacity IS NULL;
ALTER TABLE trucks
    MODIFY maximum_capacity DOUBLE NOT NULL;