ALTER TABLE article
    ADD COLUMN profession_id VARCHAR(255);

ALTER TABLE article
    ADD CONSTRAINT FK_ARTICLE_ON_PROFESSION FOREIGN KEY (profession_id) REFERENCES profession (id);