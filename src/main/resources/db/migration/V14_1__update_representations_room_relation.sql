ALTER TABLE representations
    ADD COLUMN room_id BIGINT NULL;

UPDATE representations SET room_id = 1 WHERE id = 1;
UPDATE representations SET room_id = 2 WHERE id = 2;
UPDATE representations SET room_id = 3 WHERE id = 3;
UPDATE representations SET room_id = 4 WHERE id = 4;

ALTER TABLE representations
    MODIFY room_id BIGINT NOT NULL;

ALTER TABLE representations
    ADD CONSTRAINT fk_representations_rooms
        FOREIGN KEY (room_id)
            REFERENCES rooms(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT;

ALTER TABLE representations
DROP COLUMN location_id;