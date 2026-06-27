CREATE TABLE troupes (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(60) NOT NULL UNIQUE,
                         logo_url VARCHAR(255)
);

ALTER TABLE artists
    ADD COLUMN troupe_id BIGINT NULL;

ALTER TABLE artists
    ADD CONSTRAINT fk_artist_troupe
        FOREIGN KEY (troupe_id)
            REFERENCES troupes(id)
            ON UPDATE CASCADE
            ON DELETE RESTRICT;