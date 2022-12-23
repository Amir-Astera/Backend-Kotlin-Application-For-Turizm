CREATE TABLE stories
(
    id          VARCHAR(50)     NOT NULL,
    title       VARCHAR(255),
    priority    INTEGER         NOT NULL,
    html_content TEXT           NOT NULL,
    article_id  VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_stories PRIMARY KEY (id)
);

ALTER TABLE stories
    ADD CONSTRAINT fk_stories_on_article_entity FOREIGN KEY (article_id) REFERENCES article (id);

-- stories - files relation --
CREATE TABLE stories_files
(
    stories_id VARCHAR(50)  NOT NULL,
    files_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_stories_files PRIMARY KEY (stories_id, files_id)
);

ALTER TABLE stories_files
    ADD CONSTRAINT uc_stories_files_files UNIQUE (files_id);

ALTER TABLE stories_files
    ADD CONSTRAINT fk_stories_files_on_stories_entity FOREIGN KEY (stories_id) REFERENCES stories (id);

ALTER TABLE stories_files
    ADD CONSTRAINT fk_stories_files_on_file_entity FOREIGN KEY (files_id) REFERENCES file (id);

-- stories - supplier relation --
CREATE TABLE stories_supplier
(
    stories_id VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255) NOT NULL
);

ALTER TABLE stories_supplier
    ADD CONSTRAINT fk_stories_supplier_on_stories FOREIGN KEY (stories_id) REFERENCES stories (id);

ALTER TABLE stories_supplier
    ADD CONSTRAINT fk_stories_supplier_on_supplier FOREIGN KEY (supplier_id) REFERENCES supplier (id);
