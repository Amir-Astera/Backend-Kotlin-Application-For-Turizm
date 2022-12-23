CREATE TABLE calendar
(
    id                  VARCHAR(255) NOT NULL,
    supplier_id         VARCHAR(255) NOT NULL,
    first_day_of_month  DATE NOT NULL,
    working_days        DATE[] NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_working_days PRIMARY KEY (id, supplier_id)
);
