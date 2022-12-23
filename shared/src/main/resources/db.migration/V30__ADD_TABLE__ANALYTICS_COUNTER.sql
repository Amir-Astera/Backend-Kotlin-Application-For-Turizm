CREATE TABLE analytics_counter (
    id VARCHAR(255) NOT NULL,
    supplier_logins INTEGER NOT NULL,
    client_logins INTEGER NOT NULL,
    total_logins INTEGER NOT NULL,
    suppliers_created INTEGER NOT NULL,
    clients_created INTEGER NOT NULL,
    total_created INTEGER NOT NULL,
    transactions_completed INTEGER NOT NULL,
    created_at TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_analytics_counter PRIMARY KEY (id)
)