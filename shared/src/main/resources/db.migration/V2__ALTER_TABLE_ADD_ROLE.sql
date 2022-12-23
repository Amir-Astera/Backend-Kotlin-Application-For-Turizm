ALTER TABLE widget ADD COLUMN authority VARCHAR(255);

UPDATE widget SET authority='CLIENT' WHERE authority IS NULL;

ALTER TABLE widget ALTER COLUMN authority SET NOT NULL;

ALTER TABLE widget DROP COLUMN position;

ALTER TABLE tutorial ADD COLUMN authority VARCHAR(255);

UPDATE tutorial SET authority='CLIENT' WHERE authority IS NULL;

ALTER TABLE tutorial ALTER COLUMN authority SET NOT NULL;

ALTER TABLE faq ADD COLUMN authority VARCHAR(255);

UPDATE faq SET authority='CLIENT' WHERE authority IS NULL;

ALTER TABLE faq ALTER COLUMN authority SET NOT NULL;