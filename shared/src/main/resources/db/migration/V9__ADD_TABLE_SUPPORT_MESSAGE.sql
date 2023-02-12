CREATE TABLE support_messages
(
    id          VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255),
    client_id   VARCHAR(255),
    admin_id    VARCHAR(255),
    chat_id     VARCHAR(255),
    content     VARCHAR(255),
    status      VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_support_messages PRIMARY KEY (id)
);

ALTER TABLE support_messages
    ADD CONSTRAINT FK_SUPPORT_MESSAGES_ON_CHAT FOREIGN KEY (chat_id) REFERENCES chat_support (id);

ALTER TABLE support_messages
    ADD CONSTRAINT FK_SUPPORT_MESSAGES_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE support_messages
    ADD CONSTRAINT FK_SUPPORT_MESSAGES_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);

ALTER TABLE support_messages
    ADD CONSTRAINT FK_SUPPORT_MESSAGES_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES admin (id);