CREATE TABLE rooms (
                       id BIGINT NOT NULL AUTO_INCREMENT,
                       name VARCHAR(60) NOT NULL,
                       seats SMALLINT NOT NULL,
                       location_id INT NOT NULL,
                       PRIMARY KEY (id),
                       UNIQUE (name),
                       CONSTRAINT fk_rooms_locations
                           FOREIGN KEY (location_id)
                               REFERENCES locations(id)
                               ON UPDATE CASCADE
                               ON DELETE RESTRICT,
                       CONSTRAINT chk_rooms_seats_positive
                           CHECK (seats > 0)
);