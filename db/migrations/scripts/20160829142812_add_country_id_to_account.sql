-- // add_country_id_to_account
-- Migration SQL that makes the change goes here.

ALTER TABLE account ADD country_id BIGINT NULL;


-- //@UNDO
-- SQL to undo the change goes here.


ALTER TABLE account DROP COLUMN country_id;