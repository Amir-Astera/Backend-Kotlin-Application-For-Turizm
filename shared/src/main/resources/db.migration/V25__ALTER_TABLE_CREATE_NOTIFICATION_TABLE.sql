CREATE TABLE notification
(
    id          VARCHAR(50)  NOT NULL,
    user_id     VARCHAR(255),
    senders_name VARCHAR(255) NOT NULL,
    recipient_type TEXT NOT NULL,
    status TEXT NOT NULL,
    appointment_datetime  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_notification PRIMARY KEY (id)
);

ALTER TABLE notification
    ADD CONSTRAINT FK_NOTIFICATION_ON_APP_USER FOREIGN KEY (user_id) REFERENCES app_user (id);

ALTER TABLE supplier ADD COLUMN notify BOOLEAN;
UPDATE supplier SET notify=TRUE WHERE notify IS NULL;
ALTER TABLE supplier ALTER COLUMN notify SET NOT NULL;

ALTER TABLE client ADD COLUMN notify BOOLEAN;
UPDATE client SET notify=TRUE WHERE notify IS NULL;
ALTER TABLE client ALTER COLUMN notify SET NOT NULL;