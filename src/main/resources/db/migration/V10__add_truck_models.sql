CREATE TABLE truck_models
(
    id                       BIGINT AUTO_INCREMENT NOT NULL,
    name                     VARCHAR(255)          NOT NULL,
    `description`            VARCHAR(255)          NULL,
    default_maximum_capacity DOUBLE                NULL,
    default_truck_type       SMALLINT              NULL,
    created_at               datetime              NULL,
    height                   DOUBLE                NULL,
    width                    DOUBLE                NULL,
    length                   DOUBLE                NULL,
    CONSTRAINT pk_truck_models PRIMARY KEY (id)
);

ALTER TABLE trucks
    MODIFY maximum_capacity DOUBLE;