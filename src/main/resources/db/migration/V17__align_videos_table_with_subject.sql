UPDATE videos
SET video_url = 'https://youtu.be/dQw4w9WgXcQ'
WHERE id = 1;

ALTER TABLE videos
    MODIFY title VARCHAR(255) NOT NULL,
    MODIFY video_url VARCHAR(30) NOT NULL;
