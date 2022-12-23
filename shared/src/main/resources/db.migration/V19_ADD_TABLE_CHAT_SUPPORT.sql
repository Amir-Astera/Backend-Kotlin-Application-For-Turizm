create table chat_support
(
    id varchar(255) not null
        constraint pk_chat_support
            primary key,
    client_id   varchar(255)
        constraint fk_chat_support_on_client
            references client,
    supplier_id varchar(255)
        constraint fk_chat_support_on_supplier
            references supplier,
    authority varchar(255) not null,
    created_at  timestamp    not null
);