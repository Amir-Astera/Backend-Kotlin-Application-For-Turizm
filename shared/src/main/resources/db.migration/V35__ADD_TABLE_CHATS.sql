CREATE TABLE chat_support
(
    id          VARCHAR(255) NOT NULL,
    client_id   VARCHAR(255),
    supplier_id VARCHAR(255),
    authority   VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_chat_support PRIMARY KEY (id)
);

ALTER TABLE chat_support
    ADD CONSTRAINT fk_chat_support_on_client FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE chat_support
    ADD CONSTRAINT fk_chat_support_on_supplier FOREIGN KEY (supplier_id) REFERENCES supplier (id);

-- chat-supplier --
CREATE TABLE chat
(
    id          VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255),
    client_id   VARCHAR(255),
    archive_status VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_chat PRIMARY KEY (id)
);

ALTER TABLE chat
    ADD CONSTRAINT fk_chat_on_client FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE chat
    ADD CONSTRAINT fk_chat_on_supplier FOREIGN KEY (supplier_id) REFERENCES supplier (id);