CREATE TABLE typologies (
                            id BIGINT NOT NULL AUTO_INCREMENT,
                            name VARCHAR(60) NOT NULL,
                            PRIMARY KEY (id),
                            CONSTRAINT uk_typologies_name UNIQUE (name)
) ENGINE=InnoDB;

INSERT INTO typologies (id, name) VALUES
                                      (1, 'Interprétation'),
                                      (2, 'Création');

ALTER TABLE types
    ADD COLUMN typology_id BIGINT NULL;

UPDATE types SET typology_id = 1 WHERE id = 1;
UPDATE types SET typology_id = 2 WHERE id IN (2, 3);

ALTER TABLE types
    MODIFY typology_id BIGINT NOT NULL;

ALTER TABLE types
    ADD CONSTRAINT fk_types_typology
        FOREIGN KEY (typology_id)
            REFERENCES typologies(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT;