CREATE TABLE favorite_supplier
(
    id VARCHAR (255) NOT NULL,
    favorite_supplier_id VARCHAR(255) NOT NULL,
    client_id    VARCHAR(255) NOT NULL,
    CONSTRAINT pk_favorites PRIMARY KEY (id)
);

ALTER TABLE favorite_supplier
    ADD CONSTRAINT FK_FAVORITES_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE favorite_supplier
    ADD CONSTRAINT FK_FAVORITES_ON_SUPPLIER FOREIGN KEY (favorite_supplier_id) REFERENCES supplier (id);