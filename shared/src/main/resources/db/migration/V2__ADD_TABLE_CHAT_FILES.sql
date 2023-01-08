CREATE TABLE message_files
(
    message_id VARCHAR(50)  NOT NULL,
    file_id   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_message_files PRIMARY KEY (message_id, file_id)
);

ALTER TABLE message_files
    ADD CONSTRAINT fk_message_files_on_message_entity FOREIGN KEY (message_id) REFERENCES messages (id);

ALTER TABLE message_files
    ADD CONSTRAINT fk_message_files_on_file_entity FOREIGN KEY (file_id) REFERENCES file (id);
