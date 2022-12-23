CREATE TABLE article_supplier
(
    article_id VARCHAR(255) NOT NULL,
    supplier_id VARCHAR(255) NOT NULL
);

ALTER TABLE article_supplier
    ADD CONSTRAINT FK_AS_ON_ARTICLE FOREIGN KEY (article_id) REFERENCES article (id);

ALTER TABLE article_supplier
    ADD CONSTRAINT FK_AS_ON_SUPPLIER FOREIGN KEY (supplier_id) REFERENCES supplier (id);