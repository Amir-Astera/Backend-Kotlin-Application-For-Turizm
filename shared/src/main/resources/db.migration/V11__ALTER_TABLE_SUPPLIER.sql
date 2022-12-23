ALTER TABLE supplier RENAME full_name TO first_name;
ALTER TABLE supplier ADD COLUMN surname VARCHAR (255);
ALTER TABLE supplier ADD COLUMN patronymic VARCHAR (255);
ALTER TABLE supplier ALTER COLUMN first_name DROP NOT NULL;
