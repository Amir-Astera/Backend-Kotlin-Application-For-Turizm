ALTER TABLE chat ADD COLUMN unread_messages_count INTEGER;
UPDATE chat SET unread_messages_count=0 WHERE unread_messages_count IS NULL;
ALTER TABLE chat ALTER COLUMN unread_messages_count SET NOT NULL;