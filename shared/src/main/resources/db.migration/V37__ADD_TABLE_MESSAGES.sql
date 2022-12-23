CREATE TABLE messages
(
    id          VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255),
    client_id   VARCHAR(255),
    chat_id      VARCHAR(255),
    content VARCHAR(255) NOT NULL,
    status VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_messages PRIMARY KEY (id)
);

ALTER TABLE messages
    ADD CONSTRAINT fk_messages_on_client FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE messages
    ADD CONSTRAINT fk_messages_on_supplier FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE messages
    ADD CONSTRAINT fk_messages_on_chat FOREIGN KEY (chat_id) REFERENCES chat (id);