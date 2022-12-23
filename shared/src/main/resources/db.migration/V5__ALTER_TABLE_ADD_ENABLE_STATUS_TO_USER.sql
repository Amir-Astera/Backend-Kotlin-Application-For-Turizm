ALTER TABLE client ADD COLUMN enable_status VARCHAR(255);

UPDATE client SET enable_status='ENABLED' WHERE enable_status IS NULL;

ALTER TABLE client ALTER COLUMN enable_status SET NOT NULL;