ALTER TABLE supplier ADD COLUMN feedback_count INTEGER;
UPDATE supplier SET feedback_count=0 WHERE feedback_count IS NULL;
ALTER TABLE supplier ALTER COLUMN feedback_count SET NOT NULL;