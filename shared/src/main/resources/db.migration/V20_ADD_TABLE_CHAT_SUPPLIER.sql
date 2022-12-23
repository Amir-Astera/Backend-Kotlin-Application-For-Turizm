create table chat_supplier
(
    id varchar(255) not null
        constraint pk_chat_supplier
            primary key,
    supplier_id varchar(255)
        constraint fk_chat_supplier_on_supplier
            references supplier,
    client_id   varchar(255)
        constraint fk_chat_supplier_on_client
            references client,
    archive_status varchar(255) not null,
    created_at  timestamp    not null
);