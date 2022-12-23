CREATE TABLE promocode_client_activated
(
    id VARCHAR (255) NOT NULL,
    promocode_id VARCHAR(255) NOT NULL,
    client_id    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_promocode_client_activated PRIMARY KEY (id)
);

ALTER TABLE promocode_client_activated
    ADD CONSTRAINT FK_PROMOCODE_ACTIVATED_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE promocode_client_activated
    ADD CONSTRAINT FK_PROMOCODE_ACTIVATED_ON_PROMOCODE FOREIGN KEY (promocode_id) REFERENCES promocode (id);