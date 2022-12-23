create table chat_client
(
    id varchar(255) not null
        constraint pk_chat_client
            primary key,
    client_id   varchar(255)
        constraint fk_chat_client_on_client
            references client,
    supplier_id varchar(255)
        constraint fk_chat_client_on_supplier
            references supplier,
    archive_status varchar(255) not null,
    created_at  timestamp    not null
);