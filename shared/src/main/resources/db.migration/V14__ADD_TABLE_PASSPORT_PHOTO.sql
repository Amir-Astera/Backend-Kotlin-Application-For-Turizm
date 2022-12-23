CREATE TABLE passport_photo
(
    passport_files_id    VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255) NOT NULL,
    CONSTRAINT pk_passport_photo PRIMARY KEY (passport_files_id, supplier_id)
);
ALTER TABLE passport_photo
    ADD CONSTRAINT UC_PASSPHOTO_FILES UNIQUE (passport_files_id);

ALTER TABLE passport_photo
    ADD CONSTRAINT FK_PASSPHOTO_ON_FILE FOREIGN KEY (passport_files_id) REFERENCES file (id);

ALTER TABLE passport_photo
    ADD CONSTRAINT FK_PASSPHOTO_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);
