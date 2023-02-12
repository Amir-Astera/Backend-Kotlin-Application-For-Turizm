DROP TABLE IF EXISTS notification;

CREATE TABLE notification
(
    id          VARCHAR(50)  NOT NULL,
    supplier_id      VARCHAR(255) NOT NULL,
    client_id        VARCHAR(255) NOT NULL,
    status TEXT NOT NULL,
    appointment_datetime  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

ALTER TABLE notification
    ADD CONSTRAINT FK_APPOINTMENT_ON_CLIENT FOREIGN KEY (client_id) REFERENCES client (id);

ALTER TABLE notification
    ADD CONSTRAINT FK_APPOINTMENT_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);