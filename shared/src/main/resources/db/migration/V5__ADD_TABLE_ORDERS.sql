CREATE TABLE orders
(
    id           VARCHAR(50)            NOT NULL,
    status       VARCHAR(255)           NOT NULL,
    amount       FLOAT                    NOT NULL,
    discounted_amount FLOAT,
    promocode_id VARCHAR(255),
    transaction_id INT                  NOT NULL,
    appointment_id VARCHAR(255),
    card_id VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_orders PRIMARY KEY (id)
);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_on_client_entity FOREIGN KEY (card_id) REFERENCES bank_card (id);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_on_promocode_entity FOREIGN KEY (promocode_id) REFERENCES promocode (id);

ALTER TABLE orders
    ADD CONSTRAINT fk_orders_on_appointment_entity FOREIGN KEY (appointment_id) REFERENCES appointment (id);
