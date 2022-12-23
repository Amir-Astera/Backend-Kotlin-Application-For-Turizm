ALTER TABLE supplier ADD COLUMN full_name VARCHAR(255);
UPDATE supplier SET full_name='DEFAULT NAME' WHERE full_name IS NULL;
ALTER TABLE supplier ALTER COLUMN full_name SET NOT NULL;
ALTER TABLE supplier ADD COLUMN gender VARCHAR(255);
UPDATE supplier SET gender='UNKNOWN' WHERE gender IS NULL;
ALTER TABLE supplier ALTER COLUMN gender SET NOT NULL;
ALTER TABLE supplier ADD COLUMN birth_date DATE;
ALTER TABLE supplier ADD COLUMN file_id VARCHAR(255);
ALTER TABLE supplier ADD COLUMN phone_type VARCHAR(255);
ALTER TABLE supplier ADD COLUMN os_type VARCHAR(255);
ALTER TABLE supplier ADD COLUMN app_type VARCHAR(255);
ALTER TABLE supplier ADD COLUMN have_unread_messages BOOLEAN;
ALTER TABLE supplier ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE;
UPDATE supplier SET created_at=now()::timestamp WHERE created_at IS NULL;
ALTER TABLE supplier ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE supplier ADD COLUMN updated_at TIMESTAMP WITHOUT TIME ZONE;
UPDATE supplier SET updated_at=now()::timestamp WHERE updated_at IS NULL;
ALTER TABLE supplier ALTER COLUMN updated_at SET NOT NULL;

ALTER TABLE client ADD COLUMN full_name VARCHAR(255);
UPDATE client SET full_name='DEFAULT NAME' WHERE full_name IS NULL;
ALTER TABLE client ALTER COLUMN full_name SET NOT NULL;
ALTER TABLE client ADD COLUMN gender VARCHAR(255);
UPDATE client SET gender='UNKNOWN' WHERE gender IS NULL;
ALTER TABLE client ALTER COLUMN gender SET NOT NULL;
ALTER TABLE client ADD COLUMN birth_date DATE;
ALTER TABLE client ADD COLUMN file_id VARCHAR(255);
ALTER TABLE client ADD COLUMN phone_type VARCHAR(255);
ALTER TABLE client ADD COLUMN os_type VARCHAR(255);
ALTER TABLE client ADD COLUMN app_type VARCHAR(255);
ALTER TABLE client ADD COLUMN have_unread_messages BOOLEAN;
ALTER TABLE client ADD COLUMN created_at TIMESTAMP WITHOUT TIME ZONE;
UPDATE client SET created_at=now()::timestamp WHERE created_at IS NULL;
ALTER TABLE client ALTER COLUMN created_at SET NOT NULL;
ALTER TABLE client ADD COLUMN updated_at TIMESTAMP WITHOUT TIME ZONE;
UPDATE client SET updated_at=now()::timestamp WHERE updated_at IS NULL;
ALTER TABLE client ALTER COLUMN updated_at SET NOT NULL;

ALTER TABLE app_user DROP COLUMN full_name;
ALTER TABLE app_user DROP COLUMN gender;
ALTER TABLE app_user DROP COLUMN birth_date;
ALTER TABLE app_user DROP COLUMN file_id;
ALTER TABLE app_user DROP COLUMN phone_type;
ALTER TABLE app_user DROP COLUMN os_type;
ALTER TABLE app_user DROP COLUMN app_type;
ALTER TABLE app_user DROP COLUMN have_unread_messages;

CREATE TABLE admin (
    id                   VARCHAR(255) NOT NULL,
    full_name            VARCHAR(255) NOT NULL,
    phone                VARCHAR(255),
    gender               VARCHAR(255) NOT NULL,
    file_id              VARCHAR(255),
    birth_date           date,
    phone_type           VARCHAR(255),
    os_type              VARCHAR(255),
    app_type             VARCHAR(255),
    have_unread_messages BOOLEAN,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_admin PRIMARY KEY (id)
);

ALTER TABLE app_user ADD COLUMN admin_id VARCHAR(255);

ALTER TABLE app_user
    ADD CONSTRAINT FK_APP_USER_ON_ADMIN FOREIGN KEY (admin_id) REFERENCES admin (id);
