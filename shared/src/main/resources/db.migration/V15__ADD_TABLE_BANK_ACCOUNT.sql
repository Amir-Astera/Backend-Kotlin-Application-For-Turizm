CREATE TABLE bank_account (
       id                   VARCHAR(255) NOT NULL,
       card_number          VARCHAR(255),
       company_name         VARCHAR(255),
       company_address      VARCHAR(255),
       bin                  VARCHAR(255),
       kbe                  VARCHAR(255),
       bik                  VARCHAR(255),
       iik                  VARCHAR(255),
       CONSTRAINT pk_bank_acc PRIMARY KEY (id)
);

ALTER TABLE supplier ADD COLUMN bank_account VARCHAR(255);

ALTER TABLE supplier
    ADD CONSTRAINT FK_SUPPLIER_ON_BANK_ACCOUNT FOREIGN KEY (bank_account) REFERENCES bank_account (id);