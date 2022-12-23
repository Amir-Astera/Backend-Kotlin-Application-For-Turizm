DROP TABLE IF EXISTS promocode CASCADE;
CREATE TABLE promocode
(
    id                     VARCHAR(255) NOT NULL,
    code                   VARCHAR(255) NOT NULL,
    description            VARCHAR(255),
    discount_type          VARCHAR(255) NOT NULL,
    discount_amount        INTEGER,
    discount_percentage    FLOAT,
    validity_from          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    validity_to            TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    status                 VARCHAR(255) NOT NULL,
    activation_limit       INTEGER      NOT NULL,
    activated_amount       INTEGER      NOT NULL,
    total_attempts         INTEGER      NOT NULL,
    supplier_id            VARCHAR(255),
    admin_id               VARCHAR(255),
    created_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_promocode PRIMARY KEY (id)
);

ALTER TABLE promocode
    ADD CONSTRAINT uc_promocode_title UNIQUE (code);


ALTER TABLE promocode
    ADD CONSTRAINT FK_PROMOCODE_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);


ALTER TABLE promocode
    ADD CONSTRAINT FK_PROMOCODE_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES admin (id)
