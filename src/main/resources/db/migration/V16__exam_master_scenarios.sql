CREATE TABLE IF NOT EXISTS troupes (id BIGINT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(60) NOT NULL UNIQUE, logo_url VARCHAR(255));
ALTER TABLE artists ADD COLUMN IF NOT EXISTS troupe_id BIGINT NULL;
ALTER TABLE artists ADD CONSTRAINT fk_artist_troupe FOREIGN KEY (troupe_id) REFERENCES troupes(id) ON UPDATE CASCADE ON DELETE RESTRICT;
INSERT INTO troupes(name,logo_url) VALUES ('Compagnie du Centre','https://via.placeholder.com/100'),('Collectif Scène','https://via.placeholder.com/100');
UPDATE artists SET troupe_id=1 WHERE id IN (1,2);

CREATE TABLE videos (id BIGINT AUTO_INCREMENT PRIMARY KEY, title VARCHAR(255), video_url VARCHAR(255) NOT NULL UNIQUE, show_id INT NOT NULL, CONSTRAINT fk_video_show FOREIGN KEY(show_id) REFERENCES shows(id) ON UPDATE CASCADE ON DELETE RESTRICT);
INSERT INTO videos(title,video_url,show_id) VALUES ('Bande-annonce Ayiti','https://www.youtube.com/embed/dQw4w9WgXcQ',1);

CREATE TABLE tags (id BIGINT AUTO_INCREMENT PRIMARY KEY, tag VARCHAR(30) NOT NULL UNIQUE);
CREATE TABLE show_tag (show_id INT NOT NULL, tag_id BIGINT NOT NULL, PRIMARY KEY(show_id,tag_id), CONSTRAINT fk_show_tag_show FOREIGN KEY(show_id) REFERENCES shows(id) ON UPDATE CASCADE ON DELETE RESTRICT, CONSTRAINT fk_show_tag_tag FOREIGN KEY(tag_id) REFERENCES tags(id) ON UPDATE CASCADE ON DELETE RESTRICT);
INSERT INTO tags(tag) VALUES ('drame'),('famille'),('humour');
INSERT INTO show_tag(show_id,tag_id) VALUES (1,1),(1,2),(2,3);

CREATE TABLE tarifs (id BIGINT AUTO_INCREMENT PRIMARY KEY, type VARCHAR(30) NOT NULL, prix DOUBLE DEFAULT 0, show_id INT NOT NULL, CONSTRAINT fk_tarif_show FOREIGN KEY(show_id) REFERENCES shows(id) ON UPDATE CASCADE ON DELETE RESTRICT);
INSERT INTO tarifs(type,prix,show_id) VALUES ('senior',7.50,1),('kids',5.00,1),('promo',6.00,2);

CREATE TABLE languages (id BIGINT AUTO_INCREMENT PRIMARY KEY, language VARCHAR(60) NOT NULL UNIQUE);
CREATE TABLE artist_language (id BIGINT AUTO_INCREMENT PRIMARY KEY, artist_id INT NOT NULL, language_id BIGINT NOT NULL, level VARCHAR(30) NOT NULL, UNIQUE KEY uq_artist_language(artist_id,language_id), CONSTRAINT fk_al_artist FOREIGN KEY(artist_id) REFERENCES artists(id) ON UPDATE CASCADE ON DELETE RESTRICT, CONSTRAINT fk_al_language FOREIGN KEY(language_id) REFERENCES languages(id) ON UPDATE CASCADE ON DELETE RESTRICT);
INSERT INTO languages(language) VALUES ('Français'),('Anglais'),('Néerlandais'),('Arabe');
INSERT INTO artist_language(artist_id,language_id,level) VALUES (1,1,'LANGUE_MATERNELLE'),(1,2,'COURANT'),(2,1,'COURANT');
UPDATE users SET password='admin' WHERE login='bob';
