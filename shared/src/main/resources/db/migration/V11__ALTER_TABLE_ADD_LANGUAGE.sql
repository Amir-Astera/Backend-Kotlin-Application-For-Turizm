ALTER TABLE supplier ADD COLUMN language VARCHAR(2);
UPDATE supplier SET language='EN' WHERE language IS NULL;
ALTER TABLE supplier ALTER COLUMN language SET NOT NULL;

ALTER TABLE client ADD COLUMN language VARCHAR(2);
UPDATE client SET language='EN' WHERE language IS NULL;
ALTER TABLE client ALTER COLUMN language SET NOT NULL;