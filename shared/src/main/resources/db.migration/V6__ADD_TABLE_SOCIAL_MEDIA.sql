CREATE TABLE social_media
(
    id              VARCHAR(255) NOT NULL,
    linkedin        VARCHAR(255),
    facebook        VARCHAR(255),
    instagram       VARCHAR(255),
    youtube         VARCHAR(255),
    tiktok          VARCHAR(255),
    telegram        VARCHAR(255),
    twitter         VARCHAR(255),
    vk              VARCHAR(255),
    CONSTRAINT pk_social_media PRIMARY KEY (id)
);

ALTER TABLE supplier ADD COLUMN social_media VARCHAR(255);
