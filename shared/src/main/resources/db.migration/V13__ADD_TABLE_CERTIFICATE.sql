CREATE TABLE certificate
(
    id               VARCHAR(255) NOT NULL,
    title            VARCHAR(255),
    issue_date       TIMESTAMP WITHOUT TIME ZONE,
    specialization   VARCHAR(255),
    file_id          VARCHAR(255),
    supplier_id      VARCHAR(255),
    CONSTRAINT pk_certificate PRIMARY KEY (id)
);

ALTER TABLE certificate
    ADD CONSTRAINT FK_CERTIFICATE_ON_FILE FOREIGN KEY (file_id) REFERENCES file (id);

ALTER TABLE certificate
    ADD CONSTRAINT FK_CERTIFICATE_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);