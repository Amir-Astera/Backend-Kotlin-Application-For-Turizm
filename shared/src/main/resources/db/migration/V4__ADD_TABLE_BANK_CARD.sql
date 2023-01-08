CREATE TABLE bank_card
(
    id           VARCHAR(50)            NOT NULL,
    client_id    VARCHAR(255),
    card_type     VARCHAR(255)           NOT NULL,
    card_last_four VARCHAR(255)           NOT NULL,
    token        VARCHAR(255)           NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_bank_card PRIMARY KEY (id)
);

ALTER TABLE bank_card
    ADD CONSTRAINT fk_bank_card_on_client_entity FOREIGN KEY (client_id) REFERENCES client (id);

