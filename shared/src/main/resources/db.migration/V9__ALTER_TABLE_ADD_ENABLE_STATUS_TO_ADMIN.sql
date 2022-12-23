ALTER TABLE admin ADD COLUMN enable_status VARCHAR(255);
UPDATE admin SET enable_status='ENABLED' WHERE enable_status IS NULL;
ALTER TABLE admin ALTER COLUMN enable_status SET NOT NULL;

ALTER TABLE admin ADD COLUMN activity_status VARCHAR(255);
UPDATE admin SET activity_status='ACTIVE' WHERE activity_status IS NULL;
ALTER TABLE admin ALTER COLUMN activity_status SET NOT NULL;

ALTER TABLE admin DROP COLUMN phone;