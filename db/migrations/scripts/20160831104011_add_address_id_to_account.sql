-- // add_address_id_to_account
-- Migration SQL that makes the change goes here.
ALTER TABLE account ADD COLUMN address_id INTEGER;


-- //@UNDO
-- SQL to undo the change goes here.

ALTER TABLE account DROP COLUMN address_id;
