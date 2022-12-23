ALTER TABLE client ADD COLUMN activity_status VARCHAR(255);

UPDATE client SET activity_status='ACTIVE' WHERE activity_status IS NULL;

ALTER TABLE client ALTER COLUMN activity_status SET NOT NULL;